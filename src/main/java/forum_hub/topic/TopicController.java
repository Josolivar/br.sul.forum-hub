package forum_hub.topic;

import forum_hub.message.MessageDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public ResponseEntity<Page<TopicDTO>> get(@PageableDefault(size = 10, sort = {"dateOfCreation"}) Pageable pageable) {
        return ResponseEntity.ok(topicService.getAllTopicsFromDatabaseAsDTO(pageable));
    }

    @GetMapping("/actives")
    public ResponseEntity<Page<TopicDTO>> getActives(@PageableDefault(size = 10, sort = {"dateOfCreation"}) Pageable pageable) {
        return ResponseEntity.ok(topicService.getActiveTopicsFromDatabaseAsDTO(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDTO> getIndicatedTopic(@PathVariable long id) {
        return ResponseEntity.ok(topicService.getTopicByIdFromDatabase(id));
    }

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid TopicDTO topicDTO, UriComponentsBuilder uriComponentsBuilder) {
        Topic newTopic = topicService.saveTopicToDatabase(topicDTO);
        return ResponseEntity.created(
                uriComponentsBuilder.path("/topics/{id}").buildAndExpand(newTopic.getId()).toUri()
        ).body(new TopicDTO(newTopic));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity sendNewMessage(@RequestBody @Valid MessageDTO messageDTO, @PathVariable long id) {
        if(topicService.saveMessageToTopicInDatabase(messageDTO, id))
            return ResponseEntity.ok(messageDTO);
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    //@Secured("ROLE_ADMIN")
    @Transactional
    public ResponseEntity delete(@PathVariable long id) {
        topicService.deleteTopicFromDatabase(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/actives/{id}")
    @Transactional
    public ResponseEntity inactivate(@PathVariable long id) {
        topicService.inactiveTopicFromDatabase(id);
        return ResponseEntity.noContent().build();
    }
}