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
import android.widget.Button;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
//import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.demov1.Entity.GroupEntity;
import com.example.demov1.dao.GroupDao;
import com.example.demov1.dao.UserDao;

import java.util.ArrayList;

public class GroupFragment extends Fragment {

    private View view; // Define view to set up the layout of fragment
    private RecyclerView mGroupRecyclerView; // Define RecyclerView
    private ArrayList<GroupEntity> groupEntityList = new ArrayList<GroupEntity>();
    private GroupRecycleAdapter mGroupRecyclerAdapter;

    private GroupDao groupDao;
    private UserDao userDao;
    private int userId = 1;

    private static final String MyPREFERENCES = "user_details";
//    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.group_tab, container, false);
        groupDao = new GroupDao(getActivity());
        groupEntityList = initData();  // Load data

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Activity.MODE_PRIVATE);
        String username = sharedPreferences.getString("email", "");
        userDao = new UserDao(getActivity());
//        userId = userDao.findUserId(username);

        initRecyclerView(); // Initialize recycleview

        mGroupRecyclerAdapter = new GroupRecycleAdapter(getActivity(), groupEntityList);
        mGroupRecyclerView.setAdapter(mGroupRecyclerAdapter);
        mGroupRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mGroupRecyclerAdapter.setOnItemClickListener(GroupItemClickListener);

        return view;
    }

    private ArrayList<GroupEntity> initData() {
        ArrayList<GroupEntity> groupEntityList = groupDao.listGroup();
        System.out.println("Group Tab" + groupEntityList.size());
        return groupEntityList;
    }

    private void initRecyclerView() {
        mGroupRecyclerView = (RecyclerView) view.findViewById(R.id.goal_recyclerView);
    }

    /**
     * item＋item里的控件点击监听事件
     */
    private GroupRecycleAdapter.OnItemClickListener GroupItemClickListener = new GroupRecycleAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, GroupRecycleAdapter.ViewName viewName, int position, GroupEntity groupEntity) {
            //viewName可区分item及item内部控件
            switch (v.getId()) {
                case R.id.join_group_btn:
                    Toast.makeText(getActivity(), "You click the join button" + (position + 1), Toast.LENGTH_SHORT).show();
                    groupDao.joinGroup(userId, groupEntity.getGroupId());
//                    mGroupRecyclerAdapter.notifyDataSetChanged();
//                    mGroupRecyclerAdapter.notifyItemInserted(position + 1);
                    break;
                default:
                    Toast.makeText(getActivity(), "You click the item" + (position + 1), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onItemLongClick(View v) {

        }
    };

}

