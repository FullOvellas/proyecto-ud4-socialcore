package socialcore.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import socialcore.model.PointOfInterest;

@Repository
public interface PointOfInterestRepository extends JpaRepository<PointOfInterest, Long> {
}
