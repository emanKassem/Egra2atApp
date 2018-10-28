package com.example.a2laa.egra2atapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.model.Sector;
import com.example.a2laa.egra2atapp.model.Service;
import com.example.a2laa.egra2atapp.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServicesFragment extends Fragment {

    @BindView(R.id.sectorsRecyclerView)
    RecyclerView servicesRecyclerView;
    @BindView(R.id.addSectorTV)
    TextView addServiceTV;
    @BindView(R.id.noSectorsTV)
    TextView noServicesTV;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.addSectorLinear)
    LinearLayout addSectorLinear;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    Context context = App.getContext();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sectors_fragment, container, false);
        ButterKnife.bind(this, view);
        toolbar_title.setText("الاجراءات");
        addServiceTV.setText("إضافة إجراء");
        final String ministryName = getArguments().getString("ministryName");
        final String sectorName = getArguments().getString("sectorName");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        servicesRecyclerView.setLayoutManager(manager);
        if (Utils.isOnline(context)){
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(getString(R.string.ministries)).child(ministryName);
            progressBar.setVisibility(View.VISIBLE);
            myRef.child(getString(R.string.sectors)).child(sectorName)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            Sector sector = dataSnapshot.getValue(Sector.class);
                            if (sector!=null && sector.getServices()!=null){
                                noServicesTV.setVisibility(View.GONE);
                                List<Service> services = new ArrayList<>(sector.getServices().values());
                                ServicesAdapter adapter = new ServicesAdapter(services, ministryName, sectorName);
                                servicesRecyclerView.setAdapter(adapter);
                            }else {
                                noServicesTV.setVisibility(View.VISIBLE);
                                noServicesTV.setText("لا يوجد إجراءات");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }

        addSectorLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminActivity.class);
                intent.putExtra("ministryName", ministryName);
                intent.putExtra("sectorName", sectorName);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
