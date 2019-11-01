package com.example.demov1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.demov1.sqlite.SQLiteHelper;

/**
 * Created by qxm on 2019/10/18.
 */

public class LoginDao {
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase db ;
    public LoginDao(Context context){
        sqLiteHelper = new SQLiteHelper(context);
    }

    /*Login Function*/
    public boolean login(String username , String pwd){

//        当数据库中的数据进行查询操作的时候，需要调用getReadableDatabase()
        db = sqLiteHelper.getReadableDatabase();
        Cursor cursor =  db.query("user", new String[]{"user_name"},"user_name = ? and user_pwd = ?",new String[]{username,pwd},
                null,null,null);
        if (cursor.moveToNext()){
            cursor.close();
            db.close();
            return true;
        }else {
            return false;
        }
    }
}