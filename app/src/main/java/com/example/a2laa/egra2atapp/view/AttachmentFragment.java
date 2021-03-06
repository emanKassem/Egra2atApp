package com.example.a2laa.egra2atapp.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.model.Service;
import com.example.a2laa.egra2atapp.utils.PrefUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttachmentFragment extends Fragment{

    @BindView(R.id.addFilesFAB)
    FloatingActionButton addFilesFAB;
    @BindView(R.id.attachmentsRecyclerView)
    RecyclerView attachmentsRecyclerView;
    @BindView(R.id.NoAttachmentsTV)
    TextView NoAttachmentsTV;
    Context context = App.getContext();
    AttachmentsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attachment, container, false);
        ButterKnife.bind(this, view);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        attachmentsRecyclerView.setLayoutManager(manager);
        String update = PrefUtils.getKeys(context, "update");
        if (update!=null){
            String serviceJson = PrefUtils.getKeys(context, "service");
            Gson gson = new Gson();
            Service service = gson.fromJson(serviceJson, Service.class);
            if (service.getFiles()!=null){
                List<Uri> filesUris = new ArrayList<>();
                for (String file: service.getFiles().values()){
                    filesUris.add(Uri.parse(file));
                    adapter = new AttachmentsAdapter(filesUris);
                    attachmentsRecyclerView.setAdapter(adapter);
                    NoAttachmentsTV.setVisibility(View.GONE);
                }
            }else {
                NoAttachmentsTV.setVisibility(View.VISIBLE);
            }
        }
        addFilesFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, 1);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                String serviceJson = PrefUtils.getKeys(context, "service");
                Gson gson = new Gson();
                Service service = gson.fromJson(serviceJson, Service.class);
                List<Uri> filesUris = new ArrayList<>();
                if (service!=null && service.getFiles()!=null){
                    for (String file: service.getFiles().values()){
                        filesUris.add(Uri.parse(file));
                    }
                }
                ContentResolver cr = context.getContentResolver();
                if(data.getClipData() != null) {
                    NoAttachmentsTV.setVisibility(View.GONE);
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;
                    while(currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        String mime = cr.getType(imageUri);
                        String[] file = mime.split("/");
                        String extension  = file[1];
                        String fileUri = data.getData().toString().concat("."+extension);
                        Uri uri = Uri.parse(fileUri);
                        filesUris.add(filesUris.size(), uri);
                        currentItem = currentItem + 1;
                    }
                } else if(data.getData() != null) {
                    NoAttachmentsTV.setVisibility(View.GONE);
                    String mime = cr.getType(data.getData());
                    String[] file = mime.split("/");
                    String extension  = file[1];
                    String fileUri = data.getData().toString().concat("."+extension);
                    Uri uri = Uri.parse(fileUri);
                    filesUris.add(filesUris.size(), uri);

                }
                adapter = new AttachmentsAdapter(filesUris);
                attachmentsRecyclerView.setAdapter(adapter);
                List<String> files = new ArrayList<>();
                for (Uri file: filesUris){
                    files.add(file.toString());
                }
                Map<String , String> filesMap = new HashMap<>();
                for (int i = 1; i<=files.size(); i++){
                    filesMap.put("file"+i, files.get(i-1));
                }
                service.setFiles(filesMap);
                String json = gson.toJson(service);
                PrefUtils.storeKeys(context, "service", json);
            }
        }

    }


}
