package com.sifaki.api;

import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.sifaki.db.entity.Event;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class EventsGetterControllerTest {

    private static final int ID = 666;

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Query query;
    @InjectMocks
    private EventsGetterController eventsGetterController;

    private MockMvc mvc;
    private Event event = Event.newBuilder().id(ID).build();

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(eventsGetterController).build();
        doReturn(session).when(sessionFactory).openSession();
        doReturn(transaction).when(session).beginTransaction();
    }

    @Test
    public void testGetRegularEvent() throws Exception {
        final List<Event> events = Collections.singletonList(event);
        doReturn(query).when(session).createQuery("from " + Event.class.getName() + " E where E.id = " + ID);
        doReturn(events).when(query).list();

        mvc.perform(
                MockMvcRequestBuilders.
                        get("/events/get/{id}", ID).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(
                        status().isOk()).
                andExpect(
                        content().string(new Gson().toJson(event)));

        verify(transaction).commit();
    }

    @Test
    public void testGetNullEvent() throws Exception {
        doReturn(query).when(session).createQuery("from " + Event.class.getName() + " E where E.id = " + ID);
        doReturn(Collections.EMPTY_LIST).when(query).list();
        mvc.perform(
                MockMvcRequestBuilders.
                        get("/events/get/{id}", ID).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(
                        status().isOk()).
                andExpect(
                        content().string(
                                equalTo("No events with id=" + ID)));

        verify(transaction).commit();
    }
}
