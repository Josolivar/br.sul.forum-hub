package forum_hub.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    public Topic findByTitle(String title);

    Page<Topic> findAllByActiveTrue(Pageable pageable);
}