package com.example.a2laa.egra2atapp.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.model.Sector;
import com.example.a2laa.egra2atapp.model.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SectorsAdapter extends RecyclerView.Adapter<SectorsAdapter.SectorsViewHolder> {

    private List<String> sectors;
    private String ministryName;
    private Context context = App.getContext();
    public SectorsAdapter(List<String> sectors, String ministryName) {
        this.sectors = sectors;
        this.ministryName = ministryName;
    }

    public void AddSector(String sectorName){
        sectors.add(sectors.size(), sectorName);
        notifyItemInserted(sectors.size()-1);
    }
    @NonNull
    @Override
    public SectorsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.sector_item, viewGroup, false);
        return new SectorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SectorsViewHolder holder, int i) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference(context.getString(R.string.ministries));
        holder.sectorNameTV.setText(sectors.get(i));
        holder.deleteSector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sector = sectors.get(holder.getAdapterPosition());
                myRef.child(ministryName).child(context.getString(R.string.sectors)).child(sector).removeValue();
            }
        });
        holder.sectorParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServicesFragment fragment = new ServicesFragment();
                Bundle bundle = new Bundle();
                bundle.putString("ministryName", ministryName);
                bundle.putString("sectorName", sectors.get(holder.getAdapterPosition()));
                fragment.setArguments(bundle);
                ((Main2Activity) context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.replaceFrameLayout, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(sectors!=null)
            return sectors.size();
        return 0;
    }

    public class SectorsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.sectorNameTV)
        TextView sectorNameTV;
        @BindView(R.id.deleteSector)
        Button deleteSector;
        @BindView(R.id.sectorParent)
        ConstraintLayout sectorParent;
        public SectorsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
