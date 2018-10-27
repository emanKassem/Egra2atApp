package com.example.a2laa.egra2atapp.view;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;
import com.example.a2laa.egra2atapp.model.Service;
import com.example.a2laa.egra2atapp.utils.PrefUtils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttachmentsAdapter extends RecyclerView.Adapter<AttachmentsAdapter.AttachmentsViewHolder> {

    List<Uri> filesUris;
    Context context = App.getContext();
    public AttachmentsAdapter(List<Uri> filesUris){
        this.filesUris = filesUris;
    }

    public List<Uri> getFilesUris(){
        return filesUris;
    }
    @NonNull
    @Override
    public AttachmentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.attachment_item, viewGroup, false);
        return new AttachmentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttachmentsViewHolder holder, int i) {
        if (filesUris.get(i).getLastPathSegment()==null){
            holder.attachmentName.setText(filesUris.get(i).toString());
        }else {
            holder.attachmentName.setText(filesUris.get(i).getLastPathSegment());
        }
        holder.deleteAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String service  = PrefUtils.getKeys(context, "service");
                Gson gson = new Gson();
                Service service1 = gson.fromJson(service, Service.class);
                service1.getFiles().values().remove(filesUris.get(holder.getAdapterPosition()).toString());
                service = gson.toJson(service1);
                PrefUtils.storeKeys(context, "service", service);
                StorageReference mStorageRef;
                mStorageRef = FirebaseStorage.getInstance().getReference().child(filesUris.get(holder.getAdapterPosition()).toString());
                mStorageRef.delete();
                filesUris.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (filesUris!=null)
            return filesUris.size();
        return 0;
    }

    public class AttachmentsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.attachmentName)
        TextView attachmentName;
        @BindView(R.id.deleteAttachment)
        Button deleteAttachment;
        public AttachmentsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
