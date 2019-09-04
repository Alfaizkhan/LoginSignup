package com.example.loginsignup.Activity;

import android.os.Bundle;

import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsignup.Adapter.DataAdapter;
import com.example.loginsignup.Adapter.DataViewModel;

import com.example.loginsignup.Model.Data;
import com.example.loginsignup.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;


import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    DataAdapter adapter;
    private DataViewModel mViewModel;
    private ImageView logoutbutton;
    GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        // View Model for list Users
        mViewModel = ViewModelProviders.of(this).get(DataViewModel.class);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DataAdapter(this, null);
        recyclerView.setAdapter(adapter);

        mViewModel.getData().observe(this, new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> data) {
                adapter.updateDataList(data);
            }
        });

        mViewModel.loadData();
    }


}






