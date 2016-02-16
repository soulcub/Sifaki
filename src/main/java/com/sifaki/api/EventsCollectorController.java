package com.sifaki.api;

import java.io.IOException;
import java.util.List;

import com.sifaki.db.entity.Event;
import com.sifaki.webparser.dou.DouHtmlParser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SStorozhev
 * @since 2/2/2016
 */
@RestController
@RequestMapping(value = "/dataCollector")
public class EventsCollectorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsCollectorController.class);

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private DouHtmlParser douHtmlParser;

    @RequestMapping(value = "/dou", method = RequestMethod.GET)
    public String collectEvents() throws IOException {
        LOGGER.info("Starting collection of the events.");
        final List<Event> events = douHtmlParser.parseAllEvents();

        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        events.forEach(session::saveOrUpdate);
        transaction.commit();
        return events.toString();
    }

}
