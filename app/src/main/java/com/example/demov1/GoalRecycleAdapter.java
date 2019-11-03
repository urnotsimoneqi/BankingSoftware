package com.example.demov1;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.demov1.Entity.GroupEntity;

import java.util.ArrayList;

public class GoalRecycleAdapter extends RecyclerView.Adapter<GoalRecycleAdapter.myViewHodler> {
    private Context context;
    private ArrayList<GroupEntity> goalEntityList;

    //创建构造函数
    public GoalRecycleAdapter(Context context, ArrayList<GroupEntity> goalEntityList) {
        //将传递过来的数据，赋值给本地变量
        this.context = context;//上下文
        this.goalEntityList = goalEntityList;//实体类数据ArrayList
    }

    /**
     * Create viewholder, equal to creating view and viewholder in getview of listview
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public myViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建自定义布局
        View itemView = View.inflate(context, R.layout.card_view, null);
        return new myViewHodler(itemView);
    }

    /**
     * Bind data wih view
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(myViewHodler holder, int position) {
        //根据点击位置绑定数据
        GroupEntity data = goalEntityList.get(position);
//        holder.mItemGroupId.setText(String.valueOf(data.getGroupId()))
        holder.mItemGroupName.setText(data.getGroupName());// get the groupName field of entity to set
        holder.groupCurrentAmount.setText(String.valueOf(data.getCurrentAmount()));
        holder.groupTargetAmount.setText(String.valueOf(data.getTargetAmount()));
        String groupMember = "GroupMember: " + String.valueOf(data.getUsers().size()) +"/5";
        holder.groupMemberNum.setText(groupMember);
        holder.progressBar.setMax(data.getTargetAmount());
        holder.progressBar.setProgress(data.getCurrentAmount());
    }

    /**
     * Get total count
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return goalEntityList.size();
    }

    //自定义viewhodler
    class myViewHodler extends RecyclerView.ViewHolder {
        private ImageView mItemGroupImg;
        private TextView mItemGroupName;
        private TextView groupCurrentAmount;
        private TextView groupTargetAmount;
        private TextView groupMemberNum;
        private RoundCornerProgressBar progressBar;

        public myViewHodler(View itemView) {
            super(itemView);
            mItemGroupImg = (ImageView) itemView.findViewById(R.id.group_photo);
            mItemGroupName = (TextView) itemView.findViewById(R.id.groupName);
            groupCurrentAmount = (TextView) itemView.findViewById(R.id.current_amount);
            groupTargetAmount = (TextView) itemView.findViewById(R.id.target_amount);
            groupMemberNum = (TextView) itemView.findViewById(R.id.group_member);
            progressBar = itemView.findViewById(R.id.progress_bar);
            //点击事件放在adapter中使用，也可以写个接口在activity中调用
            //方法一：在adapter中设置点击事件
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //可以选择直接在本位置直接写业务处理
//                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
//                    //此处回传点击监听事件
//                    if(onItemClickListener!=null){
//                        onItemClickListener.OnItemClick(v, goalEntityList.get(getLayoutPosition()));
//                    }
//                }
//            });

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
        public void OnItemClick(View view, GroupEntity data);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
