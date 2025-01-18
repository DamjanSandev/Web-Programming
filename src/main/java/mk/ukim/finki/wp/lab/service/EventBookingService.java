package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.EventBooking;

import java.util.List;

public interface EventBookingService {
    void placeBooking(String username, String eventName, String attendeeName, String attendeeAddress, long numberOfTickets);

    List<EventBooking> listAll();

    List<EventBooking> searchEvents(String text);

    List<EventBooking> findByUser(String username);

    EventBooking findById(Long id);

    void deleteEventBooking(Long id);
}