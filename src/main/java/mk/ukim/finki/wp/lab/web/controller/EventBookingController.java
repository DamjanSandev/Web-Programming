package mk.ukim.finki.wp.lab.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/eventBooking")
public class EventBookingController {

    @PostMapping("/confirm")
    public String confirm(@RequestParam("eventName") String name, @RequestParam("numTickets") int numTickets, Model model) {
        model.addAttribute("eventName", name);
        model.addAttribute("numTickets", numTickets);
        return "bookingConfirmation";
    }
}
