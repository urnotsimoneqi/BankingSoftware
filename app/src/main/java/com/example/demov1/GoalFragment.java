package com.example.demov1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.fastjson.JSON;
import com.example.demov1.entity.GroupEntity;
import com.example.demov1.dao.GroupDao;
import com.example.demov1.dao.UserDao;
import java.util.ArrayList;

public class GoalFragment extends Fragment {

    private View view; // Define view to set up the layout of fragment
    public RecyclerView mGoalRecyclerView; // Define RecyclerView
    private  FloatingActionButton fab;
    private ArrayList<GroupEntity> groupEntityList = new ArrayList<GroupEntity>();
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
        view = inflater.inflate(R.layout.goal_tab, container, false); // Get the layout of fragment
        isCreated = true; // Flag
        groupDao = new GroupDao(getActivity());
//        System.out.println("Current login user Id" + userId);
        groupEntityList = initData(userId); // Load data
        initRecyclerView();
        return view;
    }

    /**
     * TODO Test Data
     */
    private ArrayList<GroupEntity> initData(int userId) {
        ArrayList<GroupEntity> groupEntityList = groupDao.listMyGroups(userId);
//        System.out.println("Initializing my groups ......");
        System.out.println(groupEntityList.size());
        fab = (FloatingActionButton) view.findViewById(R.id.fab_new_group);
        // hide the creating goal button if the user already has a goal in progress
        for(int i=0;i<groupEntityList.size();i++){
            GroupEntity group = groupEntityList.get(i);
//            System.out.println("Group Id"+group.getGroupId());
//            System.out.println("Group Name"+group.getGroupName());
//            System.out.println("Group Status"+group.getGroupStatus());
            if (group.getGroupStatus() == 1) {
                fab.setVisibility(View.INVISIBLE);
            }
        }
        return groupEntityList;
    }

    /**
     * TODO Configure recycleview
     */

    private void initRecyclerView() {
        System.out.println("Initialize RecyclerView");
        // Get RecyclerView
        mGoalRecyclerView = (RecyclerView) view.findViewById(R.id.goal_recyclerView);
        if (mGoalRecyclerView != null) {
            System.out.println("Goal RecycleView not null!");
        }

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
            // Execute when this fragment shows
            // Network request or refresh data
            Log.e("GoalFragment", "onStart: ++++++++++++++++++");
            //mGoalRecyclerAdapter refresh，update group list
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
            // similar as onPause of Fragment
            // System.out.println("ChatFragment ---setUserVisibleHint---isVisibleToUser - FALSE");
        }
    }

}
