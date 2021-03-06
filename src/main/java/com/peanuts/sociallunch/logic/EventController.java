package com.peanuts.sociallunch.logic;


import com.peanuts.sociallunch.dao.EventDao;
import com.peanuts.sociallunch.dao.UserDao;
import com.peanuts.sociallunch.model.Address;
import com.peanuts.sociallunch.model.Event;
import com.peanuts.sociallunch.model.User;
import com.peanuts.sociallunch.util.ViewUtil;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventController {

    private EventDao eventDao;
    private UserDao userDao;

    public EventController(EventDao eventDao, UserDao userDao) {
        this.eventDao = eventDao;
        this.userDao = userDao;
    }

    public Route getAllEvents = (Request request, Response response) -> {
        List<Event> result = eventDao.getAll();
        Map params = new HashMap<>();
        params.put("events", result);
        return ViewUtil.render(request, params, "/home/index");
    };

    public Route showAddNewForm = (Request request, Response response) -> ViewUtil.render(request, new HashMap(),"/new-event");

    public Route createNewEvent = (Request request, Response response) -> {

        String title = request.queryParams("event-title");
        Integer capacity = Integer.parseInt(request.queryParams("event-capacity"));
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datetime = new Date();

        try {
            datetime = dateTimeFormatter.parse(request.queryParams("event-date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Address address = new Address("Hungary", "Budapest", "1111", "Some street 43. 4/2", "Home");
        String description = request.queryParams("event-description");
        User user = new User("Jee", "Pa",
                "lama23@gi.com", "200000", "valami",
                "2imgfilename", (byte) 1,(byte) 0,null,
                null);


        if (true) {
            Event newEvent = new Event();
            newEvent.setTitle(title);
            newEvent.setCapacity(capacity);
            newEvent.setDate(datetime);
            newEvent.setDescription(description);
            newEvent.setHost(null);
            eventDao.save(newEvent);
            //response.redirect("/event-created");
        }

        Map params = new HashMap<>();
        List<Event> result = eventDao.getAll();
        params.put("events", result);
        return ViewUtil.render(request, params, "/home/index");
    };

    public Route addedEvent = (Request request, Response response) -> ViewUtil.render(request, new HashMap(),"/event-added");


    public Route findEventById = (Request request, Response response) -> {

        long eventId = Long.parseLong(request.queryParams("eid"));
        Event result = eventDao.findEventById(eventId);
        Map params = new HashMap<>();
        params.put("event", result);

        return ViewUtil.render(request, params, "event");
    };

}
