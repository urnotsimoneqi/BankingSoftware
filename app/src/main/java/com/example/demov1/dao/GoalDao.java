package com.example.demov1.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.demov1.sqlite.SQLiteHelper;

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
//            db.execSQL("insert into goal (user_id, goal_name, goal_target, goal_current, goal_status, goal_if_public) values(?,?,?,?,?,?)", new Object[]{userId, goalName, goalTarget, 0, 1, goalIfPublic});
            db.close();
            return true;
        } else {
            return false;
        }
    }

}
