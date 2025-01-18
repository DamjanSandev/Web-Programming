package mk.ukim.finki.wp.lab.web.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.lab.model.Category;
import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.model.Location;
import mk.ukim.finki.wp.lab.service.AuthService;
import mk.ukim.finki.wp.lab.service.CategoryService;
import mk.ukim.finki.wp.lab.service.EventService;
import mk.ukim.finki.wp.lab.service.LocationService;
import org.h2.util.Cache;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final CategoryService categoryService;
    private final AuthService authService;

    public EventController(EventService eventService, LocationService locationService, CategoryService categoryService, AuthService authService) {
        this.eventService = eventService;
        this.locationService = locationService;
        this.categoryService = categoryService;
        this.authService = authService;
    }

    @PostMapping("/location")
    public String getEventsWithLocation(@RequestParam Long locationId, Model model) {
        Optional<Location> location = locationService.findById(locationId);
        if (location.isPresent()) {
            List<Event> events = location.get().getEvents();
            if (events.isEmpty()) {
                return "redirect:/events";
            }
            model.addAttribute("events", events);
            return "eventsByLocation";
        }
        return "redirect:/events";
    }

    @GetMapping
    public String getEventsPage(@RequestParam(required = false) String error,
                                @RequestParam(required = false) Long searchByCategory,
                                @RequestParam(required = false) Long searchByLocation,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                Model model, HttpServletRequest request) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        //model.addAttribute("events", eventService.findByLocationAndCategory(searchByLocation, searchByCategory));
        Page<Event> page = this.eventService.findPage(searchByCategory, searchByLocation, pageNum, pageSize);
        model.addAttribute("page", page);
        model.addAttribute("categories", categoryService.listAll());
        model.addAttribute("locations", locationService.findAll());
        String username = request.getRemoteUser();
        model.addAttribute("username", username);
        model.addAttribute("name", username != null ? authService.findByUsername(username).getName() : null);
        return "listEvents";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public String saveEvent(@RequestParam String name, @RequestParam String description, @RequestParam double popularityScore, @RequestParam Long locationId, @RequestParam Long categoryId,@RequestParam int numTickets) {
        eventService.save(name, description, popularityScore, locationId, categoryId,numTickets);
        return "redirect:/events";

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/addEvent/{id}")
    public String editEvent(@PathVariable Long id, Model model) {
        Optional<Event> event = this.eventService.findById(id);
        if (event.isEmpty()) {
            model.addAttribute("error", "Event not found");
            return "redirect:/events";
        }
        Event e = event.get();
        List<Location> locations = locationService.findAll();
        List<Category> categories = categoryService.listAll();
        model.addAttribute("locations", locations);
        model.addAttribute("event", e);
        model.addAttribute("categories", categories);
        return "add-event";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/addEvent")
    public String addEvent(Model model) {
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("categories", categoryService.listAll());
        return "add-event";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/update/{id}")
    public String updateEvent(@PathVariable Long id,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam double popularityScore,
                              @RequestParam Long locationId,
                              @RequestParam Long categoryId,
                            @RequestParam int numTickets) {

        eventService.updateEvent(id, name, description, popularityScore, locationId, categoryId,numTickets);
        return "redirect:/events";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id, Model model) {
        this.eventService.deleteEvent(id);
        return "redirect:/events";
    }

//    @PostMapping("/like/{id}")
//    public String likeEvent(@PathVariable Long id, Model model) {
//        Optional<Event> e = eventService.findById(id);
//        if (e.isPresent()) {
//            Event ev = e.get();
//            ev.setDisabled(true);
//            ev.setPopularityScore(ev.getPopularityScore() + 1);
//            eventService.updateEvent(ev.getId(), ev.getName(), ev.getDescription(), ev.getPopularityScore(), ev.getLocation().getId(), ev.getCategory().getId(),ev.getNumTickets());
//        }
//        return "redirect:/events";
//    }

}
