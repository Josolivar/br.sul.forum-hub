package forum_hub.topic;

import forum_hub.message.Message;
import forum_hub.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topics")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    @ManyToOne
    private User author;
    private String course;
    private LocalDateTime dateOfCreation;
    @ElementCollection
    @CollectionTable(name="topic_messages", joinColumns=@JoinColumn(name="topic_id"))
    private List<Message> messages;
    private boolean active = true;

    public Topic(TopicDTO topicDTO, User user) {
        this.title = topicDTO.title();
        this.author = user;
        this.course = topicDTO.course();
        this.dateOfCreation = LocalDateTime.now();
        this.messages = new ArrayList<>();
    }
}