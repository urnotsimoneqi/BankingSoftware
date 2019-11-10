package com.example.demov1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.demov1.entity.UserEntity;
import com.example.demov1.sqlite.SQLiteHelper;

public class UserDao {
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db;

    public UserDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    // Find user ID by username
    public int findUserId(String userName) {
        db = sqLiteHelper.getReadableDatabase();
        UserEntity user = new UserEntity();
        System.out.println("Current userName:"+userName);
        Cursor cursor = db.rawQuery("select * from user where user_name = '"+userName+"'", null);
        while (cursor.moveToNext()) {
            int userId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id")));
            user.setUserId(userId);
        }
        cursor.close();
        db.close();
        return user.getUserId();

    }
}
