package com.example.demov1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.fastjson.JSON;
import com.example.demov1.Entity.GroupEntity;
import com.example.demov1.dao.GoalDao;
import com.example.demov1.dao.UserDao;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SaveMoneyActivity extends AppCompatActivity {

    private LottieAnimationView animationView;
    private GoalDao goalDao;
    private static final String MyPREFERENCES = "user_details";
    private int userId;
    private GroupEntity group;
    private EditText savingAmountText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_now);

        goalDao = new GoalDao(this);
        UserDao userDao = new UserDao(this);
        SharedPreferences sharedPreferences = this.getSharedPreferences(MyPREFERENCES, Activity.MODE_PRIVATE);
        String username = sharedPreferences.getString("email", "");
        userId = userDao.findUserId(username);

        String groupJson = getIntent().getStringExtra("Group");
        group = JSON.parseObject(groupJson, GroupEntity.class);

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("SGD");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });

        animationView = findViewById(R.id.animation_view);
        animationView.setAnimation("wallet_coin.json");

        Button saveMoneyBtn = findViewById(R.id.save_money_btn);
        savingAmountText = findViewById(R.id.saving_amount);
//        int savingAmount = Integer.parseInt(savingAmountText.getText().toString());
        saveMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goalDao.depositMoney(userId, group.getGroupId(),
                        Integer.parseInt(savingAmountText.getText().toString()),1000);
                animationView.playAnimation();// play animation
//                animationView.cancelAnimation();//取消动画
            }
        });
    }
}
