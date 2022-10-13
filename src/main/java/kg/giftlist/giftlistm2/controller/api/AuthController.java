package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.controller.payload.SignupResponse;
import kg.giftlist.giftlistm2.db.service.UserService;
import kg.giftlist.giftlistm2.mapper.LoginMapper;
import kg.giftlist.giftlistm2.controller.payload.LoginRequest;
import kg.giftlist.giftlistm2.controller.payload.LoginResponse;
import kg.giftlist.giftlistm2.validation.ValidationType;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.config.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/public")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Auth API",description = "Any user can do registration and login")
public class AuthController {

    private final UserRepository repository;
    private final JwtTokenUtil jwtTokenUtil;
    private final LoginMapper loginMapper;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    @PostMapping("/login")
    @Operation(summary = "Login",description = "User can do login")
    public ResponseEntity<LoginResponse> getLogin(@RequestBody LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = repository.findByEmail(token.getName()).get();
            return ResponseEntity.ok().body(loginMapper.loginView(jwtTokenUtil.generateToken(user), ValidationType.SUCCESSFUL, user));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginMapper.loginView("", ValidationType.LOGIN_FAILED, null));
        }
    }

    @Operation(summary = "Registration", description = "Any user can register")
    @PostMapping("signup")
    public SignupResponse register(@RequestBody SignupRequest request) {
        return userService.register(request);
    }




   @GetMapping("/oauth2")
   @Operation(summary = "register with google",description = "user use email can register")
    public SignupResponse signUpGoogle(Principal principal){
       JSONObject jsonObject=new JSONObject(principal);
       SignupRequest request=new SignupRequest();
       request.setFirstName(jsonObject.getJSONObject("principal").getString("givenName"));
       request.setLastName(jsonObject.getJSONObject("principal").getString("familyName"));
       request.setEmail(jsonObject.getJSONObject("principal").getJSONObject("claims").getString("email"));
       return userService.register(request);
   }


}


