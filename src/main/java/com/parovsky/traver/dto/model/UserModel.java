package com.parovsky.traver.dto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

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

    @Nullable
    public String getPassword() {
        return password;
    }

    @Nullable
    public String getVerifyCode() {
        return verifyCode;
    }
}
