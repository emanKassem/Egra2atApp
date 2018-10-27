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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.model.Ministry;
import com.example.a2laa.egra2atapp.model.Sector;
import com.example.a2laa.egra2atapp.model.Service;
import com.example.a2laa.egra2atapp.utils.PrefUtils;
import com.example.a2laa.egra2atapp.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoFragment extends Fragment {

    @BindView(R.id.serviceNameET)
    EditText serviceNameET;
    @BindView(R.id.notSaudi)
    RadioButton notSaudi;
    @BindView(R.id.saudi)
    RadioButton saudi;
    @BindView(R.id.stepsRecyclerView)
    RecyclerView stepsRecyclerView;
    @BindView(R.id.addStep)
    Button addStep;
    @BindView(R.id.save)
    Button save;
    EditText newStep;
    Button addStep2;
    Button cancelStep;
    Context context = App.getContext();
    List<String> steps = new ArrayList<>();
    ServiceStepsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        stepsRecyclerView.setLayoutManager(manager);
        String update = PrefUtils.getKeys(context, "update");
        String serviceJson = PrefUtils.getKeys(context, "service");
        Gson gson = new Gson();
        final Service service = gson.fromJson(serviceJson, Service.class);
        if (update != null) {
            if (service.getServiceName() != null) {
                serviceNameET.setText(service.getServiceName());
            }
            if (service.getNationality() != null) {
                if (service.getNationality().equals("سعودى")) {
                    saudi.setChecked(true);
                } else {
                    notSaudi.setChecked(true);
                }
            }
            if (service.getServiceSteps() != null) {
                steps = (List<String>) service.getServiceSteps().values();
            }
        }
        adapter = new ServiceStepsAdapter(steps);
        stepsRecyclerView.setAdapter(adapter);

        notSaudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.setNationality("غير سعودى");
            }
        });
        saudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.setNationality("سعودى");
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.setServiceName(serviceNameET.getText().toString());
                if (steps.size() != 0) {
                    Map<String , String> stepsMap = new HashMap<>();
                    for (int i = 1; i<=steps.size(); i++){
                        stepsMap.put("step"+i, steps.get(i-1));
                    }
                    service.setServiceSteps(stepsMap);
                }
                Gson gson = new Gson();
                String json = gson.toJson(service);
                PrefUtils.storeKeys(context, "service", json);
                Toast.makeText(context, "تم الحفظ", Toast.LENGTH_SHORT).show();
            }
        });
        addStep.setOnClickListener(new View.OnClickListener() {
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
                newStep = layout.findViewById(R.id.newStep);
                addStep2 = layout.findViewById(R.id.addStep);
                cancelStep = layout.findViewById(R.id.cancelStep);
                addStep2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        steps.add(steps.size(), newStep.getText().toString());
                        adapter.notifyItemInserted(steps.size() - 1);
                        stepsRecyclerView.scrollToPosition(steps.size() - 1);
                        pw.dismiss();
                    }
                });
                cancelStep.setOnClickListener(new View.OnClickListener() {
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
