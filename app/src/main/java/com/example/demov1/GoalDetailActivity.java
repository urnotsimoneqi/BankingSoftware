package com.example.demov1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import cn.refactor.lib.colordialog.PromptDialog;
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.example.demov1.base.BaseActivity;
import com.example.demov1.entity.GroupEntity;
import com.alibaba.fastjson.JSON;
import com.example.demov1.entity.UserEntity;
import com.example.demov1.dao.GroupDao;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.mob.MobSDK;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GoalDetailActivity extends AppCompatActivity {
    public RecyclerView mUserRecyclerView;
    //    private RoundCornerProgressBar progressBar;
    private DonutProgress progressBar;
    private List<UserEntity> userEntityList = new ArrayList<>();
    private UserRecycleAdapter mUserRecyclerAdapter;
    private GroupDao groupDao;
    private GroupEntity group;
    private static final String TAG = "Test";
    CommonTitleBar titleBar;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_detail);
        MobSDK.init(this);
        String groupJson = getIntent().getStringExtra("Group");
        group = JSON.parseObject(groupJson, GroupEntity.class);
        userEntityList = group.getUsers();
        progressBar = findViewById(R.id.detail_progress_bar);
        float progressFloat = Math.round((float) (group.getCurrentAmount()) / (float) (group.getTargetAmount()) * (float) 100);
        int progressInt = (int) progressFloat;
        System.out.println("GoalDetailOnCreateFloat" + progressFloat);
        String progressString = String.valueOf(progressInt);
        System.out.println("GoalDetailOnCreateString" + progressString);
        progressBar.setDonut_progress(progressString);

        // if the group hit the target, prompt the success dialog
        if (group.getTargetAmount().equals(group.getCurrentAmount())) {
            new PromptDialog(this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                    .setAnimationEnable(true)
                    .setTitleText(getString(R.string.operation))
                    .setContentText(getString(R.string.content_text))
                    .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            Random rand = new Random();
            String context = String.valueOf(rand.nextInt(30)+ 1);
            new PromptDialog(this)
                    .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                    .setAnimationEnable(true)
                    .setTitleText(getString(R.string.warning))
                    .setContentText(context+getString(R.string.warning_text))
                    .setPositiveListener(getString(R.string.ok), new PromptDialog.OnPositiveListener() {
                        @Override
                        public void onClick(PromptDialog dialog) {
                            dialog.dismiss();
                        }
                    }).show();
        }

        // Initialize recyclerView
        initRecyclerView();
        titleBar = findViewById(R.id.title_bar_goal_detail);
        titleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    GoalDetailActivity.this.finish();
                }
                if (action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                    showShare();
                }
            }
        });

        fab = findViewById(R.id.fab_save_money);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GoalDetailActivity.this, SaveMoneyActivity.class);
                String jsonString = JSON.toJSONString(group);
                Log.e(TAG, "simpleEncode: " + jsonString);
                intent.putExtra("Group", jsonString);
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
        mUserRecyclerView = (RecyclerView) findViewById(R.id.member_recyclerView);
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
        // There is no listening event in the RecyclerView.
        // You need to write an interface to listen for events in the adapter.
        // Parameters are based on customization
        mUserRecyclerAdapter.setOnItemClickListener(new UserRecycleAdapter.OnItemClickListener() {
            private static final String TAG = "Test";

            @Override
            public void OnItemClick(View view, UserEntity data, int groupId) {
                // Business processing of listening events here
//                Toast.makeText(this, data.getUserName(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, UserDetailActivity.class);
                String jsonString = JSON.toJSONString(data);
                Log.e(TAG, "simpleEncode: " + jsonString);
//                intent.putExtra("Group", jsonString);
//                startActivity(intent);
            }
        });
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        // Turn off sso authorization
        oks.disableSSOWhenAuthorize();
        // title
        oks.setTitle("Share");
        // titleUrl
        oks.setTitleUrl("http://sharesdk.cn");
        // All fields need this shared text
        oks.setText("Hi!Join us and start your saving journey");
        // imagePath is the local path of the image. This parameter is supported by platforms other than Linked-In.
        oks.setImagePath("/sdcard/test.jpg");// Make sure this image exists under the SDcard
        // Url is used on WeChat, Weibo, Facebook and other platforms
        oks.setUrl("http://sharesdk.cn");
        // comment used in Renren.com
        oks.setComment("text comment text");
        // Start sharing GUI
        oks.show(this);
    }
}


