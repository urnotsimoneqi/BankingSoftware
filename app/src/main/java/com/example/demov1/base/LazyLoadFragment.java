//package com.example.demov1.base;
//
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import com.example.demov1.base.BaseFragment;
//
//public abstract class LazyLoadFragment extends BaseFragment {
//    private boolean isViewCreated; // 界面是否已创建完成
//    private boolean isVisibleToUser; // 是否对用户可见
//    private boolean isDataLoaded; // 数据是否已请求
//
//    // 实现具体的数据请求逻辑
//    protected abstract void loadData();
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        this.isVisibleToUser = isVisibleToUser;
//        tryLoadData();
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        isViewCreated = true;
//        tryLoadData();
//    }
//
//    public void tryLoadData() {
//        if (isViewCreated && isVisibleToUser && !isDataLoaded) {
//            loadData();
//            isDataLoaded = true;
//        }
//    }
//}
