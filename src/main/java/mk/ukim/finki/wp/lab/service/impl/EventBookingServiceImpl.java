package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.EventBooking;
import mk.ukim.finki.wp.lab.model.User;
import mk.ukim.finki.wp.lab.model.exception.InvalidArgsException;
import mk.ukim.finki.wp.lab.model.exception.InvalidEventBookingRepositoryId;
import mk.ukim.finki.wp.lab.repository.jpa.EventBookingRepository;
import mk.ukim.finki.wp.lab.repository.jpa.UserRepository;
import mk.ukim.finki.wp.lab.service.EventBookingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventBookingServiceImpl implements EventBookingService {
    private final EventBookingRepository eventBookingRepository;
    private final UserRepository userRepository;

    public EventBookingServiceImpl(EventBookingRepository eventBookingRepository, UserRepository userRepository) {
        this.eventBookingRepository = eventBookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void placeBooking(String username, String eventName, String attendeeName, String attendeeAddress, long numberOfTickets) {
        User user = userRepository.findByUsername(username).orElseThrow(InvalidArgsException::new);
        EventBooking booking = new EventBooking(eventName, attendeeName, attendeeAddress, numberOfTickets, user);
        eventBookingRepository.save(booking);

    }

    @Override
    public List<EventBooking> listAll() {
        return eventBookingRepository.findAll();
    }

    @Override
    public List<EventBooking> searchEvents(String text) {
        return eventBookingRepository.searchEventBookingByEventName(text);
    }

    @Override
    public List<EventBooking> findByUser(String username) {
        return eventBookingRepository.findAllByUserUsername(username);
    }

    @Override
    public EventBooking findById(Long id) {
        return eventBookingRepository.findById(id).orElseThrow(InvalidEventBookingRepositoryId::new);
    }

    @Override
    public void deleteEventBooking(Long id) {
        eventBookingRepository.deleteById(id);
    }
}
