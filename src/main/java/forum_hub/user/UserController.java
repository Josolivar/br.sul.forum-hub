package forum_hub.user;

import forum_hub.topic.TopicDTO;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> get(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        return ResponseEntity.ok(
                userService.getUsersFromDatabaseAsDTO(pageable)
        );
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid UserDTO userDTO, UriComponentsBuilder uriComponentsBuilder) {
        User newUser;
        if(userService.getUserByNameFromDatabase(userDTO.name()) == null && userService.getUserByEmailFromDatabase(userDTO.email()) == null) {
            newUser = userService.saveUserToDatabase(userDTO);
            return ResponseEntity.created(
                    uriComponentsBuilder.path("/topics/{id}").buildAndExpand(newUser.getId()).toUri()
            ).body(new UserDTO(newUser));
        }
        else {
            System.out.println("Error: User already registered with this name or email.");
            return ResponseEntity.noContent().build();
        }

    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Transactional
    public ResponseEntity delete(@PathVariable long id) {
        userService.deleteUserFromDatabase(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/actives/{id}")
    @Transactional
    public ResponseEntity inactivate(@PathVariable long id) {
        userService.inactiveUserFromDatabase(id);
        return ResponseEntity.noContent().build();
    }
}