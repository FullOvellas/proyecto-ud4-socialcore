package socialcore.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String text;
    @ManyToOne
    @JoinColumn(name = "pointOfInterest")
    private PointOfInterest pointOfInterest;

}
