package forum_hub.topic;

import forum_hub.message.Message;
import forum_hub.message.MessageDTO;
import forum_hub.user.User;
import forum_hub.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicService {


    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private UserService userService;

    public Page<TopicDTO> getAllTopicsFromDatabaseAsDTO(Pageable pageable) {
        return topicRepository.findAll(pageable).map(TopicDTO::new);
    }

    public Page<TopicDTO> getActiveTopicsFromDatabaseAsDTO(Pageable pageable) {
        return topicRepository.findAllByActiveTrue(pageable).map(TopicDTO::new);
    }
    public TopicDTO getTopicByIdFromDatabase(long id) {
        return new TopicDTO(topicRepository.getReferenceById(id));
    }

    public Topic saveTopicToDatabase(TopicDTO topicDTO) {
        Topic newTopic = new Topic(topicDTO, userService.getUserByNameFromDatabase(topicDTO.author()));
        topicRepository.save(newTopic);
        return newTopic;
    }

    public boolean saveMessageToTopicInDatabase(MessageDTO messageDTO, long idTopic) {
        Topic topic = topicRepository.getReferenceById(idTopic);
        User user = userService.getUserByNameFromDatabase(messageDTO.userName());
        if(topic != null && topic.isActive() && user!= null && user.isActive())
            return topic.getMessages().add(new Message(messageDTO.content(), user));
        return false;
    }

    public void deleteTopicFromDatabase(long id) {
        topicRepository.deleteById(id);
    }

    public void inactiveTopicFromDatabase(long id) {  ///?????? faz ou não  a verificação, para evitar nullpointer?
        topicRepository.getReferenceById(id).setActive(false);
    }
}