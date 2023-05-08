package com.week10springsecurity.dto;


import com.week10springsecurity.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
     private  String userName;
     private String email;
     private String password;
     private Role role;


}
