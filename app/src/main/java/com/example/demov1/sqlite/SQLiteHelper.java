package com.example.demov1.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qxm on 2019/10/18.
 */

public class SQLiteHelper extends SQLiteOpenHelper{

    /*Four parameters of SQLiteHelper: context, name of database, null, version number*/
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /*The above is too complicated, so we need to overload a simple method: Complete the database creation through the constructor*/
    public SQLiteHelper(Context context){
        super(context,"mydb",null,1);
    }

    /*Create data table through onCreate function*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        // group_status: 0 represents the goal is completed while 1 represents the goal is in progress
        // group_if_public: 0 represents that the goal is public while 1 represents the goal is private
        db.execSQL("create table saving_group (group_id integer primary key autoincrement, group_name varchar(20), " +
                "target_amount integer, current_amount integer, group_status integer, group_if_public integer)");
        db.execSQL("insert into saving_group values(1,'Group1',10000,10000,0,0)");
        db.execSQL("insert into saving_group values(2,'Group2',10000,2000,1,0)");
        db.execSQL("insert into saving_group values(3,'Group3',10000,2000,1,0)");

        db.execSQL("insert into saving_group values(4,'Group4',50000,20000,1,0)");
        db.execSQL("insert into saving_group values(5,'Group5',20000,10000,1,0)");
//        db.execSQL("insert into saving_group values(6,'Group6',1000000,20000,1,0)");

        // Create user table
        db.execSQL("create table user (user_id integer primary key autoincrement, " +
                "user_name varchar(20), user_pwd varchar(20), user_avatar varchar(20))");
        db.execSQL("insert into user values(1,'user1@demo.com','123456','user1_avatar.png')");
        db.execSQL("insert into user values(2,'user2@demo.com','123456','user2_avatar.png')");
        db.execSQL("insert into user values(3,'user3@demo.com','123456','user3_avatar.png')");
        db.execSQL("insert into user values(4,'user4@demo.com','123456','user4_avatar.png')");
        db.execSQL("insert into user values(5,'user5@demo.com','123456','user5_avatar.png')");
        db.execSQL("insert into user values(6,'user6@demo.com','123456','user6_avatar.png')");

        // goal_status: 0 represents the goal is completed while 1 represents the goal is in progress
        // goal_if_public: 0 represents that the goal is public while 1 represents the goal is private
        db.execSQL("create table goal (goal_id integer primary key autoincrement, user_id integer, " +
                "group_id integer, goal_name varchar(20), " +
                "goal_target integer, goal_current integer, " +
                "goal_status integer, goal_if_public integer)");
        db.execSQL("insert into goal values(1,1,1,'Goal1',2000,2000,0,0)");
        db.execSQL("insert into goal values(2,2,1,'Goal1_User2',1000,1000,0,0)");
        db.execSQL("insert into goal values(3,3,1,'Goal1_User3',2000,2000,0,0)");
        db.execSQL("insert into goal values(4,4,1,'Goal1_User4',2000,2000,0,0)");
        db.execSQL("insert into goal values(5,5,1,'Goal1_User5',3000,3000,0,0)");

        db.execSQL("insert into goal values(6,1,2,'Goal2',10000,1000,1,0)");

        db.execSQL("insert into goal values(7,2,3,'Goal3',10000,1000,1,0)");

        db.execSQL("insert into goal values(8,3,4,'Goal4',10000,1000,1,0)");
        db.execSQL("insert into goal values(9,4,5,'Goal5',10000,1000,1,0)");
//        db.execSQL("insert into goal values(10,5,6,'Goal6',10000,1000,1,0)");

        // Create user-group table, multiple to multiple relationship
        db.execSQL("create table user_group (user_group_id integer primary key autoincrement, " +
                "user_id integer, group_id integer)");
        db.execSQL("insert into user_group values(1,1,1)");
        db.execSQL("insert into user_group values(2,1,2)");
        db.execSQL("insert into user_group values(3,2,1)");
        db.execSQL("insert into user_group values(4,3,1)");
        db.execSQL("insert into user_group values(5,4,1)");
        db.execSQL("insert into user_group values(6,5,1)");

        db.execSQL("insert into user_group values(7,2,3)");

        db.execSQL("insert into user_group values(8,3,4)");
        db.execSQL("insert into user_group values(9,4,5)");
//        db.execSQL("insert into user_group values(10,5,6)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("call update");
    }
}
