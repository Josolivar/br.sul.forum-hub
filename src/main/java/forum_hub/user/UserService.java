package forum_hub.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Page<UserDTO> getUsersFromDatabaseAsDTO(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }

    public User getUserByNameFromDatabase(String name) {
        return userRepository.findByName(name);
    }

    public User saveUserToDatabase(UserDTO userDTO) {
        return userRepository.save(new User(userDTO));
    }

    public User getUserByEmailFromDatabase(String email) { return userRepository.findByEmail(email); }

    public void deleteUserFromDatabase(long id) { userRepository.deleteById(id); }

    public void inactiveUserFromDatabase(long id) {
        User user = userRepository.getReferenceById(id);
        if(user != null)
            user.setActive(false);
    }
}
