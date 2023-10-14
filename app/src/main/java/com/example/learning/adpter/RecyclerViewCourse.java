package com.example.learning.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learning.databinding.ItemSubjectBinding;
import com.example.learning.model.CourseModel;

import java.util.ArrayList;

public class RecyclerViewCourse extends RecyclerView.Adapter<RecyclerViewCourse.Holder>  {
    private ArrayList<CourseModel> list ;

    private OnClick onClick ;
    public void setList(ArrayList<CourseModel> list) {
        this.list = list;
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSubjectBinding binding= ItemSubjectBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.bind(list.get(position).getCourseName());

    }

    @Override
    public int getItemCount() {
        return list == null?0 :list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView textCourseName ;
        ItemSubjectBinding binding;

        public Holder(ItemSubjectBinding binding) {

            super(binding.getRoot());
            this.binding=binding;

            if (onClick != null){

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClick.onItemClick(list.get(getAdapterPosition()).getCourseId() , list.get(getAdapterPosition()).getCourseName());
                    }
                });
            }
        }

        void bind(String name){
            binding.textName.setText(name);
        }
    }

    public interface OnClick {

        void onItemClick(String id , String name);
    }
}
