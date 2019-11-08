package com.example.demov1.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public abstract class BasePageFragment extends Fragment {

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public abstract void fetchData();

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }

}

//
//public abstract class BaseFragment extends Fragment {
//    protected BaseActivity mActivity;
//    protected Unbinder mUnbinder;
//    protected View mRootView;
//
//    protected abstract @LayoutRes
//    int initLayoutRes();
//
//    protected abstract void initData();
//
//    protected abstract void initView();
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        mActivity = (BaseActivity) context;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initData();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mRootView = inflater.inflate(initLayoutRes(), container, false);
//        mUnbinder = ButterKnife.bind(this, mRootView);
//        initView();
//        return mRootView;
//    }
//
//    @Override
//    public void onDestroy() {
//        mUnbinder.unbind();
//        super.onDestroy();
//    }
//}
