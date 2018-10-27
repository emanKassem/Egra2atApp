package com.example.a2laa.egra2atapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.model.Ministry;
import com.example.a2laa.egra2atapp.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MinistriesFragment extends Fragment {

    @BindView(R.id.ministriesRecyclerView)
    RecyclerView ministriesRecyclerView;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.noMinistriesTV)
    TextView noMinistriesTV;
    Context context = App.getContext();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ministry_fragment, container, false);
        ButterKnife.bind(this, view);
        toolbar_title.setText("الوزارات");
        final RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        ministriesRecyclerView.setLayoutManager(manager);
        if (Utils.isOnline(context)) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(getString(R.string.ministries));
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> ministries = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Ministry ministry = dataSnapshot1.getValue(Ministry.class);
                        ministries.add(ministry.getMinistryName());
                    }
                    if (ministries.size() != 0) {
                        MinistriesAdapter adapter = new MinistriesAdapter(ministries);
                        ministriesRecyclerView.setAdapter(adapter);
                    } else {
                        noMinistriesTV.setVisibility(View.VISIBLE);
                        ministriesRecyclerView.setVisibility(View.GONE);
                        noMinistriesTV.setText("لا يوجد وزارات");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            noMinistriesTV.setVisibility(View.VISIBLE);
            ministriesRecyclerView.setVisibility(View.GONE);
            noMinistriesTV.setText("لا يوجد اتصال بالانترنت");
        }
        return view;
    }
}
