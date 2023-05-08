package com.week10springsecurity.service;





import com.week10springsecurity.auth.AuthenticationResponse;
import com.week10springsecurity.dto.UserDTO;

import java.util.List;

public interface UserService {
    AuthenticationResponse createUser(UserDTO userDTO);
    public List<UserDTO> getALllUser();

    public  AuthenticationResponse loginUser(UserDTO userDTO);

}
