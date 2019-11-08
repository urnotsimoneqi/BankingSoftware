package com.example.demov1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.demov1.Entity.GoalEntity;
import com.example.demov1.sqlite.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

// Goal: goal_id, user_id, group_id, goal_target, goal_status, goal_if_public
public class GoalDao {
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db;

    public GoalDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    // Create New Goal Function
    public boolean createGoal(int userId, String goalName, int goalTarget, int goalIfPublic) {
        db = sqLiteHelper.getWritableDatabase();
        // Judge if the database is available
        if (db.isOpen()) {
            // execute insert operation
            db.execSQL("insert into goal (user_id, goal_name, goal_target, goal_current, goal_status, " +
                    "goal_if_public) values(?,?,?,?,?,?)", new Object[]{userId, goalName, goalTarget, 0, 1, goalIfPublic});
            System.out.println("Create a new goal");
            db.close();
            return true;
        } else {
            return false;
        }
    }

    // Find completed goal by userId
    public List<GoalEntity> findGoalByUserId(int userId) {
        db = sqLiteHelper.getReadableDatabase();
        List<GoalEntity> goalList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from goal where user_id = "+userId, null);
        while (cursor.moveToNext()) {
            int goalId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_id")));
            int groupId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_id")));
            String goalName = cursor.getString(cursor.getColumnIndex("goal_name"));
            int goalTarget = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_target")));
            int goalCurrent = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_current")));
            int goalStatus = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_status")));
            int goalIfPublic = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_if_public")));
            GoalEntity goal = new GoalEntity();
            goal.setGoalId(goalId);
            goal.setUserId(userId);
            goal.setGroupId(groupId);
            goal.setGoalName(goalName);
            goal.setGoalTarget(goalTarget);
            goal.setGoalCurrent(goalCurrent);
            goal.setGoalStatus(goalStatus);
            goal.setGoalIfPublic(goalIfPublic);
            goalList.add(goal);
        }
        cursor.close();
        db.close();
        return goalList;
    }

}
