package socialcore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PointOfInterest {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

}
