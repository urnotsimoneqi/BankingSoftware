package com.example.demov1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.demov1.Entity.GroupEntity;
import com.example.demov1.Entity.UserEntity;
import com.example.demov1.sqlite.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qxm on 2019/11/03.
 */
public class GroupDao {
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db;

    public GroupDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    private ArrayList<GroupEntity> groupList;
    private List<UserEntity> userList;

//    // Add Group Function
//    public boolean addGroup(String groupName, int groupTarget) {
//        db = sqLiteHelper.getWritableDatabase();
//        // Judge if the database is available
//        if (db.isOpen()) {
//            // execute insert operation
//            db.execSQL("insert into group_goal (group_name, target_amount) values(?,?)", new Object[]{groupName, groupTarget});
//            db.close();
//            return true;
//        } else {
//            return false;
//        }
//    }

    // List Group Function
    public ArrayList<GroupEntity> listGroup() {
        db = sqLiteHelper.getReadableDatabase();
        groupList = new ArrayList<>();
//        db.execSQL("select * from group_goal");
        Cursor cursor = db.rawQuery("select * from group_goal", null);

        while (cursor.moveToNext()) {
            int groupId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_id")));
            String groupName = cursor.getString(cursor.getColumnIndex("group_name"));
            int targetAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("target_amount")));
            int currentAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("current_amount")));
            userList = listUserInGroup(groupId);
            GroupEntity groupEntity = new GroupEntity(groupId, groupName, targetAmount, currentAmount, userList);
            groupList.add(groupEntity);
        }
        cursor.close();
        db.close();
        return groupList;
    }

    // List Function: list users of certain group
    public List<UserEntity> listUserInGroup(int groupId) {
        db = sqLiteHelper.getReadableDatabase();
        userList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from user where group_id ="+groupId, null);


        while (cursor.moveToNext()) {
            int userId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id")));
//            int groupId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_id")));
            String userName = cursor.getString(cursor.getColumnIndex("user_name"));
            String userPassword = cursor.getString(cursor.getColumnIndex("user_pwd"));

            UserEntity userEntity = new UserEntity(userId, groupId, userName, userPassword);
            userList.add(userEntity);
        }
        cursor.close();
        db.close();
        return userList;
    }


}



