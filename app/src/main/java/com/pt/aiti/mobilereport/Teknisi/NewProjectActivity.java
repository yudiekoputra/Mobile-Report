package com.pt.aiti.mobilereport.Teknisi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Utility.Constanta;

public class NewProjectActivity extends AppCompatActivity {
    private Context context = this;
    private Spinner project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);
        project = findViewById(R.id.project);
        setSpinnerData();
    }

    private void setSpinnerData() {
        ArrayAdapter<String> adapterProject = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, Constanta.PROJECT);
        adapterProject.setDropDownViewResource(android.R.layout.simple_spinner_item);
        project.setAdapter(adapterProject);
    }
}
