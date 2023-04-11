package com.example.realmcrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class ShowData extends AppCompatActivity {

    List<DataModel> dataModelList;
    RecyclerView recyclerView;
    Realm realm;
    RecyclerViewAdapter recyclerViewAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data1);

        recyclerView=findViewById(R.id.Rcylst);
        realm= Realm.getDefaultInstance();

        dataModelList = new ArrayList<>();


        dataModelList= realm.where(DataModel.class).findAll();

        recyclerViewAdapter=new RecyclerViewAdapter(dataModelList,this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapter);

    }
}