package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Event;
import mk.ukim.finki.wp.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EventRepository {

    public List<Event> findAll() {
        return DataHolder.events;
    }

    public List<Event> searchEvents(String text) {
        return DataHolder.events.stream().filter(event -> event.getName().contains(text) || event.getDescription().contains(text)).toList();
    }

    public Optional<Event> save(String name, String description, Double score, Location location) {

        Event event = new Event(name, description, score, location);
        DataHolder.events.removeIf(e -> e.getName().equals(event.getName()));
        DataHolder.events.add(event);
        return Optional.of(event);
    }

    public Optional<Event> findById(Long id) {
        return DataHolder.events.stream().filter(event -> event.getId().equals(id)).findFirst();
    }

    public void updateEvent(Long eventid, String name, String description, Double score, Location location) {
        Optional<Event> event = findById(eventid);
        if (event.isPresent()) {
            Event e = event.get();
            e.setName(name);
            e.setDescription(description);
            e.setPopularityScore(score);
            e.setLocation(location);
        }

    }

    public void deleteById(Long eventid) {
        DataHolder.events.removeIf(e -> e.getId().equals(eventid));
    }
}
