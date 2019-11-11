package com.example.demov1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.airbnb.lottie.LottieAnimationView;
import com.alibaba.fastjson.JSON;
import com.example.demov1.base.BaseActivity;
import com.example.demov1.entity.GroupEntity;
import com.example.demov1.dao.GoalDao;
import com.example.demov1.dao.GroupDao;
import com.example.demov1.dao.UserDao;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

public class SaveMoneyActivity extends AppCompatActivity {

    private LottieAnimationView animationView;
    private GoalDao goalDao;
    private GroupDao groupDao;
    private static final String MyPREFERENCES = "user_details";
    private int userId;
    private GroupEntity group;
    private EditText savingAmountText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_now);

        goalDao = new GoalDao(this);
        groupDao = new GroupDao(this);
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
//        animationView.loop(true);
        animationView.addAnimatorUpdateListener(valueAnimator -> {
            // judge if the animation is finished
//            Log.d("Lottie", "" + valueAnimator.getAnimatedFraction());
            if (valueAnimator.getAnimatedFraction() == 1) {
                animationView.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(SaveMoneyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button saveMoneyBtn = findViewById(R.id.save_money_btn);
        savingAmountText = findViewById(R.id.saving_amount);
//        int savingAmount = Integer.parseInt(savingAmountText.getText().toString());
        saveMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("GroupId when click save money" + group.getGroupId());
                // need to get current amount
                goalDao.depositMoney(userId, group.getGroupId(),
                        Integer.parseInt(savingAmountText.getText().toString()));
                groupDao.updateGroupGoal(group.getGroupId());
                animationView.playAnimation();// play animation

            }
        });

        CommonTitleBar cancelButton = findViewById(R.id.title_bar_save_money);
        cancelButton.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    SaveMoneyActivity.this.finish();
                }
            }
        });
    }
}
