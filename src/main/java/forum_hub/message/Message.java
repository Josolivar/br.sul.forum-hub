package forum_hub.message;

import forum_hub.topic.Topic;
import forum_hub.user.User;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String content;
    private User user;
    private LocalDateTime dateOfCreation;

    public Message(String str, User user) {
        this.content = str;
        this.user = user;
        this.dateOfCreation = LocalDateTime.now();
    }
    @Override
    public String toString() {
        return content + "\n*Message sent by " + user.getName() + ", on " + this.dateOfCreation;
    }
}