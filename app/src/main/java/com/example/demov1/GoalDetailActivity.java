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
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.demov1.Entity.GroupEntity;
import com.alibaba.fastjson.JSON;
import com.example.demov1.Entity.UserEntity;
import com.example.demov1.dao.GroupDao;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

public class GoalDetailActivity extends AppCompatActivity {
    public RecyclerView mUserRecyclerView;
    private RoundCornerProgressBar progressBar;
    private List<UserEntity> userEntityList = new ArrayList<>();
    private UserRecycleAdapter mUserRecyclerAdapter;
    private GroupDao groupDao;
    private GroupEntity group;
    private static final String TAG = "Test";
    CommonTitleBar _returnButton;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);
        String groupJson = getIntent().getStringExtra("Group");
        group = JSON.parseObject(groupJson, GroupEntity.class);
//        Log.d(TAG, "Group Name->" + group.getGroupName());
//        Log.d(TAG, "Group Id->" + group.getGroupId());
//        int groupId = group.getGroupId();
//        int groupStatus = group.getGroupStatus();
        userEntityList = group.getUsers();
//        Log.d(TAG, "Group Size->" + userEntityList.size());
        progressBar = findViewById(R.id.detail_progress_bar);
        progressBar.setMax(group.getTargetAmount());
        progressBar.setProgress(group.getCurrentAmount());
        // Initialize recyclerView
        initRecyclerView();
        _returnButton = findViewById(R.id.title_bar_goal_detail);
        _returnButton.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    GoalDetailActivity.this.finish();
                }
            }
        });

        fab = findViewById(R.id.fab_save_money);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoalDetailActivity.this, SaveMoneyActivity.class);
                startActivity(intent);
            }

        });
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
//        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab_add_member);
        // Create Adapter
        mUserRecyclerAdapter = new UserRecycleAdapter(this, userEntityList, group.getGroupId());
        // Set layoutManager
        mUserRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // Set adapter for RecyclerView
        mUserRecyclerView.setAdapter(mUserRecyclerAdapter);
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mUserRecyclerAdapter.setOnItemClickListener(new UserRecycleAdapter.OnItemClickListener() {
            private static final String TAG = "Test";

            @Override
            public void OnItemClick(View view, UserEntity data, int groupId) {
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
