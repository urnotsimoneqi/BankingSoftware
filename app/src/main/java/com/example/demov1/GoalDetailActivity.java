package com.example.demov1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.demov1.Entity.GroupEntity;
import com.alibaba.fastjson.JSON;

public class GoalDetailActivity extends AppCompatActivity {
    private static final String TAG = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String groupJson = getIntent().getStringExtra("Group");
        GroupEntity group = JSON.parseObject(groupJson, GroupEntity.class);
        Log.d(TAG, "Group Name->" + group.getGroupName());
        Log.d(TAG, "Group Size->" + group.getUsers().size());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);


//        String groupJson= getIntent().getStringExtra("Group");
//        GroupEntity group = new Gson().fromJson(groupJson, GroupEntity.class);
//        Log.d(TAG,"Group Name->"+group.getGroupName());
//        Log.d(TAG,"Group Size->"+group.getUsers().size());
    }

}
