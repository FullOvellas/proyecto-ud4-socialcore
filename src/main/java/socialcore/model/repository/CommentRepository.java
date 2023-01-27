package socialcore.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import socialcore.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
