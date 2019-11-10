package com.example.demov1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.demov1.entity.GoalEntity;
import com.example.demov1.entity.GroupEntity;
import com.example.demov1.entity.UserEntity;
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
    private List<GoalEntity> goalList;

    // Create Group Function, return group entity
    public GroupEntity createGroup(String groupName, int groupTarget, int groupIfPublic) {
        db = sqLiteHelper.getWritableDatabase();
        int groupId = 0;
        GroupEntity group = new GroupEntity();
        // Judge if the database is available
        if (db.isOpen()) {
            // execute insert operation
            db.execSQL("insert into saving_group (group_name, target_amount, current_amount, group_status, " +
                    "group_if_public) values(?,?,?,?,?)", new Object[]{groupName, groupTarget, 0, 1, groupIfPublic});
            System.out.println("Create a new group");
            groupId = selectLastInsertGroup();
            group.setGroupId(groupId);
            group.setGroupName(groupName);
            group.setTargetAmount(groupTarget);
            group.setCurrentAmount(0);
            group.setGroupStatus(1);
            group.setGroupIfPublic(groupIfPublic);
            db.close();
            return group;
        } else {
            return group;
        }
    }

    // Find certain group function, return group entity
    public GroupEntity selectGroup(int groupId) {
        db = sqLiteHelper.getReadableDatabase();
        GroupEntity group = new GroupEntity();
        Cursor cursor = db.rawQuery("select * from saving_group where group_id = " + groupId, null);
        while (cursor.moveToNext()) {
            String groupName = cursor.getString(cursor.getColumnIndex("group_name"));
            int targetAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("target_amount")));
            int currentAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("current_amount")));
            int groupStatus = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_status")));
            int groupIfPublic = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_if_public")));
            group = new GroupEntity(groupId, groupName, targetAmount, currentAmount,
                    groupStatus, groupIfPublic, userList, goalList);
        }
        db.close();
        return group;
    }


    // select last_insert_rowid() from table_name
    public int selectLastInsertGroup() {
        db = sqLiteHelper.getReadableDatabase();
        String sql = "select last_insert_rowid() from " + "saving_group";
        Cursor cursor = db.rawQuery(sql, null);
        int a = -1;
        if (cursor.moveToFirst()) {
            a = cursor.getInt(0);
        }
        db.close();
        System.out.println("The last insert group id is:" + a);
        return a;
    }

    // List my groups
    public ArrayList<GroupEntity> listMyGroups(int userId) {
        db = sqLiteHelper.getReadableDatabase();
        List<Integer> groupIds = findMyGroupIds(userId);
        groupList = new ArrayList<>();
        for (int i = 0; i < groupIds.size(); i++) {
            Cursor cursor = db.rawQuery("select * from saving_group where group_id = "
                    + groupIds.get(i), null);
            while (cursor.moveToNext()) {
                int groupId = groupIds.get(i);
                String groupName = cursor.getString(cursor.getColumnIndex("group_name"));
                int targetAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("target_amount")));
                int currentAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("current_amount")));
                int groupStatus = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_status")));
                int groupIfPublic = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_if_public")));
                userList = listUserInGroup(groupId);
                goalList = listGoalInGroup(groupId);
                GroupEntity groupEntity = new GroupEntity(groupId, groupName, targetAmount, currentAmount,
                        groupStatus, groupIfPublic, userList, goalList);
                updateGroupGoal(groupId);
                groupList.add(groupEntity);
            }
            cursor.close();
        }
        System.out.println("My group list" + groupList.size());
        db.close();
        return groupList;
    }


    // Find list of my group ids
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

    // List public group which is uncompleted and does not include the current user,
    public ArrayList<GroupEntity> listGroup() {
        db = sqLiteHelper.getReadableDatabase();
        groupList = new ArrayList<>();
//        db.execSQL("select * from group_goal");
        Cursor cursor = db.rawQuery("select * from saving_group where group_status=1 and group_if_public=0", null);

        while (cursor.moveToNext()) {
            int groupId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_id")));
            String groupName = cursor.getString(cursor.getColumnIndex("group_name"));
            int targetAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("target_amount")));
            int currentAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("current_amount")));
            int groupStatus = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_status")));
            int groupIfPublic = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_if_public")));
            userList = listUserInGroup(groupId);
            goalList = listGoalInGroup(groupId);
            GroupEntity groupEntity = new GroupEntity(groupId, groupName, targetAmount, currentAmount,
                    groupStatus, groupIfPublic, userList, goalList);
            updateGroupGoal(groupId);
            groupList.add(groupEntity);
        }
        cursor.close();
        db.close();
        return groupList;
    }

//    // Get a list of groupId from user_group table
//    public List<Integer> getGroupIdOfGroup(int userId) {
//        db = sqLiteHelper.getReadableDatabase();
//        List<Integer> groupIds = new ArrayList();
//        Cursor cursor = db.rawQuery("select * from user_group where user_id <> " + userId, null);
//        while (cursor.moveToNext()) {
//            int groupId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_id")));
//            groupIds.add(groupId);
//        }
//        cursor.close();
//        db.close();
//        return groupIds;
//    }

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
                UserEntity userEntity = new UserEntity(userId, userName, userPassword, userAvatar);
                userList.add(userEntity);
            }
            cursor.close();
        }
//        db.close();
        return userList;
    }

    // User join a group
    public boolean joinGroup(int userId, int groupId) {
        db = sqLiteHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("insert into user_group (user_id, group_id) values(?,?)", new Object[]{userId, groupId});
            System.out.println("User join a group");
            db.close();
            return true;
        } else {
            return false;
        }
    }

    // Get a list of goals from certain group
    public List<GoalEntity> listGoalInGroup(int groupId) {
        db = sqLiteHelper.getReadableDatabase();
        goalList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from goal where group_id = " + groupId, null);
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

//        db.close();
        return goalList;
    }

    // update Group target and current goal
    public void updateGroupGoal(int groupId) {
        goalList = listGoalInGroup(groupId);
        GroupEntity group = selectGroup(groupId);
        System.out.println("update Group Goal"+group.getGroupId());
        int groupTarget = 0;
        int groupCurrent = 0;
        for (int i = 0; i < goalList.size(); i++) {
            GoalEntity goal = goalList.get(i);
            groupTarget += goal.getGoalTarget();
            groupCurrent += goal.getGoalCurrent();
        }
        group.setTargetAmount(groupTarget);
        group.setCurrentAmount(groupCurrent);
        db = sqLiteHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.execSQL("update saving_group set target_amount=?, current_amount=? where group_id=?", new Object[]
                    {group.getTargetAmount(), group.getCurrentAmount(), groupId}); // Note: update multiple data field
            if (groupTarget == groupCurrent){
                db.execSQL("update saving_group set group_status=? where group_id=?", new Object[]
                        {0, groupId});
            }
        }
    }

    // list group in group fragment
    public void listGroupUncompletedAndUnjoined() {
//        db = sqLiteHelper.getReadableDatabase();
//        groupList = new ArrayList<>();
//        Cursor cursor = db.rawQuery("select * from saving_group where group_status=1 and group_if_public=0", null);
//        while (cursor.moveToNext()) {
//            int groupId = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_id")));
//            String groupName = cursor.getString(cursor.getColumnIndex("group_name"));
//            int targetAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("target_amount")));
//            int currentAmount = Integer.parseInt(cursor.getString(cursor.getColumnIndex("current_amount")));
//            int groupStatus = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_status")));
//            int groupIfPublic = Integer.parseInt(cursor.getString(cursor.getColumnIndex("group_if_public")));
//            userList = listUserInGroup(groupId);
//            goalList = listGoalInGroup(groupId);
//            GroupEntity groupEntity = new GroupEntity(groupId, groupName, targetAmount, currentAmount,
//                    groupStatus, groupIfPublic, userList, goalList);
//            updateGroupGoal(groupId);
//            groupList.add(groupEntity);
//        }
//        cursor.close();
//        db.close();
//        return groupList;

    }

}



