package com.example.drikkelek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PackRecyclerAdapter extends RecyclerView.Adapter<PackRecyclerAdapter.MyViewHolder> {

    private ArrayList<Pack> packList;
    private OnPackListener mOnPackListener;

    public PackRecyclerAdapter(ArrayList<Pack> packList, OnPackListener onPackListener) {
        this.packList = packList;
        this.mOnPackListener = onPackListener;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView packView;
        OnPackListener onPackListener;

        public MyViewHolder(final View view, OnPackListener onPackListener) {
            super(view);
            packView = view.findViewById(R.id.pack_btn);
            this.onPackListener = onPackListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPackListener.onPackClick(getAdapterPosition());
        }
    }

    public interface OnPackListener {
        void onPackClick(int position);
    }

    @NonNull
    @Override
    public PackRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pack_item, parent, false);
        return new MyViewHolder(itemView, mOnPackListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PackRecyclerAdapter.MyViewHolder holder, int position) {
        String name = packList.get(position).getPackName();
        holder.packView.setText(name);

    }

    @Override
    public int getItemCount() {
        return packList.size();
    }
}
