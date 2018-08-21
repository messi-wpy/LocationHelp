package com.example.messi_lp.qiniuyun.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.messi_lp.qiniuyun.R;

import java.util.List;

class MyAdapter extends RecyclerView.Adapter {
    private List<String> list;
    private Context context;

    public MyAdapter(List<String> list, Context context){
        this.list = list;
        this.context =context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item,viewGroup,false);
        return new MyHolder(view,list.get(i));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nameText;
        private String name;

        public MyHolder(View itemView,String name) {
            super(itemView);
            this.name = name;
            nameText = itemView.findViewById(R.id.place_name);
            nameText.setText(name);
            nameText.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context,ChangeActivity.class);
            intent.putExtra("name",name);
            context.startActivity(intent);
        }
    }
}
