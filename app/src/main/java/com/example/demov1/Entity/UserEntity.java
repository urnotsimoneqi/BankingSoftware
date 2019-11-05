package com.example.demov1.Entity;

public class UserEntity {
    private Integer userId;
    private String userName;
    private String userPassword;
    private String userAvatar;

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Integer getUserId() { return userId; }

    public UserEntity(Integer userId, String userName, String userPassword, String userAvatar) {
        this.userId = userId;
//        this.groupId = groupId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userAvatar = userAvatar;
    }

    // fastJson needs to call the default non-parameter constructor of the object when deserializing
    public UserEntity(){

    }

    public void setUserId(Integer userId) { this.userId = userId; }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
