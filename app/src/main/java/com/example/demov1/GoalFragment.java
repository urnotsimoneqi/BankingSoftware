package com.example.demov1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.demov1.Entity.GroupEntity;
import com.example.demov1.Entity.UserEntity;
import com.example.demov1.dao.GroupDao;

import java.util.ArrayList;
import java.util.List;

public class GoalFragment extends Fragment {
    private View view;//定义view用来设置fragment的layout
    public RecyclerView mGoalRecyclerView;//定义RecyclerView
    //定义以groupentity实体类为对象的数据集合
    private ArrayList<GroupEntity> groupEntityList = new ArrayList<GroupEntity>();
    //自定义recyclerveiw的适配器
    private GoalRecycleAdapter mGoalRecyclerAdapter;
    private GroupDao groupDao;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取fragment的layout
        view = inflater.inflate(R.layout.goal_tab, container, false);
        //对recycleview进行配置
        initRecyclerView();
        groupDao = new GroupDao(getActivity());
        //模拟数据
        groupEntityList = initData();
        System.out.println("groupsize"+groupEntityList.size());
        return view;
    }

    /**
     * TODO Test Data
     */
    private ArrayList<GroupEntity> initData() {
//        ArrayList<GroupEntity> groupEntityList = new ArrayList<>();
//        List<UserEntity> userEntityList1 = new ArrayList<>();
//        List<UserEntity> userEntityList2 = new ArrayList<>();
//        userEntityList1.add(new UserEntity(1,1,"user 1","123456"));
//        userEntityList1.add(new UserEntity(2,1,"user 2","123456"));
//        userEntityList1.add(new UserEntity(3,1,"user 3","123456"));
//        userEntityList1.add(new UserEntity(4,1,"user 4","123456"));
//        userEntityList1.add(new UserEntity(5,1,"user 5","123456"));
//        userEntityList2.add(new UserEntity(6,2,"user 6","123456"));
//        groupEntityList.add(new GroupEntity(1, "Goal 1", 3000, 1000, userEntityList1));
//        groupEntityList.add(new GroupEntity(2, "Goal 2", 3000, 1000, userEntityList2));

        ArrayList<GroupEntity> groupEntityList = groupDao.listGroup();


        return groupEntityList;


    }

    /**
     * TODO Configure recycleview
     */

    private void initRecyclerView() {
        //获取RecyclerView
        mGoalRecyclerView = (RecyclerView) view.findViewById(R.id.goal_recyclerView);
        //创建adapter
        mGoalRecyclerAdapter = new GoalRecycleAdapter(getActivity(), groupEntityList);
        //给RecyclerView设置adapter
        mGoalRecyclerView.setAdapter(mGoalRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mGoalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
//        mGoalRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mGoalRecyclerAdapter.setOnItemClickListener(new GoalRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, GroupEntity data) {
                //此处进行监听事件的业务处理
                Toast.makeText(getActivity(), "我是item", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

//public class GoalFragment extends Fragment {

//    private CardView cardView;
//    private RecyclerView recyclerView;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.goal_tab, container, false);
//
////        List<GroupEntity> myGoalList = initializeData();
//
//        List<GroupEntity> groups = initializeData();
//
//        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        GoalAdapter adapter = new GoalAdapter(groups);
//        recyclerView.setAdapter(adapter);
//        //新建适配器
////        adapter = new MyNewsDataAdapter(MainActivity.this);
////        //ListView
////        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
////        recyclerView.setAdapter(new GoalAdapter(this, myGoalList ));
////        //添加数据到适配器中，(这个方法在适配器中)
////        adapter.addDataToAdapter(dataBeens, true);
//
//
//
//        return view;
//    }
//
//    private List<GroupEntity> initializeData() {
//        List<GroupEntity> myGoalList = new ArrayList<>();
//        myGoalList.add(new GroupEntity(1,"Goal 1", 3000, 1000));
//        myGoalList.add(new GroupEntity(2,"Goal 2", 3000, 1000));
//
//        return myGoalList;
//    }
//
//
//
//
//
//
//}