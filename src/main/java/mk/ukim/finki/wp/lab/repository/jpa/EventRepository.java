package mk.ukim.finki.wp.lab.repository.jpa;

import mk.ukim.finki.wp.lab.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByNameLike(String name);

    Optional<Event> findByName(String name);

    List<Event> findAllByLocation_Id(Long locationId);

    List<Event> findAllByCategory_Id(Long categoryId);

    List<Event> findAllByNameLike(String name);

    List<Event> findAllByLocation_IdAndCategory_Id(Long locationId, Long categoryId);

    Page<Event> findAll(Specification<Event> specification, Pageable page);

    @Modifying
    @Query("UPDATE Event e" +
            " SET e.numTickets = e.numTickets - :numTickets " +
            "WHERE e.id = :eventId AND e.numTickets >= :numTickets")
    void reserveTickets(@Param("eventId") Long eventId, @Param("numTickets") int numTickets);
}
