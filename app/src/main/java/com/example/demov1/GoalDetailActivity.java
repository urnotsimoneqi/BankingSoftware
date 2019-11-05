package com.example.demov1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.example.demov1.Entity.GroupEntity;
import com.alibaba.fastjson.JSON;
import com.example.demov1.Entity.UserEntity;
import com.example.demov1.dao.GroupDao;

import java.util.ArrayList;
import java.util.List;

public class GoalDetailActivity extends AppCompatActivity {
    public RecyclerView mUserRecyclerView;
    private List<UserEntity> userEntityList = new ArrayList<>();
    private UserRecycleAdapter mUserRecyclerAdapter;
    private GroupDao groupDao;
    private static final String TAG = "Test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String groupJson = getIntent().getStringExtra("Group");
        GroupEntity group = JSON.parseObject(groupJson, GroupEntity.class);
        Log.d(TAG, "Group Name->" + group.getGroupName());
        Log.d(TAG, "Group Id->" + group.getGroupId());
        int groupId = group.getGroupId();
        Log.d(TAG, "Group Member 1->" + group.getUsers().get(0).getUserName());
        userEntityList = group.getUsers();
        Log.d(TAG, "Group Size->" + userEntityList.size());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);
        // Initialize recyclerView
        initRecyclerView();
    }

    /**
     * TODO Test Data
     */
    private List<UserEntity> initData(int groupId) {
        List<UserEntity> userEntityList = groupDao.listUserInGroup(groupId);
        return userEntityList;
    }

    /**
     * TODO Configure recycleview
     */

    private void initRecyclerView() {
        // Get RecyclerView
        mUserRecyclerView = (RecyclerView)findViewById(R.id.member_recyclerView);
//        if (mUserRecyclerView == null) {
//            System.out.println("User RecycleView is null!");
//        }
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_add_member);
        // Create Adapter
        mUserRecyclerAdapter = new UserRecycleAdapter(this, userEntityList);
        // Set layoutManager
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // Set adapter for RecyclerView
        mUserRecyclerView.setAdapter(mUserRecyclerAdapter);
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mUserRecyclerAdapter.setOnItemClickListener(new UserRecycleAdapter.OnItemClickListener() {
            private static final String TAG = "Test";

            @Override
            public void OnItemClick(View view, UserEntity data) {
                //此处进行监听事件的业务处理
//                Toast.makeText(this, data.getUserName(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, UserDetailActivity.class);
                String jsonString = JSON.toJSONString(data);
                Log.e(TAG, "simpleEncode: " + jsonString);
//                intent.putExtra("Group", jsonString);
//                startActivity(intent);
            }
        });
    }

}
