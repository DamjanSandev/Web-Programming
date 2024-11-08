package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.EventBooking;
import mk.ukim.finki.wp.lab.model.exception.InvalidArgsException;
import mk.ukim.finki.wp.lab.service.EventBookingService;
import org.springframework.stereotype.Service;

@Service
public class EventBookingServiceImpl implements EventBookingService {

    @Override
    public EventBooking placeBooking(String eventName, String attendeeName, String attendeeAddress, long numberOfTickets) {
        if (eventName == null || attendeeName == null || attendeeAddress == null || numberOfTickets <= 0)
            throw new InvalidArgsException();
        return new EventBooking(eventName, attendeeName, attendeeAddress, numberOfTickets);
    }
}
