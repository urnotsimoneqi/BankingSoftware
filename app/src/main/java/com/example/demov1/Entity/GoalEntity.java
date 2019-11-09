package com.example.demov1.Entity;

import java.util.List;

public class GoalEntity {
    private Integer goalId;
    private Integer userId;
    private Integer groupId; // groupId=0 represents it's an individual goal? / can be null
    private String goalName;
    private Integer goalTarget;
    private Integer goalCurrent;
    private Integer goalStatus;
    private Integer goalIfPublic;

    public Integer getGoalId() {
        return goalId;
    }

    public void setGoalId(Integer goalId) {
        this.goalId = goalId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public Integer getGoalTarget() {
        return goalTarget;
    }

    public void setGoalTarget(Integer goalTarget) {
        this.goalTarget = goalTarget;
    }

    public Integer getGoalCurrent() {
        return goalCurrent;
    }

    public void setGoalCurrent(Integer goalCurrent) {
        this.goalCurrent = goalCurrent;
    }

    public Integer getGoalStatus() {
        return goalStatus;
    }

    public void setGoalStatus(Integer goalStatus) {
        this.goalStatus = goalStatus;
    }

    public Integer getGoalIfPublic() {
        return goalIfPublic;
    }

    public void setGoalIfPublic(Integer goalIfPublic) {
        this.goalIfPublic = goalIfPublic;
    }

    public GoalEntity(Integer goalId, Integer userId, Integer groupId, String goalName,
                      Integer goalTarget, Integer goalCurrent,
                      Integer goalStatus, Integer goalIfPublic) {
        this.goalId = goalId;
        this.userId = userId;
        this.groupId = groupId;
        this.goalName = goalName;
        this.goalTarget = goalTarget;
        this.goalCurrent = goalCurrent;
        this.goalStatus = goalStatus;
        this.goalIfPublic = goalIfPublic;
    }

    // fastJson needs to call the default non-parameter constructor of the object when deserializing
    public GoalEntity() {
    }
}
