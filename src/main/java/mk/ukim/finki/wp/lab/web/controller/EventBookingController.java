package mk.ukim.finki.wp.lab.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.model.EventBooking;
import mk.ukim.finki.wp.lab.service.EventBookingService;
import mk.ukim.finki.wp.lab.service.EventService;
import mk.ukim.finki.wp.lab.service.impl.EventBookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/eventBooking")
public class EventBookingController {
    private final EventBookingService eventBookingService;
    private final EventService eventService;

    public EventBookingController(EventBookingService eventBookingService, EventService eventService) {
        this.eventBookingService = eventBookingService;
        this.eventService = eventService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getEventBookingPage(HttpServletRequest request, Model model) {
        String username = request.getRemoteUser();
        List<EventBooking> bookings = this.eventBookingService.findByUser(username);
        model.addAttribute("bookings", bookings);
        return "bookingConfirmation";
    }

    @PostMapping("/book")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String bookEvent(@RequestParam String attName,
                            @RequestParam String eventName,
                            @RequestParam int numTickets,
                            HttpServletRequest request) {
        // Fetch the event by name
        Event bookedEvent = eventService.findByName(eventName).orElse(null);

        // Check if event exists
        if (bookedEvent == null) {
            // Handle the case when the event doesn't exist
            return "redirect:/events?error=EventNotFound";
        }

        // Check if there are enough tickets available
        if (bookedEvent.getNumTickets() < numTickets) {
            // Handle insufficient tickets
            return "redirect:/events?error=InsufficientTickets";
        }

        // Get the username and place the booking
        String username = request.getRemoteUser();
        eventBookingService.placeBooking(username, eventName, attName, request.getRemoteAddr(), numTickets);

        // Update the event to reserve the tickets
        eventService.reserveCard(bookedEvent.getId(), numTickets);

        // Redirect to the booking page or another confirmation page
        return "redirect:/eventBooking";
    }

}
