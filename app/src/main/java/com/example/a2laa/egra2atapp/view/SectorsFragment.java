package com.example.a2laa.egra2atapp.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.model.Ministry;
import com.example.a2laa.egra2atapp.model.Sector;
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

public class SectorsFragment extends Fragment {

    @BindView(R.id.sectorsRecyclerView)
    RecyclerView sectorsRecyclerView;
    @BindView(R.id.addSectorTV)
    TextView addSectorTV;
    @BindView(R.id.noSectorsTV)
    TextView noSectorsTV;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.addSectorLinear)
    LinearLayout addSectorLinear;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    Context context = App.getContext();
    EditText newSector;
    Button addSector;
    Button cancelSector;
    TextView imageView7;
    SectorsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sectors_fragment, container, false);
        ButterKnife.bind(this, view);
        final RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        sectorsRecyclerView.setLayoutManager(manager);
        final String ministryName = getArguments().getString("ministryName");
        toolbar_title.setText("قطاعات "+ ministryName);
        if (Utils.isOnline(context)) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(getString(R.string.ministries)).child(ministryName);
            progressBar.setVisibility(View.VISIBLE);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressBar.setVisibility(View.GONE);
                    Ministry ministry = dataSnapshot.getValue(Ministry.class);
                    if (ministry!=null && ministry.getSectors()!=null) {
                        List<Sector> sectors = new ArrayList<>(ministry.getSectors().values());
                        List<String> sectorsName = new ArrayList<>();
                        for (Sector sector : sectors){
                            sectorsName.add(sector.getSectorName());
                        }
                        SectorsAdapter adapter = new SectorsAdapter(sectorsName, ministry.getMinistryName());
                        sectorsRecyclerView.setAdapter(adapter);
                    }
                     else {
                        noSectorsTV.setVisibility(View.VISIBLE);
                        sectorsRecyclerView.setVisibility(View.GONE);
                        noSectorsTV.setText("لا يوجد قطاعات");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        } else {
            noSectorsTV.setVisibility(View.VISIBLE);
            sectorsRecyclerView.setVisibility(View.GONE);
            noSectorsTV.setText("لا يوجد اتصال بالانترنت");
        }
        addSectorLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.step_popup_window, null);
                //Get the devices screen density to calculate correct pixel sizes
                float density = context.getResources().getDisplayMetrics().density;
                // create a focusable PopupWindow with the given layout and correct size
                final PopupWindow pw = new PopupWindow(layout, (int) density * 300, ViewPager.LayoutParams.WRAP_CONTENT, true);
                pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pw.setTouchInterceptor(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            pw.dismiss();
                            return true;
                        }
                        return false;
                    }
                });
                pw.setOutsideTouchable(true);
                newSector = layout.findViewById(R.id.newStep);
                addSector = layout.findViewById(R.id.addStep);
                cancelSector= layout.findViewById(R.id.cancelStep);
                imageView7 = layout.findViewById(R.id.imageView7);
                imageView7.setText("إضافة قطاع");
                addSector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (newSector.getText()!=null && !newSector.getText().toString().equals("")) {
                            if (Utils.isOnline(context)) {
                                String sectorName = newSector.getText().toString();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                database.getReference(getString(R.string.ministries)).child(ministryName).
                                child(context.getString(R.string.sectors)).child(sectorName)
                                        .child(getString(R.string.sector_name)).setValue(sectorName);
                                if (adapter!=null){
                                    adapter.AddSector(sectorName);
                                    adapter.notifyDataSetChanged();
                                }else {
                                    sectorsRecyclerView.setVisibility(View.VISIBLE);
                                    List<String> sectors = new ArrayList<>();
                                    sectors.add(sectorName);
                                    adapter = new SectorsAdapter(sectors, ministryName);
                                    sectorsRecyclerView.setAdapter(adapter);
                                }
                            }
                        }else {
                            Toast.makeText(context, "برجاء ادخال اسم القطاع", Toast.LENGTH_LONG).show();
                        }
                        pw.dismiss();
                    }
                });
                cancelSector.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pw.dismiss();
                    }
                });

                try {
                    pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return view;
    }
}
