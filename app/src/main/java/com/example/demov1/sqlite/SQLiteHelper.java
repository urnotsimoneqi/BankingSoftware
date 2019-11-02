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
        db.execSQL("create table user (user_name varchar(20), user_pwd varchar(20))");
        db.execSQL("insert into user values('demo@demo.com','123456')");

//        db.execSQL("create table group_goal (group_id integer primary key autoincrement, group_name varchar(20), target_amount integer)");
//        db.execSQL("insert into group_goal values(1,'test1','10000')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("call update");
    }
}
