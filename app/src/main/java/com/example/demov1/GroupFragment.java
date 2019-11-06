package com.example.demov1;

import android.content.Intent;
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
//import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.demov1.Entity.GroupEntity;
import com.example.demov1.dao.GroupDao;

import java.util.ArrayList;

public class GroupFragment extends Fragment {
    private View view; // Define view to set up the layout of fragment
    public RecyclerView mGroupRecyclerView; // Define RecyclerView
    private ArrayList<GroupEntity> groupEntityList = new ArrayList<GroupEntity>();
    private GroupRecycleAdapter mGroupRecyclerAdapter;
    private GroupDao groupDao;
//    private FloatingSearchView mSearchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.group_tab, container, false);
        groupDao = new GroupDao(getActivity());
        groupEntityList = initData();  // Load data
//        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
//            @Override
//            public void onSearchTextChanged(String oldQuery, final String newQuery) {
//
//                //get suggestions based on newQuery
//
//                //pass them on to the search view
//                mSearchView.swapSuggestions(newSuggestions);
//            }
//        });
        initRecyclerView(); // Initialize recycleview
        return view;
    }

    private ArrayList<GroupEntity> initData() {
        ArrayList<GroupEntity> groupEntityList = groupDao.listGroup();
        return groupEntityList;
    }

    private void initRecyclerView() {
        // Get RecyclerView
        mGroupRecyclerView = (RecyclerView) view.findViewById(R.id.goal_recyclerView);
        if ( mGroupRecyclerView != null) {
            System.out.println("Goal RecycleView not null!");
        }
        // Create Adapter
        mGroupRecyclerAdapter = new GroupRecycleAdapter(getActivity(), groupEntityList);
        // Set adapter for RecyclerView
        mGroupRecyclerView.setAdapter(mGroupRecyclerAdapter);
        mGroupRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mGroupRecyclerAdapter.setOnItemClickListener(new GroupRecycleAdapter.OnItemClickListener() {
            private static final String TAG = "Test";
            @Override
            public void OnItemClick(View view, GroupEntity data) {
//                Intent intent = new Intent(getActivity(), GoalDetailActivity.class);
//                String jsonString = JSON.toJSONString(data);
//                Log.e(TAG, "simpleEncode: " + jsonString);
//                intent.putExtra("Group", jsonString);
//                startActivity(intent);
            }
        });
    }
}

