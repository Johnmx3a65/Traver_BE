package com.parovsky.traver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter(onMethod = @__({@org.springframework.lang.NonNull}))
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long id;

    private String name;

    private String email;

    private String password;

    private String role;

    private String verifyCode;

    public UserModel(Long id, String email, String name, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

}
