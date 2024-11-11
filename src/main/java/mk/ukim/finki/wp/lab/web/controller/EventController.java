package mk.ukim.finki.wp.lab.web.controller;


import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.model.Location;
import mk.ukim.finki.wp.lab.service.EventService;
import mk.ukim.finki.wp.lab.service.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final LocationService locationService;

    public EventController(EventService eventService, LocationService locationService) {
        this.eventService = eventService;
        this.locationService = locationService;
    }


    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("error", error);
            model.addAttribute("hasError", true);
        }
        List<Event> events = eventService.listAll();
        model.addAttribute("events", events);
        model.addAttribute("locations", locationService.findAll());
        return "listEvents";
    }

    @PostMapping("/add")
    public String saveEvent(@RequestParam String name, @RequestParam String description, @RequestParam double popularityScore, @RequestParam Long locationId) {
        Optional<Location> location = locationService.findById(locationId);
        if (location.isEmpty()) {
            return "redirect:/events";
        }
        eventService.save(name, description, popularityScore, locationId);
        return "redirect:/events";

    }

    @GetMapping("/addEvent/{id}")
    public String editEvent(@PathVariable Long id, Model model) {
        Optional<Event> event = this.eventService.findById(id);
        if (event.isEmpty()) {
            model.addAttribute("error", "Event not found");
            return "redirect:/events";
        }
        Event e = event.get();
        List<Location> locations = locationService.findAll();
        model.addAttribute("locations", locations);
        model.addAttribute("event", e);
        return "add-event";
    }

    @GetMapping("/addEvent")
    public String addEvent(Model model) {
        model.addAttribute("locations", locationService.findAll());
        return "add-event";
    }

    @PostMapping("/update/{id}")
    public String updateEvent(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam double popularityScore,
                              @RequestParam Long locationId) {

        Optional<Location> locationOptional = locationService.findById(locationId);
        if (locationOptional.isEmpty()) {
            return "redirect:/events?error=Location+not+found";
        }

        eventService.updateEvent(id, name, description, popularityScore, locationId);
        return "redirect:/events";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id, Model model) {
        this.eventService.deleteEvent(id);
        return "redirect:/events";
    }

    @PostMapping("/like/{id}")
    public String likeEvent(@PathVariable Long id, Model model) {
        Optional<Event> e = eventService.findById(id);
        if (e.isPresent()) {
            Event ev = e.get();
            ev.setDisabled(true);
            ev.setPopularityScore(ev.getPopularityScore() + 1);
        }
        return "redirect:/events";
    }

}
