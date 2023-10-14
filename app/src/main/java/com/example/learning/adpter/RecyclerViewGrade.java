package com.example.learning.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learning.databinding.ItemGradIntructorBinding;
import com.example.learning.model.MemberModel;

import java.util.ArrayList;

public class RecyclerViewGrade extends RecyclerView.Adapter<RecyclerViewGrade.Holder> {

    ArrayList<MemberModel> list =new ArrayList<>();

    public void setList(ArrayList<MemberModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGradIntructorBinding binding = ItemGradIntructorBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return  new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(list.get(position).getStudentEmail(),list.get(position).getQuizGrade(),list.get(position).getAttendanceGrade());
    }

    @Override
    public int getItemCount() {

        return list==null?0: list.size();

    }

    class Holder extends RecyclerView.ViewHolder{
        ItemGradIntructorBinding binding;


        public Holder(ItemGradIntructorBinding binding) {

            super(binding.getRoot());
            this.binding= binding;

        }
        void bind(String name,double quiz,double attendance){
            binding.textName.setText(name);
            binding.textQuiz.setText(String.valueOf(quiz));
            binding.textAttendance.setText(String.valueOf(attendance));
        }

    }
}
