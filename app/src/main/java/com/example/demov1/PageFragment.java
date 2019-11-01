package com.example.demov1;

import com.ogaclejapan.smarttablayout.utils.v4.Bundler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PageFragment extends Fragment {

    private static final String KEY_PARAM = "key_param";

    public static PageFragment newInstance(String param) {
        PageFragment f = new PageFragment();
        f.setArguments(arguments(param));
        return f;
    }

    public static Bundle arguments(String param) {
        return new Bundler()
                .putString(KEY_PARAM, param)
                .get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String param = getArguments().getString(KEY_PARAM);

        TextView pageText = (TextView) view.findViewById(R.id.page_text);
        pageText.setText(param);

    }

}
