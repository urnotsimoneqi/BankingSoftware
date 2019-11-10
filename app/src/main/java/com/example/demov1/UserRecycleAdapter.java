package com.example.demov1;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.demov1.entity.GoalEntity;
import com.example.demov1.entity.UserEntity;
import com.example.demov1.dao.GoalDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserRecycleAdapter extends RecyclerView.Adapter<UserRecycleAdapter.myViewHodler> {
    private Context context;
    private List<UserEntity> userEntityList;
    private GoalDao goalDao;
    private int groupId;
//    private LayoutInflater mInflater;

    // Create constructor function, data is passed into the constructor
    public UserRecycleAdapter(Context context, List<UserEntity> userEntityList, int groupId) {
        // Assign the value of passed data to local variable
        this.context = context; // context
//        this.mInflater = LayoutInflater.from(context);
        this.userEntityList = userEntityList; // Entity class
        this.groupId = groupId;
    }

    /**
     * Create viewholder, equal to creating view and viewholder in getview of listview
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public UserRecycleAdapter.myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create customized layout
//        View memberView = mInflater.inflate(R.layout.group_member, parent, false);
        View memberView = View.inflate(context, R.layout.group_member, null);
        return new UserRecycleAdapter.myViewHodler(memberView);
    }

    /**
     * Bind data wih view
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(UserRecycleAdapter.myViewHodler holder, int position) {
        // Bind data based on click location
        UserEntity data = userEntityList.get(position);

        AssetManager assetManager = context.getAssets();
        String path = data.getUserAvatar(); // Pixel: 144*144
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        holder.userAvatar.setImageBitmap(bitmap);
        holder.userName.setText(data.getUserName());

        goalDao = new GoalDao(context);
        List<GoalEntity> individualGoalList = goalDao.findGoalByUserId(data.getUserId());
        for (int i = 0; i < individualGoalList.size(); i++) {
            GoalEntity individualGoal = individualGoalList.get(i);
            if(individualGoal.getGroupId() == groupId){
                holder.progressBar.setMax(individualGoal.getGoalTarget());
                holder.progressBar.setProgress(individualGoal.getGoalCurrent());
            }
        }
//        holder.goalCompleted.setText("50%");

//        holder.progressBar.setMax(2000);
//        holder.progressBar.setProgress(1000);
    }

    /**
     * Get total count
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return userEntityList.size();
    }

    // Customize viewHolder
    class myViewHodler extends RecyclerView.ViewHolder {
        private ImageView userAvatar;
        private TextView userName;
        private TextView goalCompleted;
        private RoundCornerProgressBar progressBar;

        public myViewHodler(View memberView) {
            super(memberView);
            userAvatar = (ImageView) memberView.findViewById(R.id.user_avatar);
            userName = (TextView) memberView.findViewById(R.id.user_name);
            goalCompleted = (TextView) memberView.findViewById(R.id.goal_completed);
            progressBar = memberView.findViewById(R.id.progress_bar_member);
//            点击事件放在adapter中使用，也可以写个接口在activity中调用
//            方法一：在adapter中设置点击事件
            memberView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
//                    Toast.makeText(context,"Click xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v, userEntityList.get(getLayoutPosition()), groupId);
//                        Intent intent = new Intent(context, GoalDetailActivity.class);
                        //intent.putExtra("group",userEntityList.get(getLayoutPosition()));
//                        context.startActivity(intent);
                        System.out.println("position:" + userEntityList.get(getLayoutPosition()));
                    }

                }
            });

        }
    }

    /**
     * 设置item的监听事件的接口
     */
    public interface OnItemClickListener {
        /**
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据
         */
        public void OnItemClick(View view, UserEntity data, int groupStatus);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(UserRecycleAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
