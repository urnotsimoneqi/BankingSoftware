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
 * Group: group_id, group_name, target_amount, current_amount, group_status, group_if_public
 */
public class GroupDao {
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db;

    public GroupDao(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    private ArrayList<GroupEntity> groupList;
    private List<UserEntity> userList;

    // Create Group Function
    public boolean createGroup(String groupName, int groupTarget, int groupIfPublic) {
        db = sqLiteHelper.getWritableDatabase();
        // Judge if the database is available
        if (db.isOpen()) {
            // execute insert operation
//            db.execSQL("insert into saving_group (group_name, target_amount, current_amount, group_status, " +
//                    "group_if_public) values(?,?,?,?,?)", new Object[]{groupName, groupTarget, 0, 1, groupIfPublic});
            System.out.println("Create a new group");
            db.close();
            return true;
        } else {
            return false;
        }
    }

    // List my groups
    public ArrayList<GroupEntity> listMyGroups(int userId) {
        db = sqLiteHelper.getReadableDatabase();
        List<Integer> groupIds = findMyGroupIds(userId);
        groupList = new ArrayList<>();
        for (int i = 0; i < groupIds.size(); i++) {
            Cursor cursor = db.rawQuery("select * from saving_group where group_id = " + groupIds.get(i), null);
            while (cursor.moveToNext()) {
                int groupId = groupIds.get(i);
                String groupName = cursor.getString(cursor.getColumnIndex("group_name"));
                int targetAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("target_amount")));
                int currentAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("current_amount")));
                userList = listUserInGroup(groupId);
                GroupEntity groupEntity = new GroupEntity(groupId, groupName, targetAmount, currentAmount, userList);
                groupList.add(groupEntity);
            }
            cursor.close();
        }
        System.out.println("My group list" + groupList.size());
        db.close();
        return groupList;
    }


    //     Find list of my group ids
    public List<Integer> findMyGroupIds(int userId) {
        db = sqLiteHelper.getReadableDatabase();
        List<Integer> groupIds = new ArrayList();
        Cursor cursor = db.rawQuery("select * from user_group where user_id = " + userId, null);
        while (cursor.moveToNext()) {
            int groupId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_id")));
            System.out.println(groupId);
            groupIds.add(groupId);
        }
        cursor.close();
//        db.close();
        return groupIds;
    }

    // List All Group Function
    public ArrayList<GroupEntity> listGroup() {
        db = sqLiteHelper.getReadableDatabase();
        groupList = new ArrayList<>();
//        db.execSQL("select * from group_goal");
        Cursor cursor = db.rawQuery("select * from saving_group", null);

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

    // Get a list of userId from user_group table
    public List<Integer> getUserIdOfGroup(int groupId) {
        db = sqLiteHelper.getReadableDatabase();
        List<Integer> userIds = new ArrayList();
        Cursor cursor = db.rawQuery("select * from user_group where group_id =" + groupId, null);
        while (cursor.moveToNext()) {
            int userId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id")));
            userIds.add(userId);
        }
        cursor.close();
        db.close();
        return userIds;
    }

    // Get a list of user entity from certain group, call getUserIdOfGroup
    public List<UserEntity> listUserInGroup(int groupId) {
        List<Integer> userIdList = getUserIdOfGroup(groupId);
        db = sqLiteHelper.getReadableDatabase();
        userList = new ArrayList<>();
        for (int i = 0; i < userIdList.size(); i++) {
            Cursor cursor = db.rawQuery("select * from user where user_id =" + userIdList.get(i), null);
            while (cursor.moveToNext()) {
                int userId = userIdList.get(i);
                String userName = cursor.getString(cursor.getColumnIndex("user_name"));
                String userPassword = cursor.getString(cursor.getColumnIndex("user_pwd"));
                String userAvatar = cursor.getString(cursor.getColumnIndex("user_avatar"));
                System.out.println(userAvatar);
                UserEntity userEntity = new UserEntity(userId, userName, userPassword, userAvatar);
                userList.add(userEntity);
            }
            cursor.close();
        }
//        db.close();
        return userList;
    }


}



