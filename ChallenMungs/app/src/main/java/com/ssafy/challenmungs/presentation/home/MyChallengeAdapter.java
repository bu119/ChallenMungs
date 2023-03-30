package com.ssafy.challenmungs.presentation.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssafy.challenmungs.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MyChallengeAdapter extends RecyclerView.Adapter<MyChallengeAdapter.ViewHolder> {

    // 던져줄 때는 1번만 던져주면 될듯 진행중인 애들만 필요하니까
    private ArrayList<HashMap<String, Object>> mData;

    public MyChallengeAdapter(ArrayList<HashMap<String, Object>> data) {
        mData = data;
    }

    @NonNull
    @Override
    public MyChallengeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_challenge_card_ongoing_finish, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyChallengeAdapter.ViewHolder holder, int position) {
        HashMap<String, Object> md = mData.get(position);
        holder.tv_title.setText(md.get("title").toString());
        holder.tv_price.setText(md.get("price").toString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_price;
        public TextView tv_tag;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_tag = (TextView) itemView.findViewById(R.id.tv_tag);
        }
    }
}
