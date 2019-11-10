package com.example.demov1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.demov1.entity.GroupEntity;

import java.util.ArrayList;

public class GroupRecycleAdapter extends RecyclerView.Adapter<GroupRecycleAdapter.myViewHodler> implements View.OnClickListener{
    private Context context;
    private ArrayList<GroupEntity> groupEntityList;

    // Create constructor function
    public GroupRecycleAdapter(Context context, ArrayList<GroupEntity> groupEntityList) {
        // Assign the value of passed data to local variable
        this.context = context; // context
        this.groupEntityList = groupEntityList; // Entity class
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
        View itemView = View.inflate(context, R.layout.join_card_view, null);
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
        GroupEntity data = groupEntityList.get(position);
//        holder.mItemGroupId.setText(String.valueOf(data.getGroupId()))
        holder.mItemGroupName.setText(data.getGroupName());// get the groupName field of entity to set
        holder.groupCurrentAmount.setText(String.valueOf(data.getCurrentAmount()));
        holder.groupTargetAmount.setText(String.valueOf(data.getTargetAmount()));
        String groupMember = "GroupMember: " + String.valueOf(data.getUsers().size()) + "/5";
        holder.groupMemberNum.setText(groupMember);
        holder.progressBar.setMax(data.getTargetAmount());
        holder.progressBar.setProgress(data.getCurrentAmount());

        holder.itemView.setTag(position);
        holder.joinGroupBtn.setTag(position);
    }

    /**
     * Get total count
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return groupEntityList.size();
    }

    //自定义viewhodler
    public class myViewHodler extends RecyclerView.ViewHolder {
        private ImageView mItemGroupImg;
        private TextView mItemGroupName;
        private TextView groupCurrentAmount;
        private TextView groupTargetAmount;
        private TextView groupMemberNum;
        private RoundCornerProgressBar progressBar;
        private Button joinGroupBtn;

        public myViewHodler(View itemView) {
            super(itemView);
            mItemGroupImg = (ImageView) itemView.findViewById(R.id.group_photo);
            mItemGroupName = (TextView) itemView.findViewById(R.id.groupName);
            groupCurrentAmount = (TextView) itemView.findViewById(R.id.current_amount);
            groupTargetAmount = (TextView) itemView.findViewById(R.id.target_amount);
            groupMemberNum = (TextView) itemView.findViewById(R.id.group_member);
            progressBar = itemView.findViewById(R.id.progress_bar);
            joinGroupBtn = itemView.findViewById(R.id.join_group_btn);

            itemView.setOnClickListener(GroupRecycleAdapter.this);
            joinGroupBtn.setOnClickListener(GroupRecycleAdapter.this);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onItemClickListener != null) {
//                        onItemClickListener.OnItemClick(v, groupEntityList.get(getLayoutPosition()));
//                        System.out.println("position:" + groupEntityList.get(getLayoutPosition()));
//                    }
//                }
//            });

        }
    }

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        JOIN
    }

    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, ViewName viewName, int position, GroupEntity groupEntity);
        void onItemLongClick(View v);
    }

    private OnItemClickListener mOnItemClickListener; //声明自定义的接口

    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener  listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        System.out.println("getTag"+position);
        GroupEntity groupEntity = groupEntityList.get(position);
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                case R.id.goal_recyclerView:
                    mOnItemClickListener.onItemClick(v, ViewName.JOIN, position, groupEntity);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position, groupEntity);
                    break;
            }
        }
    }
}
