package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Location;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LocationRepository {
    public List<Location> findAll() {
        return DataHolder.locations;
    }

    public Optional<Location> findById(long id) {
        return DataHolder.locations.stream().filter(location -> location.getId() == id).findFirst();
    }
}
