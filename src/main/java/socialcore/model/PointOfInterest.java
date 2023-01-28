package socialcore.model;

import com.google.maps.model.LatLng;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "pointsOfInterest")
public class PointOfInterest {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private LatLng coordinates;
    @OneToMany(mappedBy = "pointOfInterest")
    private List<Comment> comments;

}
