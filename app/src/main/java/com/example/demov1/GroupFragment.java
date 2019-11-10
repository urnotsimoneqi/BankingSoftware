package com.example.demov1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
//import com.arlib.floatingsearchview.FloatingSearchView;
import com.example.demov1.entity.GroupEntity;
import com.example.demov1.entity.UserEntity;
import com.example.demov1.dao.GoalDao;
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
    private GoalDao goalDao;
    private int userId;
    private String username;

    private static final String MyPREFERENCES = "user_details";
    private SharedPreferences sharedPreferences;
    private boolean isCreated = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.group_tab, container, false);
        isCreated = true; // Flag
        groupDao = new GroupDao(getActivity());
        groupEntityList = initData();  // Load data

        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Activity.MODE_PRIVATE);
        String username = sharedPreferences.getString("email", "");
        userDao = new UserDao(getActivity());
        userId = userDao.findUserId(username);
        goalDao = new GoalDao(getActivity());

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
     * item+item: controller click listener event
     */
    private GroupRecycleAdapter.OnItemClickListener GroupItemClickListener = new GroupRecycleAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, GroupRecycleAdapter.ViewName viewName, int position, GroupEntity groupEntity) {
            //viewName可区分item及item内部控件
            switch (v.getId()) {
                case R.id.join_group_btn:
                    // Need to update: when the user already has a group
                    boolean userHasGroupInProgress = false;
                    ArrayList<GroupEntity> myGroups = groupDao.listMyGroups(userId);
                    for (int i = 0; i <myGroups.size(); i++) {
                        GroupEntity group = myGroups.get(i);
                        if (group.getGroupStatus() == 1){
                            userHasGroupInProgress = true;
                            Toast.makeText(getActivity(), "You already has a group in progress", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            userHasGroupInProgress = false;
                        }
                    }

                    boolean userInGroup = false; // set flag
                    for (int i = 0; i <groupEntity.getUsers().size(); i++) {
                        UserEntity user = groupEntity.getUsers().get(i);
                        if (user.getUserId() == userId) { // if the user already joined the group
                            userInGroup = true;
                            Toast.makeText(getActivity(), "You already in the group", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            userInGroup = false;
                        }
                    }
                    if (userInGroup == false && userHasGroupInProgress == false) {
                        goalDao.createGoal(userId, groupEntity.getGroupId(), groupEntity.getGroupName(),
                                5000, groupEntity.getGroupIfPublic());
                        groupDao.joinGroup(userId, groupEntity.getGroupId());
                        Toast.makeText(getActivity(), "Join successfully", Toast.LENGTH_SHORT).show();
                        setUserVisibleHint(true);
                    }
                    break;
                default:
//                    Toast.makeText(getActivity(), "You click the item" + (position + 1), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void onItemLongClick(View v) {

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isCreated = true;
        sharedPreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Activity.MODE_PRIVATE);
        userDao = new UserDao(getActivity());
        username = sharedPreferences.getString("email", "");
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
            Log.e("GroupFragment", "onStart: ++++++++++++++++++");
            //mGoalRecyclerAdapter refresh，update group list
            if (groupEntityList != null) {
                groupEntityList.clear();
                ArrayList<GroupEntity> data = groupDao.listGroup();
                groupEntityList.addAll(data);

                userDao = new UserDao(getActivity());
                userId = userDao.findUserId(username);
                mGroupRecyclerAdapter.notifyDataSetChanged();
            }
            mGroupRecyclerAdapter.notifyDataSetChanged();
        } else {
            // similar as onPause of Fragment
            // System.out.println("ChatFragment ---setUserVisibleHint---isVisibleToUser - FALSE");
        }
    }

}

