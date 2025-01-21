package forum_hub.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotBlank
        long id,
        @NotBlank
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Size(min = 6, max = 8, message = "The password must be between 6 and 8 characters.")
        String pass
) {
        public UserDTO(User user) {
                this(user.getId(), user.getName(), user.getEmail(), user.getPass());
        }
}
