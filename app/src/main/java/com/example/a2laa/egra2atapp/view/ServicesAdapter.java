package com.example.a2laa.egra2atapp.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.model.Service;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder>{

    List<Service> services;
    String ministryName;
    String sectorName;
    Context context = App.getContext();
    public ServicesAdapter(List<Service> services, String ministryName, String sectorName){
        this.services = services;
        this.ministryName = ministryName;
        this.sectorName = sectorName;
    }
    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.service_item, viewGroup, false);
        return new ServicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ServicesViewHolder holder, int i) {
        holder.serviceNameTV.setText(services.get(i).getServiceName());
        holder.editService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminActivity.class);
                intent.putExtra("ministryName", ministryName);
                intent.putExtra("sectorName", sectorName);
                intent.putExtra("serviceName", services.get(holder.getAdapterPosition()).getServiceName());
                Gson gson = new Gson();
                String json = gson.toJson(services.get(holder.getAdapterPosition()));
                intent.putExtra("service", json);
                context.startActivity(intent);
            }
        });
        holder.deleteService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               FirebaseDatabase database = FirebaseDatabase.getInstance();
               database.getReference(context.
                        getString(R.string.ministries)).child(ministryName).child(context.getString(R.string.sectors))
                       .child(sectorName).child(context.getString(R.string.services))
                       .child(services.get(holder.getAdapterPosition()).getServiceName()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (services!=null)
            return services.size();
        return 0;
    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.serviceNameTV)
        TextView serviceNameTV;
        @BindView(R.id.editService)
        Button editService;
        @BindView(R.id.deleteService)
        Button deleteService;
        @BindView(R.id.serviceItem)
        ConstraintLayout parent;
        public ServicesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
