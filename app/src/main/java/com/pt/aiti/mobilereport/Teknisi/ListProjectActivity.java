package com.pt.aiti.mobilereport.Teknisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pt.aiti.mobilereport.Admin.HomeAdminActivity;
import com.pt.aiti.mobilereport.Direktur.HomeDirekturActivity;
import com.pt.aiti.mobilereport.R;
import com.pt.aiti.mobilereport.Utility.ListAdapter;
import com.pt.aiti.mobilereport.Utility.SessionManager;
import com.pt.aiti.mobilereport.Utility.inputProject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ListProjectActivity extends AppCompatActivity {

    private Context context = this;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference reference;
    private ArrayList<inputProject> dataSurvey;
    private FirebaseAuth auth;
    private com.pt.aiti.mobilereport.Utility.ListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_project);

        recyclerView = findViewById(R.id.datalist);
        auth = FirebaseAuth.getInstance();
        MyRecyclerView();
        GetData();

    }

    private void GetData(){
        Toast.makeText(getApplicationContext(), "Mohon Tunggu Sebentar...", Toast.LENGTH_LONG).show();
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("inputProject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSurvey = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    inputProject survey = snapshot.getValue(inputProject.class);
                    survey.setKey(snapshot.getKey());
                    dataSurvey.add(survey);
                    Toast.makeText(getApplicationContext(),"Data Berhasil Dimuat", Toast.LENGTH_LONG).show();
                }
                listAdapter = new ListAdapter(dataSurvey, context);
                recyclerView.setAdapter(listAdapter);
                listAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
            }
        });
    }

    private void MyRecyclerView(){
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        String text = SessionManager.getUsername(context);
        if (text.equalsIgnoreCase("admin")){
            Intent intent = new Intent(context, HomeAdminActivity.class);
            startActivity(intent);
            finish();
        }else if (text.equalsIgnoreCase("direktur")){
            Intent intent = new Intent(context, HomeDirekturActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(context, HomeTeknisiActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
