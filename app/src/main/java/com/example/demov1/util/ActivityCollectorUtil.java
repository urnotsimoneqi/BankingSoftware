package com.example.demov1.util;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class ActivityCollectorUtil {
    public static ArrayList<AppCompatActivity> mActivityList = new ArrayList<AppCompatActivity>();

    /**
     * onCreate() add
     * @param activity
     */
    public static void addActivity(AppCompatActivity activity){
        //判断集合中是否已经添加，添加过的则不再添加
        if (!mActivityList.contains(activity)){
            mActivityList.add(activity);
        }
    }

    /**
     * onDestroy() delete
     * @param activity
     */
    public static void removeActivity(AppCompatActivity activity){
        mActivityList.remove(activity);
    }

    /**
     * close all Activity
     */
    public static void finishAllActivity(){
        for (AppCompatActivity activity : mActivityList){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}

