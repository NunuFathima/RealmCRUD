package com.example.realmcrud;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

public class update extends AppCompatActivity {
    EditText cname, cdur, ctrk, cdesc;
    Button up,dt;
    Realm realm;
    Long id;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        cname=findViewById(R.id.idEdtCourseName);
        cdur=findViewById(R.id.idEdtCourseDuration);
        ctrk=findViewById(R.id.idEdtCourseTracks);
        cdesc=findViewById(R.id.idEdtCourseDescription);
        up=findViewById(R.id.idBtnUpdateCourse);
        dt=findViewById(R.id.udeletebutton);

        realm=Realm.getDefaultInstance();

        Intent intent=getIntent();

        id=intent.getLongExtra("id",0);
        cname.setText(intent.getStringExtra("name"));
        cdur.setText(intent.getStringExtra("duration"));
        ctrk.setText(intent.getStringExtra("track"));
        cdesc.setText(intent.getStringExtra("description"));

        dt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse(id);
                startActivity(new Intent(update.this,ShowData.class));
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un=cname.getText().toString();
                String udu=cdur.getText().toString();
                String utr=ctrk.getText().toString();
                String ude=cdesc.getText().toString();

                if (TextUtils.isEmpty(un)){
                    cname.setError("name is empty");
                }
                else if (TextUtils.isEmpty(udu)){
                    cdur.setError("duration is empty");
                }
                else if (TextUtils.isEmpty(utr)){
                    ctrk.setError("track is empty");
                }
                else if (TextUtils.isEmpty(ude)){
                    cdesc.setError("description is empty");
                }
                else{
                    final DataModel model=realm.where(DataModel.class).equalTo("id",id).findFirst();
                    updateCourse(model,un,udu,utr,ude);
                }
                Toast.makeText(update.this,"course update",Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(update.this,ShowData.class);
                startActivity(intent1);
                finish();
            }
        });
    }
    private void deleteCourse(Long id){
        DataModel model=realm.where(DataModel.class).equalTo("id",id).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm();
            }
        });
    }
    private  void updateCourse(DataModel model,String un,String udu,String utr,String ude){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.setSname(un);
                model.setSdur(udu);
                model.setStrk(utr);
                model.setSdesc(ude);
                realm.copyToRealmOrUpdate(model);
            }
        });


    }
}