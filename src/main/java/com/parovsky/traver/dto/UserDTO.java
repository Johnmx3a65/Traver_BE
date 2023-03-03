package com.parovsky.traver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter(onMethod = @__({@org.springframework.lang.NonNull}))
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;

    private String name;

    private String mail;

    private String password;

    private String role;

    private String verifyCode;

    public UserDTO(Long id, String email, String name, String role) {
        this.id = id;
        this.name = name;
        this.mail = email;
        this.role = role;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

}
