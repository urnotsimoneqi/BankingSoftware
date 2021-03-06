package com.example.demov1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.example.demov1.base.BaseActivity;
import com.example.demov1.entity.GroupEntity;
import com.example.demov1.dao.GoalDao;
import com.example.demov1.dao.GroupDao;
import com.example.demov1.dao.UserDao;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

public class CreateGoalActivity extends BaseActivity {
    public static final String MyPREFERENCES = "user_details";
    SharedPreferences preferences;
    private UserDao userDao;
    private GoalDao goalDao;
    private GroupDao groupDao;
    int goalIfPublic = 0; // 0 represents the goal is public
    int userId;

    EditText _goalNameText;
    EditText _goalTargetText;
    CheckBox _goalCheckBox;
    Button _createGoalButton;
    CommonTitleBar _cancelGaolButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);

        _goalCheckBox = findViewById(R.id.checkbox_private);
        _goalCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // update your model (or other business logic) based on isChecked
                if (isChecked) {
                    goalIfPublic = 1;
                } else {
                    goalIfPublic = 0;
                }
            }
        });
        preferences = getSharedPreferences(MyPREFERENCES, Activity.MODE_PRIVATE);
        String username = preferences.getString("email", "");
        goalDao = new GoalDao(this);
        userDao = new UserDao(this);
        groupDao = new GroupDao(this);
        userId = userDao.findUserId(username);
        _goalNameText = findViewById(R.id.goal_name);
        _goalTargetText = findViewById(R.id.goal_target);
        _createGoalButton = findViewById(R.id.btn_create_goal);
        _createGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroupEntity group = groupDao.createGroup(_goalNameText.getText().toString(),
                        Integer.parseInt(_goalTargetText.getText().toString()), goalIfPublic); // create group
                if(goalDao.createGoal(userId, group.getGroupId(), group.getGroupName(),
                        group.getTargetAmount(), group.getGroupIfPublic())) {
                    System.out.println("Create goal and Group");
                    groupDao.joinGroup(userId, group.getGroupId());
                    Intent intent = new Intent(CreateGoalActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // fail to add group, show prompt
                    Toast.makeText(CreateGoalActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        _cancelGaolButton = findViewById(R.id.title_bar_new_goal);
        _cancelGaolButton.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    CreateGoalActivity.this.finish();
                }
            }
        });
    }
}
