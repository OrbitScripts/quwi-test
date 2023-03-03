package com.orbitsoft.quwi.ui.listFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.orbitsoft.quwi.R;
import com.orbitsoft.quwi.ui.authFragment.models.ChannelInfo;

import java.util.List;

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ViewHolder> {

    private final List<ChannelInfo> mData;
    private final LayoutInflater mInflater;

    // data is passed into the constructor
    ChannelsAdapter(Context context, List<ChannelInfo> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_channel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChannelInfo channelInfo = mData.get(position);
        holder.name.setText(channelInfo.getUser().getName());
        if (channelInfo.getChannel().getMessageLast() != null) {
            holder.lastMsg.setText(channelInfo.getChannel().getMessageLast().getMessage());
            holder.lastUpd.setText(channelInfo.getChannel().getMessageLast().getLastUpd().substring(11, 16));
        }
        if (channelInfo.getUser().getAvatarUrl() != null) {
            Glide.with(holder.itemView.getContext()).load(channelInfo.getUser().getAvatarUrl())
                    .placeholder(R.drawable.ic_placeholder)
                    .into(holder.avatar);
        }
        if (channelInfo.getUser().isSavedMessages()) {
            holder.avatar.setImageDrawable(
                    AppCompatResources.getDrawable(holder.itemView.getContext(), R.drawable.logo)
            );
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name;
        TextView lastMsg;
        TextView lastUpd;

        ViewHolder(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.ivAvatar);
            name = itemView.findViewById(R.id.name);
            lastMsg = itemView.findViewById(R.id.lastMsg);
            lastUpd = itemView.findViewById(R.id.lastUpd);
        }
    }
}
