package com.example.drikkelek;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RuleRecyclerAdapter extends RecyclerView.Adapter<RuleRecyclerAdapter.MyViewHolder> {

    private ArrayList<Rule> rules = new ArrayList<>();

    public RuleRecyclerAdapter(ArrayList<Rule> rules) {
        this.rules = rules;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView ruleText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ruleText = itemView.findViewById(R.id.rule_item_text_view);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rule_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String rule = rules.get(position).getRule();
        holder.ruleText.setText(rule);
    }

    @Override
    public int getItemCount() {
        return rules.size();
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }
}
