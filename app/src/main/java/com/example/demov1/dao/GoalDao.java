package com.example.demov1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.demov1.Entity.GoalEntity;
import com.example.demov1.sqlite.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

// Goal: goal_id, user_id, group_id, goal_name, goal_target, goal_current, goal_status, goal_if_public
public class GoalDao {
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db;

    public GoalDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    // Create New Goal Function
    public boolean createGoal(int userId, int groupId, String goalName, int goalTarget, int goalIfPublic) {
        db = sqLiteHelper.getWritableDatabase();
        // Judge if the database is available
        if (db.isOpen()) {
            // execute insert operation
            db.execSQL("insert into goal (user_id, group_id, goal_name, goal_target, goal_current, goal_status, " +
                    "goal_if_public) values(?,?,?,?,?,?,?)", new Object[]{userId, groupId, goalName, goalTarget, 0, 1, goalIfPublic});
            System.out.println("Create a new goal");
            db.close();
            return true;
        } else {
            return false;
        }
    }

    // Find goalList by userId
    public List<GoalEntity> findGoalByUserId(int userId) {
        db = sqLiteHelper.getReadableDatabase();
        List<GoalEntity> goalList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from goal where user_id = "+userId, null);
        while (cursor.moveToNext()) {
            int goalId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_id")));
            Integer groupId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_id")));
            String goalName = cursor.getString(cursor.getColumnIndex("goal_name"));
            int goalTarget = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_target")));
            int goalCurrent = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_current")));
            int goalStatus = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_status")));
            int goalIfPublic = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_if_public")));
            GoalEntity goal = new GoalEntity(goalId, userId, groupId, goalName, goalTarget, goalCurrent, goalStatus, goalIfPublic);
            goalList.add(goal);
        }
        cursor.close();
        db.close();
        return goalList;
    }

    // Add money to the goal
    public boolean depositMoney(int userId, int groupId, int addMoney){
        db = sqLiteHelper.getWritableDatabase();
        System.out.println("add money"+addMoney);
        int goalCurrent = 0;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from goal where user_id = "+userId+" and group_id= "+groupId, null);
            while (cursor.moveToNext()) {
                goalCurrent = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_current")));
                System.out.println("current money"+goalCurrent);
            }
            int money = addMoney+goalCurrent;
            db.execSQL("update goal set goal_current=? where user_id=? and group_id=?", new Object[]
                    {money, userId, groupId});
            System.out.println("after add money"+money);
            db.close();
            return true;
        } else {
            return false;
        }
    }


    // Find all the goal to the group
    public List<GoalEntity> findAllGoalInGroup(int groupId){
        db = sqLiteHelper.getReadableDatabase();
        List<GoalEntity> goalList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from goal where group_id = "+groupId, null);
        while (cursor.moveToNext()) {
            int goalId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_id")));
            int userId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id")));
            String goalName = cursor.getString(cursor.getColumnIndex("goal_name"));
            int goalTarget = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_target")));
            int goalCurrent = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_current")));
            int goalStatus = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_status")));
            int goalIfPublic = Integer.parseInt(cursor.getString(cursor.getColumnIndex("goal_if_public")));
            GoalEntity goal = new GoalEntity(goalId, userId, groupId, goalName, goalTarget, goalCurrent, goalStatus, goalIfPublic);
            goalList.add(goal);
        }
        cursor.close();
        db.close();
        return goalList;
    }


//    // Get current amount of the goal
//    public int depositMoney(int userId, int groupId, int money){
}
