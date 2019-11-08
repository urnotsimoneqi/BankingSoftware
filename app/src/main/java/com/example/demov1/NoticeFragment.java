package com.example.demov1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import com.example.demov1.base.BasePageFragment;

//public class NoticeFragment extends BasePageFragment {
//
//    public static NoticeFragment newInstance(String title){
//        PageFragment fragment = new PageFragment();
//        Bundle args = new Bundle();
//        args.putString("key_fragment_title", title);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    private String title;
//    private TextView tv;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        title = getArguments().getString("key_fragment_title");
//        Trace.d(title + ":onCreate");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Trace.d(title + ":onResume");
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Trace.d(title + ":onCreateView");
//        tv = new TextView(getActivity());
//        return tv;
//    }
//
//    @Override
//    public void fetchData() {
//        tv.setText(title);
//        /** * 在这里请求网络。 */
//    }
//
//}

public class NoticeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notice_tab, container, false);
        System.out.println("This is the Notice Tab");
        return view;
    }
}

