package com.parovsky.traver.dto.model;

import com.parovsky.traver.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter(onMethod = @__({@org.springframework.lang.NonNull}))
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    private String password;

    @NotNull
    private Role role;

    private String verifyCode;

    @Nullable
    public Long getId() {
        return id;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    @Nullable
    public String getVerifyCode() {
        return verifyCode;
    }
}
