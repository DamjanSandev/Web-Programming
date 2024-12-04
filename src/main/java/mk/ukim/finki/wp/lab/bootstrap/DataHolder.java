package mk.ukim.finki.wp.lab.bootstrap;

import mk.ukim.finki.wp.lab.model.Event;
import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Location;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
 public static List<Event> events = new ArrayList<>();
    public static List<Location> locations = new ArrayList<>();
//
//
//    @PostConstruct
//    public void init() {
//        locations.add(new Location("Bajka", "partizanska Skopje", "100", "Place for karaoke and drinkis"));
//        locations.add(new Location("Arena Boris Trajkovski", "Posle Zan Mitrev", "5000", "Sports Arena"));
//        locations.add(new Location("Aurora Resort", "Berovo", "50", "Luxury resort in Berovo"));
//        locations.add(new Location("Izlet", "Do 7ca Centar", "89", "Pieme kafe i duvame"));
//        locations.add(new Location("Netaville", "Orce Nikolov Karpos 2", "70", "Fifa na Gaga i Ucenje"));
//        resetEventsDataHolder();
//    }
//
//    public static void resetEventsDataHolder() {
//        events.clear();
//        events.add(new Event("EventCenter1", "Karaoke", 10.0, locations.get(0)));
//        events.add(new Event("EventCenter2", "Sleeping", 9.1, locations.get(4)));
//        events.add(new Event("EventCenter", "Dance off", 10.0, locations.get(0)));
//        events.add(new Event("EventCenter4", "Swimming", 10.0, locations.get(1)));
//        events.add(new Event("EventCenter", "Skiing", 7.2, locations.get(2)));
//        events.add(new Event("EventCenter6", "Hiking", 1.1, locations.get(2)));
//        events.add(new Event("EventCenter", "Sleeping", 3.9, locations.get(4)));
//        events.add(new Event("EventCenter8", "Wrestling", 5.0, locations.get(1)));
//        events.add(new Event("EventCenter9", "Boxing", 8.3, locations.get(1)));
//        events.add(new Event("EventCenter10", "Sleeping", 7.3, locations.get(3)));
//    }
}
