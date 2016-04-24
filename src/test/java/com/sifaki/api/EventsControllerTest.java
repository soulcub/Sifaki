package com.sifaki.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sifaki.db.entity.Event;
import com.sifaki.db.entity.EventCategory;
import com.sifaki.db.entity.builders.EventBuilder;
import com.sifaki.db.entity.builders.EventCategoryBuilder;
import com.sifaki.webparser.prise.CurrencyType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.LocalDateTime;
import org.junit.After;
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
public class EventsControllerTest {

    private static final Integer ID = 666;
    private static final String TITLE = "testTitle";
    private static final String IMAGE_LINK = "testImagelink";
    private static final LocalDateTime DATE_TIME = LocalDateTime.parse("2016-05-18T15:16:17");
    private static final String COORDINATES = "testCoordinates";
    private static final String CITY = "testCity";
    private static final Integer COST = 999;
    private static final String COST_COMMENTARY = "testCostcommentary";
    private static final String DESCRIPTION = "testDescription";
    private static final String TAGS = "testTags";
    private static final String SOURCE_LINK = "testSourcelink";
    private static final CurrencyType CURRENCY_TYPE = CurrencyType.UAH;
    private static final Integer CATEGORY = 987;

    @Mock
    private SessionFactory sessionFactory;
    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @Mock
    private Query query;
    @InjectMocks
    private EventsController eventsController;

    private MockMvc mvc;
    private Event event = EventBuilder.anEvent()
            .withId(ID)
            .withTitle(TITLE)
            .withImageLink(IMAGE_LINK)
            .withDateTime(DATE_TIME)
            .withCoordinates(COORDINATES)
            .withCity(CITY)
            .withCost(COST)
            .withCostCommentary(COST_COMMENTARY)
            .withDescription(DESCRIPTION)
            .withTags(TAGS)
            .withSourceLink(SOURCE_LINK)
            .withCurrencyType(CURRENCY_TYPE)
            .withCategory(CATEGORY)
            .build();
    private String eventJson = "{\"" +
            "id\":666,\"" +
            "title\":\"testTitle\",\"" +
            "imageLink\":\"testImagelink\",\"" +
            "dateTime\":[2016,5,18,15,16,17,0],\"" +
            "coordinates\":\"testCoordinates\",\"" +
            "city\":\"testCity\",\"" +
            "cost\":999,\"" +
            "costCommentary\":\"testCostcommentary\",\"" +
            "description\":\"testDescription\",\"" +
            "tags\":\"testTags\",\"" +
            "sourceLink\":\"testSourcelink\",\"" +
            "currencyType\":\"UAH\",\"" +
            "category\":987}";

    @Bean
    public Gson gson() {
        return new Gson();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(eventsController).build();
        doReturn(session).when(sessionFactory).openSession();
        doReturn(transaction).when(session).beginTransaction();
    }

    @After
    public void tearDown() {
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testGetRegularEvent() throws Exception {
        final List<Event> events = Collections.singletonList(event);
        doReturn(query).when(session).createQuery("from " + Event.class.getName() + " E where E.id = " + ID);
        doReturn(events).when(query).list();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValueAsString(event);
        mvc.perform(
                MockMvcRequestBuilders.
                        get("/events/getById/{id}", ID).
                        accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(eventJson)
        );
    }

    @Test
    public void testGetNullEvent() throws Exception {
        doReturn(query).when(session).createQuery("from " + Event.class.getName() + " E where E.id = " + ID);
        doReturn(Collections.EMPTY_LIST).when(query).list();
        mvc.perform(
                MockMvcRequestBuilders.
                        get("/events/getById/{id}", ID).
                        accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(equalTo("No events with id=" + ID))
        );
    }

    @Test
    public void testCreateEvent() throws Exception {
        String eventJsonWithoutId = "{\"" +
                "title\":\"Test\",\"" +
                "imageLink\":\"testlink\",\"" +
                "dateTime\":[2016,5,18,15,0,0,0],\"" +
                "coordinates\":\"test\",\"" +
                "city\":\"TestCity\",\"" +
                "cost\":10000,\"" +
                "costCommentary\":\"Expencive\",\"" +
                "description\":\"testdes\",\"" +
                "tags\":\"testTags\",\"" +
                "sourceLink\":\"http://dou.ua/calendar/10153/\",\"" +
                "currencyType\":\"UAH\",\"" +
                "category\":3}";
        Event eventWithoutId = EventBuilder.anEvent()
                .withTitle("Test")
                .withImageLink("testlink")
                .withDateTime(LocalDateTime.parse("2016-05-18T15:00:00"))
                .withCoordinates("test")
                .withCity("TestCity")
                .withCost(10000)
                .withCostCommentary("Expencive")
                .withDescription("testdes")
                .withTags("testTags")
                .withSourceLink("http://dou.ua/calendar/10153/")
                .withCurrencyType(CurrencyType.UAH)
                .withCategory(3)
                .build();
        doReturn(event).when(session).save(eventWithoutId);

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/events/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJsonWithoutId)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(equalTo(eventJson))
        );
    }

    @Test
    public void testGetAllEventCategories() throws Exception {
        doReturn(query).when(session).createQuery("from " + EventCategory.class.getName());
        final List<Object> resultListOrCategories = Arrays.asList(
                EventCategoryBuilder.anEventCategory().withId(666).build(),
                EventCategoryBuilder.anEventCategory().withId(999).build()
        );
        doReturn(resultListOrCategories).when(query).list();

        mvc.perform(
                MockMvcRequestBuilders
                        .get("/events/categories/get")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(equalTo(new ObjectMapper().writeValueAsString(resultListOrCategories)))
        );
    }

}
