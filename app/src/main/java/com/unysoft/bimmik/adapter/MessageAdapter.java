package com.unysoft.bimmik.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unysoft.bimmik.R;
import com.unysoft.bimmik.model.MessageItem;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MessageItem> message;
    private Context context;

    public MessageAdapter(List<MessageItem> message, Context context) {
        this.message = message;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zzz_pesan,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        final MessageItem messageItem = message.get(position);
        holder.tVName.setText(messageItem.getFrom_user_id());
        holder.tVTitle.setText(messageItem.getTitle());
        holder.tVMessage.setText(messageItem.getMessage());
        holder.tVTime.setText(messageItem.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tVName, tVTitle, tVMessage, tVTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tVName = itemView.findViewById(R.id.mhs_pesan_tvNama);
            tVTitle = itemView.findViewById(R.id.mhs_pesan_tvTitle);
            tVMessage = itemView.findViewById(R.id.mhs_pesan_tvMessage);
            tVTime = itemView.findViewById(R.id.mhs_pesan_tvTimestamp);
        }
    }
}
