package com.example.a2laa.egra2atapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.utils.Utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MinistriesAdapter extends RecyclerView.Adapter<MinistriesAdapter.MinistriesViewHolder> {

    List<String> ministriesNames;
    List<Integer> ministriesLogos;
    Map<String, Integer> ministriesMap;
    Context context = App.getContext();
    public MinistriesAdapter(Map<String, Integer> ministriesMap){
        this.ministriesMap = ministriesMap;
        this.ministriesLogos = new ArrayList<>(ministriesMap.values());
        this.ministriesNames = new ArrayList<>(ministriesMap.keySet());
    }
    @NonNull
    @Override
    public MinistriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ministry_item, viewGroup, false);
        return new MinistriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MinistriesViewHolder holder, int i) {
        holder.ministryNameTV.setText(ministriesNames.get(i));
        holder.ministryLogo.setImageResource(ministriesLogos.get(i));
        holder.ministryParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SectorsFragment fragment = new SectorsFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ministryName", ministriesNames.get(holder.getAdapterPosition()));
                fragment.setArguments(bundle);
                ((Main2Activity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.replaceFrameLayout, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ministriesMap!=null)
            return ministriesMap.size();
        return 0;
    }

    public class MinistriesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.ministryNameTV)
        TextView ministryNameTV;
        @BindView(R.id.ministryLogo)
        ImageView ministryLogo;
        @BindView(R.id.ministryParent)
        FrameLayout ministryParent;
        public MinistriesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
