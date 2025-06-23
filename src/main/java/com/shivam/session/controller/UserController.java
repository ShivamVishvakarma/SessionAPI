package com.shivam.session.controller;

import com.shivam.session.Repository.UserRepository;
import com.shivam.session.config.JwtService;
import com.shivam.session.dto.UserDTO;
import com.shivam.session.entity.User;
import com.shivam.session.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

         private final JwtService jwtService;
         private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser( @Validated @RequestBody UserDTO userDTO) {
        User user = mapToEntity(userDTO);
        User savedUser = userService.createUser(user);
        return new ResponseEntity<>(mapToDTO(savedUser), HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> LoginUser(@Validated @RequestParam String username, @RequestParam String password) {
        User user = userService.loginUser(username, password);
        if(user!= null){

        String jwtToken = jwtService.generateToken((UserDetails) user);

            userRepository.save(user); // Save the token in the database
            return new ResponseEntity<>(mapToDTO(user), HttpStatus.OK);
        }
        if (user != null) {
            return new ResponseEntity<>(mapToDTO(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id)  {
        User user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(mapToDTO(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    private User mapToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

}
