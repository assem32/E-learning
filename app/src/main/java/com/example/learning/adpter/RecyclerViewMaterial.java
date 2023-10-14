package com.example.learning.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learning.databinding.ItemQuizBinding;

import java.util.ArrayList;

public class RecyclerViewMaterial extends RecyclerView.Adapter<RecyclerViewMaterial.Holder>{


    ArrayList<String>list = new ArrayList<>();

    private RecyclerViewMaterial.OnClickMaterial onClickMaterial;

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public void setOnClickMaterial(OnClickMaterial onClickMaterial) {
        this.onClickMaterial = onClickMaterial;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemQuizBinding binding =ItemQuizBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind("Material"+position+1);
    }

    @Override
    public int getItemCount() {
        return list==null?0: list.size();
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
                            onClickMaterial.onItemClick(getLayoutPosition());
                        }
                    }
            );
        }
        void bind(String name ){
            binding.textName.setText(name);
        }
    }

    public interface OnClickMaterial {
        void onItemClick(int index);
    }
}
