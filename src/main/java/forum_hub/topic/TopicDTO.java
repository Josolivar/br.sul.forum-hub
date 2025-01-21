package forum_hub.topic;

import forum_hub.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record TopicDTO(
        @NotBlank
        long id,
        @NotBlank(message = "Title required to validate request")
        String title,
        @NotBlank
        String author,
        String course,
        LocalDateTime dateOfCreation,
        List<String> messages) {

    public TopicDTO(Topic topic) {
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getAuthor().getName(),
                topic.getCourse(),
                topic.getDateOfCreation(),
                topic.getMessages().stream().map(m -> m.toString()).toList()
        );
    }
}
