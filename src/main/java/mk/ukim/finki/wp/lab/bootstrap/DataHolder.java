package mk.ukim.finki.wp.lab.bootstrap;

import mk.ukim.finki.wp.lab.model.*;
import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.enumerations.Role;
import mk.ukim.finki.wp.lab.repository.jpa.CategoryRepository;
import mk.ukim.finki.wp.lab.repository.jpa.EventRepository;
import mk.ukim.finki.wp.lab.repository.jpa.LocationRepository;
import mk.ukim.finki.wp.lab.repository.jpa.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static List<Event> events = new ArrayList<>();
    public static List<EventBooking> MyBookings = new ArrayList<>();
    public static List<Category> categories = new ArrayList<>();
    public static List<Location> locations = new ArrayList<>();
    public static List<User> users = new ArrayList<>();

    //Repos
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final EventRepository eventRepository;

    //PasswordEncoder
    private final PasswordEncoder passwordEncoder;

    public DataHolder(CategoryRepository categoryRepository, UserRepository userRepository,
                      LocationRepository locationRepository, EventRepository eventRepository, PasswordEncoder passwordEncoder) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.eventRepository = eventRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
            users.add(new User("damjan.sandev", passwordEncoder.encode("damjan"), "Damjan", "Sandev", Role.ROLE_ADMIN));
            users.add(new User("viktor.popovski", passwordEncoder.encode("viktor"), "Viktor", "Popovski", Role.ROLE_USER));
            this.userRepository.saveAll(users);
        }
        if (eventRepository.count() == 0) {
            events.add(new Event("Event 1", "This is description for event 1", 5.6, locationRepository.findAll().get(0), categoryRepository.findAll().get(0), 2));
            events.add(new Event("Event 2", "This is description for event 2", 6.7, locationRepository.findAll().get(1), categoryRepository.findAll().get(1), 1));
            events.add(new Event("Event 3", "This is description for event 3", 3.5, locationRepository.findAll().get(1), categoryRepository.findAll().get(1), 5));
            events.add(new Event("Event 4", "This is description for event 4", 7.77, locationRepository.findAll().get(2), categoryRepository.findAll().get(0), 3));
            events.add(new Event("Event 5", "This is description for event 5", 10.0, locationRepository.findAll().get(0), categoryRepository.findAll().get(1), 1));
            events.add(new Event("Event 6", "This is description for event 6", 9.5, locationRepository.findAll().get(2), categoryRepository.findAll().get(0), 4));
            this.eventRepository.saveAll(events);
        }

    }

}
