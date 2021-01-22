package com.example.medicaredoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class DashboardActivity extends AppCompatActivity {

    CardView Iuserprofile,Ihistory,ITreatment,IDocuments;
   // TextView ii;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        Iuserprofile=(CardView) findViewById(R.id.c1);
        Ihistory=(CardView) findViewById(R.id.c2);
        ITreatment=(CardView) findViewById(R.id.c3);
        IDocuments=(CardView)findViewById(R.id.c4);
     //   Inotification=(ImageButton)findViewById(R.id.c4);
     //   ii=(TextView)findViewById(R.id.emailid);



          Intent intent = getIntent();
          Bundle qc= intent.getExtras();
        UserProfile.getcode = (String)qc.get("code");
        UserTreatment.contact= (String)qc.get("code");
        Prevtreatment.code= (String)qc.get("code");
        PatientDocuments.contact= (String)qc.get("code");

        //qr.setText(getcode);

        Iuserprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DashboardActivity.this,UserProfile.class);
                startActivity(i);
            }
        });
        Ihistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DashboardActivity.this,Prevtreatment.class);
                startActivity(i);
            }
        });
        ITreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DashboardActivity.this,UserTreatment.class);
                startActivity(i);
            }
        });
        IDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DashboardActivity.this,PatientDocuments.class);
                startActivity(i);
            }
        });

    }
}
