package mk.ukim.finki.wp.lab.service.impl;

import jakarta.transaction.Transactional;
import mk.ukim.finki.wp.lab.model.Category;
import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.model.Location;
import mk.ukim.finki.wp.lab.model.exception.LocationNotFoundException;
import mk.ukim.finki.wp.lab.repository.jpa.CategoryRepository;
import mk.ukim.finki.wp.lab.repository.jpa.EventRepository;
import mk.ukim.finki.wp.lab.repository.jpa.LocationRepository;
import mk.ukim.finki.wp.lab.service.EventService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static mk.ukim.finki.wp.lab.service.specifications.FieldFilterSpecification.filterEquals;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;


    public EventServiceImpl(EventRepository eventRepository, LocationRepository LocationRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.locationRepository = LocationRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Event> listAll() {
        return this.eventRepository.findAll();
    }

    @Override
    public List<Event> searchEvent(String text) {
        return this.eventRepository.findByNameLike(text);
    }

    @Override
    public Optional<Event> save(String name, String description, Double score, Long locationId, Long categoryId, int numTickets) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new LocationNotFoundException(locationId));
        Optional<Category> category = categoryRepository.findById(categoryId);
        return Optional.of(this.eventRepository.save(new Event(name, description, score, location, category.get(), numTickets)));
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public void updateEvent(Long eventid, String name, String description, Double score, Long locationId, Long categoryId, int numTickets) {
        Location location = locationRepository.findById(locationId).orElseThrow(() -> new LocationNotFoundException(locationId));
        Event event = eventRepository.findById(eventid).orElseThrow(() -> new LocationNotFoundException(eventid));
        Optional<Category> category = categoryRepository.findById(categoryId);

        event.setName(name);
        event.setDescription(description);
        event.setPopularityScore(score);
        event.setLocation(location);
        event.setCategory(category.get());
        event.setNumTickets(numTickets);
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventid) {
        this.eventRepository.deleteById(eventid);
    }

    @Override
    public Page<Event> findPage(Long categoryId, Long locationId, Integer pageNum, Integer pageSize) {
        Specification<Event> specification = Specification
                .where(filterEquals(Event.class, "category.id", categoryId))
                .and(filterEquals(Event.class, "location.id", locationId));

        return this.eventRepository.findAll(
                specification,
                PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "name"))
        );
    }

    @Override
    public Optional<Event> findByName(String event) {
        return eventRepository.findByName(event);
    }
    @Transactional
    @Override
    public void reserveCard(Long id, int numTickets) {
        eventRepository.reserveTickets(id, numTickets);
    }


}
