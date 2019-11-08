package com.example.demov1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.demov1.Entity.GroupEntity;
import com.example.demov1.dao.GroupDao;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    private GroupDao groupDao;
    private ArrayList<GroupEntity> groupEntityList = new ArrayList<GroupEntity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);

        initView();
        groupDao = new GroupDao(this);
        groupEntityList = initData();
    }

    private void initView() {
        ButterKnife.bind(this);

        mTabLayout.addTab(mTabLayout.newTab().setText("GOAL"));
        mTabLayout.addTab(mTabLayout.newTab().setText("GROUP"));
//        mTabLayout.addTab(mTabLayout.newTab().setText("Test Tab"));

        //自定义的Adapter继承自FragmentPagerAdapter
        PagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
//        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragmentList));

        //ViewPager设置Adapter
        mViewPager.setAdapter(mAdapter);

        //为ViewPager添加页面改变监听
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        //为TabLayout添加Tab选择监听
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private ArrayList<GroupEntity> initData() {
        ArrayList<GroupEntity> groupEntityList = groupDao.listGroup();
        return groupEntityList;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        //fragment的数量
        int nNumOfTabs;

        public MyPagerAdapter(FragmentManager fm, int nNumOfTabs) {
            super(fm);
            this.nNumOfTabs = nNumOfTabs;
        }

        /**
         * 重写getItem方法
         *
         * @param position 指定的位置
         * @return 特定的Fragment
         */
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    GoalFragment tab1 = new GoalFragment();
                    return tab1;
                case 1:
                    GroupFragment tab2 = new GroupFragment();
//                    PageFragment tab2 = PageFragment.newInstance("PageFragment");
                    return tab2;
            }
            return null;
        }

        /**
         * 重写getCount方法
         *
         * @return fragment的数量
         */
        @Override
        public int getCount() {
            return nNumOfTabs;
        }
    }
}




