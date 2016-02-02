package com.sifaki.api;

import com.google.common.base.Optional;
import com.sifaki.db.entity.Event;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping("/{id}")
    public String get(@PathVariable int id) {
        LOGGER.info("Getting Event by id='{}'", id);
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        final Optional<Event> optionalEvent = Optional.fromNullable(session.get(Event.class, id));
        transaction.commit();
        if (optionalEvent.isPresent()) {
            final Event eventToReturn = optionalEvent.get();
            LOGGER.debug("Returning Event='{}'", eventToReturn);
            return eventToReturn.toString();
        } else {
            LOGGER.debug("No events with id='{}'", id);
            return "No data";
        }
    }

}
