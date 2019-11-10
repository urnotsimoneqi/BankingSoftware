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

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("Share");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }

//    private void showShare() {
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//
//        // title标题，微信、QQ和QQ空间等平台使用
//        oks.setTitle(getString(R.string.share));
//        // titleUrl QQ和QQ空间跳转链接
//        oks.setTitleUrl("http://sharesdk.cn");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("我是分享文本");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url在微信、微博，Facebook等平台中使用
//        oks.setUrl("http://sharesdk.cn");
//        // comment是我对这条分享的评论，仅在人人网使用
//        oks.setComment("我是测试评论文本");
//        // 启动分享GUI
//        oks.show(this);
//    }
}


