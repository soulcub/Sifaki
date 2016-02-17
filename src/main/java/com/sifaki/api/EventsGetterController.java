package com.sifaki.api;

import java.util.List;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.sifaki.db.entity.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventsGetterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsGetterController.class);

    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public String get(@PathVariable int id) {
        LOGGER.info("Getting Event by id='{}'", id);
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        final List list = session.createQuery("from " + Event.class.getName() + " E where E.id = " + id).list();
        final Event event = (Event) (list.isEmpty() ? null : list.iterator().next());
        final Optional<Event> optionalEvent = Optional.fromNullable(event);
        transaction.commit();
        if (optionalEvent.isPresent()) {
            final Event eventToReturn = optionalEvent.get();
            LOGGER.debug("Returning Event='{}'", eventToReturn);
            return new Gson().toJson(eventToReturn);
        } else {
            LOGGER.debug("No events with id='{}'", id);
            return "No events with id=" + id;
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAll() {
        LOGGER.info("Getting all Events");
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        final List list = session.createQuery("from " + Event.class.getName()).list();
        transaction.commit();
        if (!list.isEmpty()) {
            LOGGER.debug("Returning all Events='{}'", list);
            return new Gson().toJson(list);
        } else {
            LOGGER.debug("No events were found");
            return "No events were found";
        }
    }

}
