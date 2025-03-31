package spring.advanced.schedulerjpa.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.advanced.schedulerjpa.comment.domain.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByScheduleId(Long scheduleId);

    Optional<Comment> findByIdAndScheduleId(Long id, Long scheduleId);
}
