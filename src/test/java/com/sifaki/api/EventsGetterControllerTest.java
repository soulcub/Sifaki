package com.sifaki.api;

import com.sifaki.db.entity.Event;
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

    private MockMvc mvc;

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Event event;
    @InjectMocks
    private EventsGetterController eventsGetterController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(eventsGetterController).build();
        doReturn(session).when(sessionFactory).openSession();
        doReturn(transaction).when(session).beginTransaction();
    }

    @Test
    public void testGetRegularEvent() throws Exception {
        doReturn(event).when(session).get(Event.class, ID);
        mvc.perform(
                MockMvcRequestBuilders.
                        get("/events/get/{id}", ID).
                        accept(MediaType.APPLICATION_JSON)).
                andExpect(
                        status().isOk()).
                andExpect(
                        content().string(event.toString()));

        verify(transaction).commit();
    }

    @Test
    public void testGetNullEvent() throws Exception {
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
