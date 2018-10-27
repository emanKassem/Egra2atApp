package com.example.a2laa.egra2atapp.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a2laa.egra2atapp.R;
import com.example.a2laa.egra2atapp.app.App;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ServiceStepsAdapter extends RecyclerView.Adapter<ServiceStepsAdapter.ServiceStepsViewHolder> {

    List<String> steps;
    public ServiceStepsAdapter(List<String> steps){
        this.steps = steps;
    }


    @NonNull
    @Override
    public ServiceStepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(App.getContext()).inflate(R.layout.step_item, viewGroup, false);
        return new ServiceStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceStepsViewHolder holder, int i) {
        holder.stepName.setText(steps.get(i));
        holder.stepNo.setText(String.valueOf(i+1));
    }


    @Override
    public int getItemCount() {
        if (steps!=null)
            return steps.size();
        return 0;
    }

    public class ServiceStepsViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.stepName)
        TextView stepName;
        @BindView(R.id.stepNo)
        TextView stepNo;
        public ServiceStepsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
