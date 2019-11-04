package com.example.demov1.Entity;

import java.util.List;

public class GroupEntity {
    private Integer groupId;
    private String groupName;
    private Integer targetAmount;
    private Integer currentAmount;
    private List<UserEntity> users;

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public Integer getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
    }

    public GroupEntity(Integer groupId, String groupName, Integer targetAmount, Integer currentAmount, List<UserEntity> users) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.users = users;
    }

    // fastJson needs to call the default non-parameter constructor of the object when deserializing
    public GroupEntity() {
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(Integer targetAmount) {
        this.targetAmount = targetAmount;
    }

    @Override
    public String toString() {
        return getGroupId() + "," + getGroupName() + "," + getTargetAmount();
    }

}
