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
import org.junit.Ignore;
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
    private EventsController eventsController;

    private MockMvc mvc;
    private Event event = Event.newBuilder().id(ID).build();

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

    @Ignore
    @Test
    public void testCreateEvent() throws Exception {
        Event event = Event.newBuilder().id(1).build();
        String eventJson = new Gson().toJson(event);
        eventJson = "{\"id\":4764,\"title\":\"Курс “Java Start”\",\"imageLink\":\"http://s.developers.org.ua/CACHE/images/img/events/JAVA_logo_for_DOU_actual_ULcRkYi/b21c0c6101192e5f137b7a3f599e2cfc.png\",\"dateTime\":{\"iLocalMillis\":1456185600000,\"iChronology\":{\"iBase\":{\"iMinDaysInFirstWeek\":4}}},\"coordinates\":\"Киев, ул. Вадима Гетьмана, 1А (3 минуты ходьбы от метро Шулявская)\",\"cost\":2250,\"costCommentary\":\"2250 грн.\",\"description\":\"<div>\\r\\n <p><strong>Сложность:</strong> Новичок<br><strong>Продолжительность:</strong> 3&nbsp;недели (24&nbsp;академ часа)<br><strong>Дни занятий:</strong> Вт, Чт.</p> \\r\\n <p><img src=\\\"http://s.developers.org.ua/storage-files/JAVA_for_DOU_4_oL4Q9C0.png\\\"></p> \\r\\n <p><strong>Java Start</strong>&nbsp;— твой старт в&nbsp;мир программирования. Обучение на&nbsp;данном курсе дает базовые знания языка Java, которое включает в&nbsp;себя примитивные типы данных и&nbsp;структуры, основные операторы, перечисления, классы, интерфейсы, принципы ООП, обработка ошибок и&nbsp;исключений. После курса&nbsp;Вы сможете создавать программы с&nbsp;использованием наиболее распространенных Java конструкций, создавать иерархии классов, основанные на&nbsp;реальных бизнес-моделях, а&nbsp;так&nbsp;же, научитесь использовать современные инструментальные средства для разработки программного обеспечения.</p> \\r\\n <h2>Программа курса</h2> \\r\\n <h4>Модуль&nbsp;1: Введение</h4> \\r\\n <p>—&nbsp;Почему Java?<br>—&nbsp;История языка<br>—&nbsp;Установка Java-машины<br>—&nbsp;Среда разработки Eclipse</p> \\r\\n <h4>Модуль&nbsp;2: Основные структуры</h4> \\r\\n <p>—&nbsp;Типы данных<br>—&nbsp;Переменные и&nbsp;константы<br>—&nbsp;Основные операторы<br>—&nbsp;Строки, дата, время<br>—&nbsp;Ввод/вывод данных<br>—&nbsp;Операторы условного перехода<br>—&nbsp;Циклы<br>—&nbsp;Массивы</p> \\r\\n <h4>Модуль&nbsp;3: Объекты и&nbsp;классы</h4> \\r\\n <p>—&nbsp;Использование классов<br>—&nbsp;Создание классов<br>—&nbsp;Поля<br>—&nbsp;Методы<br>—&nbsp;Конструкторы<br>—&nbsp;Статические поля и&nbsp;методы<br>—&nbsp;Пакеты</p> \\r\\n <h4>Модуль&nbsp;4: Объектно-ориентированное программирование</h4> \\r\\n <p>—&nbsp;Инкапсуляция<br>—&nbsp;Наследование<br>—&nbsp;Полиморфизм<br>—&nbsp;Абстрактные классы<br>—&nbsp;Интерфейсы</p> \\r\\n <h4>Модуль&nbsp;5: Перечисленные типы</h4> \\r\\n <p>—&nbsp;Предназначение<br>—&nbsp;Основные свойства<br>—&nbsp;Добавление полей и&nbsp;методов</p> \\r\\n <h4>Модуль&nbsp;6: Исключения</h4> \\r\\n <p>—&nbsp;Концепция и&nbsp;предназначение<br>—&nbsp;Базовые исключения<br>—&nbsp;Перехват исключений<br>—&nbsp;Создание исключений<br>—&nbsp;Блок ‘finally’<br>—&nbsp;Классификация исключений</p> \\r\\n <h2>В&nbsp;учебном центре Java Start UP:</h2> \\r\\n <h4>01: Преподаватели Senior уровня</h4> \\r\\n <p>Преподаватели являются специалистами компаний ЕPAM и&nbsp;Ciklum, стаж преподавания больше 6&nbsp;лет, в&nbsp;IT сфере&nbsp;— от&nbsp;10&nbsp;лет</p> \\r\\n <h4>02: Online поддержка</h4> \\r\\n <p>На&nbsp;протяжении всего периода обучения у&nbsp;вас будет возможность получать оперативные ответы на&nbsp;возникшие вопросы (мы&nbsp;всегда доступны в&nbsp;Skype или на&nbsp;почте)</p> \\r\\n <h4>03. Готовое портфолио</h4> \\r\\n <p>Во&nbsp;время обучения на&nbsp;всех трёх блоках, Вы&nbsp;научитесь разрабатывать современное программное обеспечение, которое станет Вашей визитной карточкой<br><a href=\\\"http://javastartup.com.ua\\\" target=\\\"_blank\\\">javastartup.com.ua</a></p> \\r\\n <h2>Контакты</h2> \\r\\n <p>(096) 18 18&nbsp;771<br>(095) 48 84&nbsp;765<br><a href=\\\"mailto:info@javastartup.com.ua\\\">info@javastartup.com.ua</a></p>\\r\\n</div>\",\"tags\":\"<a href=\\\"http://dou.ua/calendar/tags/Java/\\\">Java</a>,<a href=\\\"http://dou.ua/calendar/tags/%D0%BA%D1%83%D1%80%D1%81%D1%8B/\\\">курсы</a>\",\"sourceLink\":\"http://dou.ua/calendar/9836/\",\"currencyType\":\"UAH\"}";
        doReturn(event).when(session).save(event);

        mvc.perform(
                MockMvcRequestBuilders.
                        post("/events/create").
                        content(eventJson).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(
                        status().isOk()).
                andExpect(
                        content().string(
                                equalTo(eventJson)));
        verify(transaction).commit();
    }

}
