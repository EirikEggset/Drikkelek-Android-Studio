package com.example.drikkelek;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlayerRecyclerAdapter extends RecyclerView.Adapter<PlayerRecyclerAdapter.MyViewHolder> {
    private ArrayList<Player> playerNames;

    public PlayerRecyclerAdapter(ArrayList<Player> playerNames) {
        this.playerNames = playerNames;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private EditText newNameText;
        private Button removePlayerBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            newNameText = itemView.findViewById(R.id.new_player_name);
            removePlayerBtn = itemView.findViewById(R.id.btn_remove_player);

            removePlayerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerNames.size() > 2) {
                        playerNames.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                }
            });
            //Adds players as EditText is changed
            newNameText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    playerNames.get(getAdapterPosition()).setName(newNameText.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    @NonNull
    @Override
    public PlayerRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_name_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerRecyclerAdapter.MyViewHolder holder, int position) {
        String name = playerNames.get(position).getName();
        holder.newNameText.setText(name);
    }

    @Override
    public int getItemCount() {
        return playerNames.size();
    }

    public ArrayList<Player> getPlayerNames() {
        ArrayList<Player> returnList = new ArrayList<>();
        for (int i = 0; i < playerNames.size(); i++) {
            if (!(playerNames.get(i).getName().equals(""))) {
                returnList.add(playerNames.get(i));
            }
        }
        return returnList;
    }
}
