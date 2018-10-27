package com.example.a2laa.egra2atapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MinistriesAdapter extends RecyclerView.Adapter<MinistriesAdapter.MinistriesViewHolder> {

    List<String> ministries;
    Context context = App.getContext();
    public MinistriesAdapter(List<String> ministries){
        this.ministries = ministries;
    }
    @NonNull
    @Override
    public MinistriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ministry_item, viewGroup, false);
        return new MinistriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MinistriesViewHolder holder, int i) {
        holder.ministryNameTV.setText(ministries.get(i));
        holder.ministryNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SectorsFragment fragment = new SectorsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ministryName", ministries.get(holder.getAdapterPosition()));
                fragment.setArguments(bundle);
                ((Main2Activity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.replaceFrameLayout, fragment).addToBackStack(null).commit();

        }
        });
    }

    @Override
    public int getItemCount() {
        if (ministries!=null)
            return ministries.size();
        return 0;
    }

    public class MinistriesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ministryNameTV)
        TextView ministryNameTV;
        public MinistriesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
