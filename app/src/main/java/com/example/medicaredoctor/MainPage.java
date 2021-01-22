package com.example.medicaredoctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainPage extends AppCompatActivity {

 CardView imgscan,imgapp;
 Button btninfo;
 EditText etAadhar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        imgscan=(CardView)findViewById(R.id.imgscanQR);
      //  imgapp=(CardView)findViewById(R.id.imgappoinment);
        btninfo=(Button)findViewById(R.id.btninfo);
        etAadhar=(EditText)findViewById(R.id.etaadhar);
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prevtreatment.code=etAadhar.getText().toString();

                UserTreatment.contact=etAadhar.getText().toString();
                PatientDocuments.contact=etAadhar.getText().toString();
                Intent i=new Intent(MainPage.this, DashboardActivity.class);
                i.putExtra("code",etAadhar.getText().toString());
                startActivity(i);
            }
        });
        imgscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainPage.this,QRActivity.class);
                startActivity(i);

            }
        });
      /*  imgapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainPage.this,UserNotification.class);
                startActivity(i);

            }
        });*/
    }
}
