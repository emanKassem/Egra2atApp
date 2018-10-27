package com.example.a2laa.egra2atapp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.model.Service;
import com.example.a2laa.egra2atapp.utils.PrefUtils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Egra2atFragment extends Fragment {

    @BindView(R.id.moneyET)
    EditText moneyET;
    @BindView(R.id.timeET)
    EditText timeET;
    @BindView(R.id.onlineET)
    EditText onlineET;
    @BindView(R.id.condition)
    AppCompatCheckBox condition;
    @BindView(R.id.accept)
    AppCompatCheckBox accept;
    @BindView(R.id.save)
    Button save;
    Context context = App.getContext();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_egra2at, container, false);
        ButterKnife.bind(this, view);
        String update = PrefUtils.getKeys(context, "update");
        if (update!=null){
            String serviceJson = PrefUtils.getKeys(context, "service");
            Gson gson = new Gson();
            final Service service = gson.fromJson(serviceJson, Service.class);
            if (service.getRepaymentOfViolations()!=null){
                condition.setChecked(true);
            }
            if (service.getNegotiability()!=null){
                accept.setChecked(true);
            }
            if (service.getFees()!=null){
                moneyET.setText(service.getFees());
            }
            if (service.getDuration()!=null){
                timeET.setText(service.getDuration());
            }
            if (service.getOnlineService()!=null){
                onlineET.setText(service.getOnlineService());
            }
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String json = PrefUtils.getKeys(context, "service");
                final Service service = gson.fromJson(json, Service.class);
                if (condition.isChecked()){
                    service.setRepaymentOfViolations("شرط");
                }
                if (accept.isChecked()){
                    service.setNegotiability("نعم");
                }
                service.setFees(moneyET.getText().toString());
                service.setDuration(timeET.getText().toString());
                service.setOnlineService(onlineET.getText().toString());
                json = gson.toJson(service);
                PrefUtils.storeKeys(context, "service", json);
                Toast.makeText(context, "تم الحفظ", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

