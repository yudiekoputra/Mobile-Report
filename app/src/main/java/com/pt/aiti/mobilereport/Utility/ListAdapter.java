package com.pt.aiti.mobilereport.Utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Teknisi.ViewProjectActivity;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<inputProject> listSurvey;
    private Context context;

    public ListAdapter(ArrayList<inputProject> listSurvey, Context context) {
        this.listSurvey = listSurvey;
        this.context = context;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, final int position) {
            final String namaProject = listSurvey.get(position).getNamaProject();
        final String lokasi = listSurvey.get(position).getLokasi();
        final String tanggal = listSurvey.get(position).getTanggalProject();

        holder.namaProject.setText(namaProject);
        holder.lokasi.setText(lokasi);
        holder.tanggalProject.setText(tanggal);

        holder.ListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TemporaryData.setDataListAdapter(listSurvey.get(position));
                Bundle bundle = new Bundle();
                bundle.putString("getPrimaryKey", listSurvey.get(position).getKey());
                Intent intent = new Intent(v.getContext(), ViewProjectActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listSurvey.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namaProject, lokasi, tanggalProject;
        private LinearLayout ListItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaProject = itemView.findViewById(R.id.namaProject);
            lokasi = itemView.findViewById(R.id.lokasi);
            tanggalProject = itemView.findViewById(R.id.tanggal);
            ListItem = itemView.findViewById(R.id.list_item);
        }
    }
}
