package ge.ibsu.demo.security.controller;

import ge.ibsu.demo.dto.AuthenticationResponse;
import ge.ibsu.demo.dto.LoginData;
import ge.ibsu.demo.dto.RegistrationRequest;
import ge.ibsu.demo.security.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegistrationRequest rq) throws Exception {
        return this.userService.register(rq);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginData rq) {
        return this.userService.login(rq);
    }
}
