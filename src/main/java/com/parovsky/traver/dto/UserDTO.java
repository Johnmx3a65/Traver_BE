package com.parovsky.traver.dto;

import org.springframework.lang.NonNull;

import java.util.List;

public class UserDTO {
    private Long id;

    private String name;

    private String mail;

    private String password;

    private String role;

    private String verifyCode;

    private List<LocationDTO> favoriteLocations;

    public UserDTO() {

    }

    public UserDTO(Long id, String email, String name, String role) {
        this.id = id;
        this.name = name;
        this.mail = email;
        this.role = role;
    }

    public UserDTO(Long id, String name, String mail, String password, String role, String verifyCode, List<LocationDTO> favoriteLocations) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.role = role;
        this.verifyCode = verifyCode;
        this.favoriteLocations = favoriteLocations;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @NonNull
    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(@NonNull String verifyCode) {
        this.verifyCode = verifyCode;
    }

    @NonNull
    public List<LocationDTO> getFavoriteLocations() {
        return favoriteLocations;
    }

    public void setFavoriteLocations(@NonNull List<LocationDTO> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }
}
