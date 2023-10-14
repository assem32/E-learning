package com.example.learning.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learning.databinding.ItemQuizBinding;
import com.example.learning.model.QuizModel;

import java.util.ArrayList;

public class RecyclerViewQuiz extends RecyclerView.Adapter<RecyclerViewQuiz.Holder> {


    ArrayList <QuizModel> list =new  ArrayList<>();

    private RecyclerViewQuiz.OnClickQuiz OnClickQuiz ;

    public void setOnClick(RecyclerViewQuiz.OnClickQuiz onClick) {
        this.OnClickQuiz = onClick;
    }

    public void setList(ArrayList<QuizModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemQuizBinding binding =ItemQuizBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list==null ? 0:list.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        ItemQuizBinding binding;

        public Holder(ItemQuizBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.getRoot().setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            OnClickQuiz.onItemClick(getLayoutPosition());
                        }
                    }
            );


        }

        public void  bind(int index){
            binding.textName.setText("Quiz "+index);
        }
    }
    public interface OnClickQuiz {
        void onItemClick(int index);
    }
}
