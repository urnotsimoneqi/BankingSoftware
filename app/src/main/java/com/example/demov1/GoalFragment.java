package com.example.demov1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.example.demov1.Entity.GroupEntity;
import com.example.demov1.Entity.UserEntity;
import com.example.demov1.base.BasePageFragment;
import com.example.demov1.dao.GroupDao;
import com.example.demov1.dao.UserDao;


import java.util.ArrayList;
import java.util.List;

public class GoalFragment extends Fragment {

//    public static GoalFragment newInstance(String title){
//        GoalFragment fragment = new GoalFragment();
//        Bundle args = new Bundle();
//        args.putString("key_fragment_title", title);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    private String title;
//    private TextView tv;
//    private View view; // Define view to set up the layout of fragment
//    public RecyclerView mGoalRecyclerView; // Define RecyclerView
//    private ArrayList<GroupEntity> groupEntityList = new ArrayList<GroupEntity>();
//    private GoalRecycleAdapter mGoalRecyclerAdapter;
//    private GroupDao groupDao;
//    private UserDao userDao;
//    public static final String MyPREFERENCES = "user_details";
//    SharedPreferences sharedPreferences;
//    private int userId;
//    private String username;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        title = getArguments().getString("key_fragment_title");
////        Trace.d(title + ":onCreate");
//        System.out.println(title + ":onCreate");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
////        Trace.d(title + ":onResume");
//        System.out.println(title + "GoalFragment:onResume");
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.goal_tab, container, false);
////        Trace.d(title + ":onCreateView");
//        System.out.println(title + "onCreateView");
//        groupDao = new GroupDao(getActivity());
//        System.out.println("Current login user Id" + userId);
//////        groupEntityList = initData(userId); // Load data
//        initRecyclerView();
//        return view;
//    }
//
//    @Override
//    public void fetchData() {
////        tv.setText(title);
//        System.out.println("fetch data");
//        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Activity.MODE_PRIVATE);
//        username = sharedPreferences.getString("email", "");
//        System.out.println("Username:"+username);
////        userId = userDao.findUserId(username);
//
//        groupEntityList = initData(userId); // Load data
//    }
//
//    private ArrayList<GroupEntity> initData(int userId) {
//        ArrayList<GroupEntity> groupEntityList = groupDao.listMyGroups(userId);
//        System.out.println("Intializing my groups ......");
//        System.out.println(groupEntityList.size());
//        return groupEntityList;
//    }
//
//    private void initRecyclerView() {
//        System.out.println("初始化RecyclerView");
//        // Get RecyclerView
//        mGoalRecyclerView = (RecyclerView) view.findViewById(R.id.goal_recyclerView);
//        if (mGoalRecyclerView != null) {
//            System.out.println("Goal RecycleView not null!");
//        }
//        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_new_group);
////        fab.(mGoalRecyclerView); // Attach the FAB to RecyclerView
//        // Create Adapter
//        mGoalRecyclerAdapter = new GoalRecycleAdapter(getActivity(), groupEntityList);
//        // Set adapter for RecyclerView
//        mGoalRecyclerView.setAdapter(mGoalRecyclerAdapter);
//        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
//        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
//        mGoalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        //设置item的分割线
////        mGoalRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
//        mGoalRecyclerAdapter.setOnItemClickListener(new GoalRecycleAdapter.OnItemClickListener() {
//            private static final String TAG = "Test";
//
//            @Override
//            public void OnItemClick(View view, GroupEntity data) {
//                //此处进行监听事件的业务处理
////                Toast.makeText(getActivity(), "It's an item", Toast.LENGTH_SHORT).show();
////                Toast.makeText(getActivity(), data.getGroupName(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getActivity(), GoalDetailActivity.class);
//                String jsonString = JSON.toJSONString(data);
//                Log.e(TAG, "simpleEncode: " + jsonString);
//                intent.putExtra("Group", jsonString);
//                startActivity(intent);
//            }
//        });
//
//        // Set on listener for floating action bar
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), CreateGoalActivity.class);
//                startActivity(intent);
//            }
//
//        });
//    }




    private View view; // Define view to set up the layout of fragment
    public RecyclerView mGoalRecyclerView; // Define RecyclerView
    //定义以groupentity实体类为对象的数据集合
    private ArrayList<GroupEntity> groupEntityList = new ArrayList<GroupEntity>();
    //自定义recyclerveiw的适配器
    private GoalRecycleAdapter mGoalRecyclerAdapter;
    private GroupDao groupDao;
    private UserDao userDao;
    public static final String MyPREFERENCES = "user_details";
    SharedPreferences sharedPreferences;
    private int userId;
    private String username;

    private boolean isCreated = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Get the layout of fragment
        view = inflater.inflate(R.layout.goal_tab, container, false);
        // 标记
        isCreated = true;
        groupDao = new GroupDao(getActivity());
        System.out.println("Current login user Id" + userId);
        groupEntityList = initData(userId); // Load data
        initRecyclerView();
        return view;
    }

    /**
     * TODO Test Data
     */
    private ArrayList<GroupEntity> initData(int userId) {
        ArrayList<GroupEntity> groupEntityList = groupDao.listMyGroups(userId);
        System.out.println("Intializing my groups ......");
        System.out.println(groupEntityList.size());
        return groupEntityList;
    }

    /**
     * TODO Configure recycleview
     */

    private void initRecyclerView() {
        System.out.println("初始化RecyclerView");
        // Get RecyclerView
        mGoalRecyclerView = (RecyclerView) view.findViewById(R.id.goal_recyclerView);
        if (mGoalRecyclerView != null) {
            System.out.println("Goal RecycleView not null!");
        }
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_new_group);
//        fab.(mGoalRecyclerView); // Attach the FAB to RecyclerView
        // Create Adapter
        mGoalRecyclerAdapter = new GoalRecycleAdapter(getActivity(), groupEntityList);
        // Set adapter for RecyclerView
        mGoalRecyclerView.setAdapter(mGoalRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mGoalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
//        mGoalRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mGoalRecyclerAdapter.setOnItemClickListener(new GoalRecycleAdapter.OnItemClickListener() {
            private static final String TAG = "Test";

            @Override
            public void OnItemClick(View view, GroupEntity data) {
                //此处进行监听事件的业务处理
//                Toast.makeText(getActivity(), "It's an item", Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), data.getGroupName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), GoalDetailActivity.class);
                String jsonString = JSON.toJSONString(data);
                Log.e(TAG, "simpleEncode: " + jsonString);
                intent.putExtra("Group", jsonString);
                startActivity(intent);
            }
        });

        // Set on listener for floating action bar
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateGoalActivity.class);
                startActivity(intent);
            }

        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Activity.MODE_PRIVATE);
        userDao = new UserDao(getActivity());
        username = sharedPreferences.getString("email", "");
        System.out.println("OnCreateGF"+username);
        userId = userDao.findUserId(username);
    }

    // Refresh fragment when switching
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isCreated) {
            return;
        }
        if (isVisibleToUser) {
            //当此fragment正当前显示是，执行该操作，
            //网络请求或者刷新数据
            Log.e("TAG", "onStart: ++++++++++++++++++");
            //ListView适配器刷新，更新答题状态显示
            if (groupEntityList != null) {
                groupEntityList.clear();
                System.out.println("Refresh here");
                ArrayList<GroupEntity> data = groupDao.listMyGroups(userId);
                groupEntityList.addAll(data);

                userDao = new UserDao(getActivity());
                userId = userDao.findUserId(username);
                mGoalRecyclerAdapter.notifyDataSetChanged();
            }
            mGoalRecyclerAdapter.notifyDataSetChanged();
        } else {
            // 相当于Fragment的onPause
            // System.out.println("ChatFragment ---setUserVisibleHint---isVisibleToUser - FALSE");
        }
    }

}
