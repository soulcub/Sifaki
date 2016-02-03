package com.sifaki.api;

/**
 * @author SStorozhev
 * @since 2/2/2016
 */
//@RestController
public class EventsCollectorController {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(EventsCollector.class);
//
////    @Autowired
//    private SessionFactory sessionFactory;
////    @Autowired
//    private DouHtmlParser douHtmlParser;
//
////    @RequestMapping(value = "/collectEvents", method = RequestMethod.GET)
//    public String collectEvents() throws IOException {
//        LOGGER.info("Starting collection of the events.");
//        final Elements events = douHtmlParser.parseAllEvents();
//
//        final Session session = sessionFactory.openSession();
//        final Transaction transaction = session.beginTransaction();
//        final Optional<Event> optionalEvent = Optional.fromNullable(session.get(Event.class, 1));
//        transaction.commit();
//        if (optionalEvent.isPresent()) {
//            final Event eventToReturn = optionalEvent.get();
//            return eventToReturn.toString();
//        } else {
//            return "No data";
//        }
//    }

}