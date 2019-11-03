//package com.example.demov1;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import com.example.demov1.Entity.GroupEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.GoalViewHolder>{
//
//    private List<GroupEntity> groups;
//
//    GoalAdapter(List<GroupEntity> groups){
//        this.groups = groups;
//    }
//
//    @NonNull
//    @Override
//    public GoalViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.goal_tab, viewGroup, false);
//        return new GoalViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull GoalViewHolder holder, int position) {
//        holder.goalId.setText(groups.get(position).getGroupId());
//        holder.goalName.setText(groups.get(position).getGroupName());
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return groups.size();
//    }
//
//    @Override
//    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//    }
//
//    static class GoalViewHolder extends RecyclerView.ViewHolder {
//        CardView cv;
//        TextView goalId;
//        TextView goalName;
////        ImageView goalPhoto;
//
//        GoalViewHolder(View itemView) {
//            super(itemView);
//            cv = (CardView)itemView.findViewById(R.id.cv);
//            goalId = (TextView)itemView.findViewById(R.id.goal_id);
//            goalName = (TextView)itemView.findViewById(R.id.goal_name);
////            goalPhoto = (ImageView)itemView.findViewById(R.id.goal_photo);
//        }
//    }
//
////    private List<GroupEntity> initializeData() {
////        List<GroupEntity> myGoalList = new ArrayList<>();
////        myGoalList.add(new GroupEntity(1,"Goal 1", 3000, 1000));
////        myGoalList.add(new GroupEntity(2,"Goal 2", 3000, 1000));
////
////        return myGoalList;
////    }
//
//}
