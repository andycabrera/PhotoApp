package com.andycabrera.photoapp.api.users.shared;

import java.io.Serializable;
import java.util.List;

import com.andycabrera.photoapp.api.users.ui.models.AlbumResponseModel;

public class UserDto implements Serializable {

    private static final long serialVersionUID = 2410948320927689197L;
    private String userId;
    private String encryptedPassword;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<AlbumResponseModel> albums;

    public List<AlbumResponseModel> getAlbums() {
        return this.albums;
    }

    public void setAlbums(List<AlbumResponseModel> albums) {
        this.albums = albums;
    } 

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
