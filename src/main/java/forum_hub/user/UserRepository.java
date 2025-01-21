package forum_hub.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByName(String name);

    public User findByEmail(String email);

    public <T> T findByEmail(String email, Class<T> t);
}