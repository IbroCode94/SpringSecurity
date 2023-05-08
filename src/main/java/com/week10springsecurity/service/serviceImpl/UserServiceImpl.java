package com.week10springsecurity.service.serviceImpl;



import com.week10springsecurity.auth.AuthenticationResponse;
import com.week10springsecurity.dto.UserDTO;
import com.week10springsecurity.entity.Token;
import com.week10springsecurity.entity.User;
import com.week10springsecurity.enums.Role;
import com.week10springsecurity.exception.UserAlreadyExistExceptions;
import com.week10springsecurity.repository.TokenRepository;
import com.week10springsecurity.repository.UserRepository;
import com.week10springsecurity.service.UserService;
import com.week10springsecurity.utils.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
   private final UserRepository userRepository;
   private final ModelMapper modelMapper;
   private  final PasswordEncoder passwordEncoder;
   private final JwtService jwtService;
   private final TokenRepository tokenRepository;




    @Override
    public AuthenticationResponse createUser(UserDTO userDTO) {
      User users = new User();
        users.setUserName(userDTO.getUserName());
        users.setEmail(userDTO.getEmail());
        users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        users.setRole(userDTO.getRole());
      User newUser = userRepository.save(users);
//      String token = jwtService.generateToken(users);
//      Token tokenSave = Token.builder()
//              .token(token)
//              .user(newUser)
//              .isExpired(false)
//              .isRevoked(false)
//              .build();
//      tokenRepository.save(tokenSave);
      return AuthenticationResponse.builder()
              .token("token")
              .build();
    }



    @Override
    public AuthenticationResponse loginUser(UserDTO userDTO) {
        User newUser = userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User does not exist"));
        String token = jwtService.generateToken(newUser);
        Token tokenToSave = Token.builder()
                .token(token)
                .user(newUser)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(tokenToSave);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }



@Override
    public List<UserDTO> getALllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        return userDTOs;
    }

}
