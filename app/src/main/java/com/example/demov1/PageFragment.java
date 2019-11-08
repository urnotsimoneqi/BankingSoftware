package com.example.demov1;

import android.os.Bundle;
import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.demov1.base.BasePageFragment;

public class PageFragment extends BasePageFragment {

    public static PageFragment newInstance(String title){
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString("key_fragment_title", title);
        fragment.setArguments(args);
        return fragment;
    }

    private String title;
    private TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("key_fragment_title");
//        Trace.d(title + ":onCreate");
        System.out.println(title + ":onCreate");
    }

    @Override
    public void onResume() {
        super.onResume();
//        Trace.d(title + ":onResume");
        System.out.println(title + ":onResume");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Trace.d(title + ":onCreateView");
        System.out.println(title + ":onCreateView");
        tv = new TextView(getActivity());
        return tv;
    }

    @Override
    public void fetchData() {
        tv.setText(title);
        /** * 在这里请求网络。 */
    }

}
