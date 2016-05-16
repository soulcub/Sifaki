package com.sifaki.api;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sifaki.Errors;
import com.sifaki.db.entity.Event;
import com.sifaki.db.entity.EventCategory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsController.class);

    @Autowired
    private SessionFactory sessionFactory;

    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public String getById(@PathVariable int id) throws JsonProcessingException {
        try (Session session = sessionFactory.openSession()) {
            LOGGER.info("Getting Event by id='{}'", id);
            final Transaction transaction = session.beginTransaction();
            final List list = session.createQuery("from " + Event.class.getName() + " E where E.id = " + id).list();
            final Event event = (Event) (list.isEmpty() ? null : list.iterator().next());
            final Optional<Event> optionalEvent = Optional.ofNullable(event);
            transaction.commit();
            if (optionalEvent.isPresent()) {
                final Event eventToReturn = optionalEvent.get();
                LOGGER.debug("Returning Event='{}'", eventToReturn);
                return new ObjectMapper().writeValueAsString(eventToReturn);
            } else {
                LOGGER.debug("No events with id='{}'", id);
                return "No events with id=" + id;
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred", e);
            return new ObjectMapper().writeValueAsString("Unexpected error");
        }
    }

    @RequestMapping(value = "/getByCategory/{category}", method = RequestMethod.GET)
    public String getByCategory(@PathVariable int category) throws JsonProcessingException {
        try (Session session = sessionFactory.openSession()) {
            LOGGER.info("Getting Event by category='{}'", category);
            final Transaction transaction = session.beginTransaction();
            final List list = session.createQuery("from " + Event.class.getName() + " E where E.category = " + category).list();
            transaction.commit();
            if (!list.isEmpty()) {
                LOGGER.debug("Returning Events='{}'", list);
                return new ObjectMapper().writeValueAsString(list);
            } else {
                LOGGER.debug("No events with category='{}'", category);
                return "No events with category=" + category;
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred", e);
            return new ObjectMapper().writeValueAsString("Unexpected error");
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getAllEvents() throws JsonProcessingException {
        LOGGER.info("Getting all Events");
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        final List list = session.createQuery("from " + Event.class.getName()).list();
        transaction.commit();
        session.close();
        if (!list.isEmpty()) {
            LOGGER.debug("Returning all Events='{}'", list);
            return new ObjectMapper().writeValueAsString(list);
        } else {
            LOGGER.debug("No events were found");
            return "No events were found";
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createEvent(@RequestBody Event event) throws JsonProcessingException {
        try (final Session session = sessionFactory.openSession()) {
            final Transaction transaction = session.beginTransaction();
            final List list = session.createQuery("from " + Event.class.getName() + " E where E.sourceLink = '" + event.getSourceLink() + "'").list();
            transaction.commit();
            if (!list.isEmpty()) {
                return new ObjectMapper().writeValueAsString(Errors.EVENT_ALREADY_EXIST);
            }
            final Serializable save = session.save(event);
            transaction.commit();
            return new ObjectMapper().writeValueAsString(save);
        } catch (Exception e) {
            LOGGER.error("Some unexpected error occurred", e);
            return new ObjectMapper().writeValueAsString(Errors.UNDEFINED_ERROR);
        }
    }

    @RequestMapping(value = "/categories/get", method = RequestMethod.GET)
    public String getAllEventCategories() throws JsonProcessingException {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();

        final List list = session.createQuery("from " + EventCategory.class.getName()).list();

        transaction.commit();
        session.close();

        if (!list.isEmpty()) {
            LOGGER.debug("Returning all EventCategories='{}'", list);
            return new ObjectMapper().writeValueAsString(list);
        } else {
            LOGGER.debug("No event categories were found");
            return "No event categories were found";
        }
    }

}
