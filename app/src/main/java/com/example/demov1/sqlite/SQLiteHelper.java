package com.example.demov1.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qxm on 2019/10/18.
 */

public class SQLiteHelper extends SQLiteOpenHelper{

    /*SQLiteHelper的四个参数，上下文，数据库名字，null,版本号（任意数字）*/
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /*上面那个太过复杂，所以需要重载一个简单的方法：通过构造方法，完成数据库的创建*/
    public SQLiteHelper(Context context){
        super(context,"mydb",null,1);
    }

    /*通过OnCreate方法，实现数据表的创建*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        // group status: 0 represents the goal is completed and 1 represents the goal is in progress
        db.execSQL("create table group_goal (group_id integer primary key autoincrement, group_name varchar(20), target_amount integer, current_amount integer, group_status integer)");
        db.execSQL("insert into group_goal values(1,'Goal1',10000,10000,0)");
        db.execSQL("insert into group_goal values(2, 'Goal2',10000,2000,1)");

//        db.execSQL("create table user (user_id integer primary key autoincrement, group_id integer, user_name varchar(20), user_pwd varchar(20), user_goal integer)");
//        db.execSQL("insert into user values(1,1,'user1@demo.com','123456')");
//        db.execSQL("insert into user values(2,1,'user2@demo.com','123456')");
//        db.execSQL("insert into user values(3,1,'user3@demo.com','123456')");
//        db.execSQL("insert into user values(4,1,'user4@demo.com','123456')");
//        db.execSQL("insert into user values(5,1,'user5@demo.com','123456')");
//        db.execSQL("insert into user values(6,2,'user6@demo.com','123456')");

        db.execSQL("create table user (user_id integer primary key autoincrement, user_name varchar(20), user_pwd varchar(20), user_avatar varchar(20))");
        db.execSQL("insert into user values(1,'user1@demo.com','123456','user1_avatar.png')");
        db.execSQL("insert into user values(2,'user2@demo.com','123456','user2_avatar.png')");
        db.execSQL("insert into user values(3,'user3@demo.com','123456','user3_avatar.png')");
        db.execSQL("insert into user values(4,'user4@demo.com','123456','user4_avatar.png')");
        db.execSQL("insert into user values(5,'user5@demo.com','123456','user5_avatar.png')");
        db.execSQL("insert into user values(6,'user6@demo.com','123456','user6_avatar.png')");


        db.execSQL("create table user_group (user_group_id integer primary key autoincrement, user_id integer, group_id integer)");
        db.execSQL("insert into user_group values(1,1,1)");
        db.execSQL("insert into user_group values(2,1,2)");
        db.execSQL("insert into user_group values(3,2,1)");
        db.execSQL("insert into user_group values(4,3,1)");
        db.execSQL("insert into user_group values(5,4,1)");
        db.execSQL("insert into user_group values(6,5,1)");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("call update");
    }
}
