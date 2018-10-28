package com.example.a2laa.egra2atapp.view;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.model.Service;
import com.example.a2laa.egra2atapp.utils.PrefUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceFragment extends Fragment {

    @BindView(R.id.locationET)
    EditText locationET;
    @BindView(R.id.mapNameET)
    EditText mapNameET;
    @BindView(R.id.from)
    TextView from;
    @BindView(R.id.to)
    TextView to;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.addService)
    Button addService;
    Context context = App.getContext();
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_place, container, false);
        ButterKnife.bind(this, view);
        final String update = PrefUtils.getKeys(context, "update");
        if (update!=null){
            addService.setText("تعديل الاجراء");
            String serviceJson = PrefUtils.getKeys(context, "service");
            Gson gson = new Gson();
            Service service = gson.fromJson(serviceJson, Service.class);
            if (service.getWebsite()!=null){
                locationET.setText(service.getWebsite());
            }
            if (service.getLocation()!=null){
                mapNameET.setText(service.getLocation());
            }
            if (service.getWorkingHours()!=null){
                String[] workingHours = service.getWorkingHours().split(" - ");
                from.setText(workingHours[0]);
                to.setText(workingHours[1]);
            }
        }
        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(App.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        from.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("موعد بداية عمل المؤسسة");
                mTimePicker.show();
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(App.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        to.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("موعد نهاية عمل المؤسسة");
                mTimePicker.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String json = PrefUtils.getKeys(context, "service");
                final Service service = gson.fromJson(json, Service.class);
                service.setWebsite(locationET.getText().toString());
                service.setLocation(mapNameET.getText().toString());
                service.setWorkingHours(from.getText().toString() + " - " + to.getText().toString());
                json = gson.toJson(service);
                PrefUtils.storeKeys(context, "service", json);
                Toast.makeText(context, "تم الحفظ", Toast.LENGTH_SHORT).show();
            }
        });

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sectorName = PrefUtils.getKeys(context, "sectorName");
                String ministryName = PrefUtils.getKeys(context, "ministryName");
                Gson gson = new Gson();
                String json = PrefUtils.getKeys(context, "service");
                final Service service = gson.fromJson(json, Service.class);
                if (service.getServiceName()==null || service.getServiceName().equals("")){
                    Toast.makeText(context, "تعذر اضافة الاجراء برجاء اكمال البيانات اولا", Toast.LENGTH_LONG).show();
                }else {
                    progressDialog = new ProgressDialog(context,
                            R.style.AppTheme_Dark_Dialog);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("يتم إضافة الاجراء ...");
                    progressDialog.show();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();

                    if (update!=null){
                        String serviceName = PrefUtils.getKeys(context, "serviceName");
                        if (serviceName!=null && !serviceName.endsWith(service.getServiceName())){
                            DatabaseReference myRef = database.getReference(getString(R.string.ministries)).child(ministryName)
                                    .child(getString(R.string.sectors)).child(sectorName)
                                    .child(getString(R.string.services)).child(serviceName);
                            myRef.removeValue();
                        }
                    }

                    DatabaseReference myRef = database.getReference(getString(R.string.ministries)).child(ministryName)
                            .child(getString(R.string.sectors)).child(sectorName)
                            .child(getString(R.string.services)).child(service.getServiceName());
                    myRef.removeValue();
                    myRef.child(getString(R.string.service_name)).setValue(service.getServiceName());
                    if (service.getServiceSteps()!=null){
                        List<String> steps = new ArrayList<>(service.getServiceSteps().values());
                        for (int i = 1; i<=steps.size(); i++){
                            myRef.child(getString(R.string.service_steps)).child("step"+i).setValue(steps.get(i-1));
                        }
                    }
                    myRef.child(getString(R.string.nationality)).setValue(service.getNationality());
                    myRef.child(getString(R.string.negotiability)).setValue(service.getNegotiability());
                    myRef.child(getString(R.string.repayment_of_violations)).setValue(service.getRepaymentOfViolations());
                    myRef.child(getString(R.string.fees)).setValue(service.getFees());
                    myRef.child(getString(R.string.online_service)).setValue(service.getOnlineService());
                    if (service.getFiles()!=null){
                        StorageReference mStorageRef;
                        mStorageRef = FirebaseStorage.getInstance().getReference();
                        List<String> files = new ArrayList<>(service.getFiles().values());
                        for (int i = 1; i<=files.size(); i++){
                            Uri file = Uri.parse(files.get(i-1));
                            if (file.getLastPathSegment()!=null) {
                                myRef.child(getString(R.string.files)).child("file"+i).setValue(file.getLastPathSegment());
                                StorageReference riversRef = mStorageRef.child(file.getLastPathSegment());
                                String fileS = MimeTypeMap.getFileExtensionFromUrl(file.toString());
                                String f = file.toString().replace("."+fileS, "");
                                riversRef.putFile(Uri.parse(f)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    }
                                });
                            }
                            else {
                                myRef.child(getString(R.string.files)).child("file"+i).setValue(file.toString());
                            }
                        }

                    }
                    myRef.child(getString(R.string.duration)).setValue(service.getDuration());
                    myRef.child(getString(R.string.location)).setValue(service.getLocation());
                    myRef.child(getString(R.string.website)).setValue(service.getWebsite());
                    myRef.child(getString(R.string.working_hours)).setValue(service.getWorkingHours());
                    progressDialog.dismiss();
                    Toast.makeText(context, "تم اضافة الاجراء بنجاح", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
