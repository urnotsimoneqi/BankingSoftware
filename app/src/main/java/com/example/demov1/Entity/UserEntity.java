package com.example.demov1.Entity;

public class UserEntity {
    private Integer userId;
    private Integer groupId;
    private String userName;
    private String userPassword;

    public Integer getUserId() { return userId; }

    public UserEntity(Integer userId, Integer groupId, String userName, String userPassword) {
        this.userId = userId;
        this.groupId = groupId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    // fastJson needs to call the default non-parameter constructor of the object when deserializing
    public UserEntity(){

    }

    public void setUserId(Integer userId) { this.userId = userId; }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

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
