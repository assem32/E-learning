package com.example.learning.adpter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learning.Constants;
import com.example.learning.databinding.ItemChatOtherBinding;
import com.example.learning.model.MessageModel;

import java.util.ArrayList;

public class RecyclerViewMessage extends RecyclerView.Adapter<RecyclerViewMessage.Holder>{

    ArrayList <MessageModel> list =new ArrayList<>();

    public void setList(ArrayList<MessageModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChatOtherBinding binding =ItemChatOtherBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if(list.get(position).getSenderId()!=Constants.User.getUid()) {

            holder.binding.messageCard.setCardBackgroundColor(0xffC35CEF);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.binding.messageCard.getLayoutParams();
            layoutParams.gravity = Gravity.END;
            holder.binding.messageCard.setLayoutParams(layoutParams);
            holder.binding.messageN.setText(list.get(position).getMessage());
            holder.binding.messageName.setText("");
//            holder.constraintLayout.setLayoutParams(params);
        }
        else
        {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.binding.messageCard.getLayoutParams();
            layoutParams.gravity = Gravity.START;
            holder.binding.messageCard.setLayoutParams(layoutParams);
            holder.binding.messageCard.setCardBackgroundColor(0xffD3D3D3);
            holder.binding.messageN.setText(list.get(position).getMessage());
            holder.binding.messageName.setText(list.get(position).getSenderName());


        }

    }

    @Override
    public int getItemCount() {
        return list==null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        ItemChatOtherBinding binding;

        public Holder(ItemChatOtherBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

        }
    }
}
