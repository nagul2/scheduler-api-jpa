package spring.advanced.schedulerjpa.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.advanced.schedulerjpa.comment.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
