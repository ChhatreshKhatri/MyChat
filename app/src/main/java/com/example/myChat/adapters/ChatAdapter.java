package com.example.myChat.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myChat.databinding.ItemContainerReceivedNessageBinding;
import com.example.myChat.databinding.ItemContainerSentMessageBinding;
import com.example.myChat.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final List<ChatMessage> chatMessages;
    private Bitmap receivedProfileImage;
    private final String senderId;

    public static final int VIEW_TYPE_SENT=1;
    public static final int VIEW_TYPE_RECEIVED=2;

    public void setReceivedProfileImage(Bitmap bitmap){
        receivedProfileImage=bitmap;
    }

    public ChatAdapter(List<ChatMessage> chatMessages, Bitmap receivedProfileImage, String senderId) {
        this.chatMessages = chatMessages;
        this.receivedProfileImage = receivedProfileImage;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==VIEW_TYPE_SENT){
            return new SentMessageViewerHolder(
                    ItemContainerSentMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }else {
            return new ReceivedMessageViewHolder(
                    ItemContainerReceivedNessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==VIEW_TYPE_SENT){
            ((SentMessageViewerHolder)holder).setData(chatMessages.get(position));
        }else {
            ((ReceivedMessageViewHolder)holder).setData(chatMessages.get(position),receivedProfileImage);
        }

    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(chatMessages.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT;
        }else{
            return VIEW_TYPE_RECEIVED;
        }
    }

    static class SentMessageViewerHolder extends RecyclerView.ViewHolder{
        private final ItemContainerSentMessageBinding binding;

        SentMessageViewerHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding){
            super(itemContainerSentMessageBinding.getRoot());
            binding=itemContainerSentMessageBinding;
        }

        void setData(ChatMessage chatMessage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
        }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerReceivedNessageBinding binding;
        ReceivedMessageViewHolder(ItemContainerReceivedNessageBinding itemContainerReceivedNessageBinding){
            super(itemContainerReceivedNessageBinding.getRoot());
            binding=itemContainerReceivedNessageBinding;
        }

        void setData(ChatMessage chatMessage,Bitmap receiverProfileImage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
            if(receiverProfileImage!=null) {
                binding.imageProfile.setImageBitmap(receiverProfileImage);
            }
        }
    }
}
