package com.evoke.example.controller;

import com.evoke.example.entities.Role;
import com.evoke.example.entities.User;
import com.evoke.example.model.*;
import com.evoke.example.repository.UserRepository;
import com.evoke.example.util.JwtUtils;
import com.evoke.example.util.RolesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1")
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;


    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RolesUtils rolesUtils;

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //token generation
            //UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String token = this.jwtUtil.generateToken(authentication);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", "Bearer " + token);
            responseHeaders.set("Username", userDetails.getUsername());
            responseHeaders.set("Email", userDetails.getEmail());
            System.out.println("token:" + token);
            JwtResponse jwtResponse = new JwtResponse(token, // token
                    userDetails.getId(), // id
                    userDetails.getUsername(), // username
                    userDetails.getEmail(), // email
                    userDetails.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toSet()) // Set<String>
            );

            return ResponseEntity.ok().headers(responseHeaders).body(jwtResponse);
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody JwtRequest jwtRequest) {

        System.out.println("send request from post man" + jwtRequest);
        // check username exist
        if (userRepository.existsByUserName(jwtRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse(" Username already exist"));
        }
        // check email exist
        if (userRepository.existsByEmail(jwtRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse(" EmailId already exist"));
        }
        // create user
        User user = new User(jwtRequest.getUsername(), jwtRequest.getEmail(),
                encoder.encode(jwtRequest.getPassword()));
        // roles given
        Set<String> usrRoles = jwtRequest.getRole();
        // roles need to be stored in DB
        Set<Role> dbRoles = new HashSet<>();

        rolesUtils.mapRoles(usrRoles, dbRoles);
        user.setRoles(dbRoles);
        userRepository.save(user);
        System.out.println("this is the role getting from db" + user.getRoles());

        return new ResponseEntity<>(user, HttpStatus.OK);
        // return ResponseEntity.ok(new MessageResponse("User Created Successfully!"));
    }
}