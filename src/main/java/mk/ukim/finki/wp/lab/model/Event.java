package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double popularityScore;
    @ManyToOne
    private Location location;
    @ManyToOne
    private Category category;
    private boolean disabled;
    public int numTickets;

    public Event() {
    }

    public Event(String name, String description, double popularityScore, Location location, Category category, int numTickets) {
        this.name = name;
        this.description = description;
        this.popularityScore = popularityScore;
        this.location = location;
        this.category = category;
        this.disabled = false;
        this.numTickets = numTickets;
    }


}
