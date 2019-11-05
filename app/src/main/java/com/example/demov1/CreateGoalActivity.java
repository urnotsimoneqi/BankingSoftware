package com.example.demov1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;

public class CreateGoalActivity extends AppCompatActivity {

    @BindView(R.id.goal_name)
    EditText _goalNameText;
    @BindView(R.id.goal_target)
    EditText _goalTargetText;
    @BindView(R.id.checkbox_private)
    CheckBox _goalCheckBox;
    @BindView(R.id.btn_create_goal)
    Button _createGoalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);
    }
}
