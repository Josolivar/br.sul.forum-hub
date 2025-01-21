package forum_hub.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/login")
public class UserAuthenticationController {

    private static final String ISSUER = "Forum Hub API";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("${api.security.token.passcode}")
    private String passcode;

    @PostMapping
    public ResponseEntity login(@RequestBody @Valid UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.email(), userDTO.pass())
        );
        return ResponseEntity.ok(new TokenDTO(getToken(userDTO)));
    }

    private record TokenDTO(String tokenJWT){}

    private String getToken(UserDTO userDTO) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userDTO.email())
                    .withClaim("user_id: ", userDTO.id())
                    .withClaim("user_name: ", userDTO.name())
                    .withExpiresAt(LocalDateTime.now().plusMinutes(100).toInstant(ZoneOffset.of("-03:00")))
                    .sign(Algorithm.HMAC256(passcode));
        } catch(JWTCreationException exception) {
            throw new RuntimeException("error generating JWT token", exception);
        }
    }

    public String retrieveSubject(String tokenJWT) {
        try {
            return JWT.require(Algorithm.HMAC256(passcode))
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Invalid or expired JWT token", exception);
        }
    }
}