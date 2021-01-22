package com.example.medicaredoctor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;

public class UserProfile extends AppCompatActivity {
    public static String getcode="";
    public static String con="";

    TextView qr;
    TextView ppname;
    TextView ppage;
    TextView ppbg;
   // ImageView imgpatientprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        qr=(TextView)findViewById(R.id.qr);
        ppname=(TextView)findViewById(R.id.pname);
        ppage=(TextView)findViewById(R.id.page);
        ppbg=(TextView)findViewById(R.id.pbg);
       /* imgpatientprofile=(ImageView)findViewById(R.id.imgprofile);

        String pic="https://firebasestorage.googleapis.com/v0/b/medicare-2aee1.appspot.com/o/images%2F"+ppname+"?alt=media&token=8541813d-7493-469b-8076-3036090ced1c";
        Picasso.with(getContext()).load(pic).into(imgpatientprofile);*/

        //imp
        // Intent intent = getIntent();
        // Bundle qc= intent.getExtras();
        //    final String getcode = (String) qc.get("code");
        qr.setText(getcode);
        Firebase.setAndroidContext(this);
        Firebase fb=new Firebase(Config.url+"/Details");

        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int cnt = (int) dataSnapshot.getChildrenCount();
                int flg = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    RegisterDetail p = data.getValue(RegisterDetail.class);

                    if(p.getAadhar().trim().equalsIgnoreCase(getcode.trim()))
                    {
                        String name=p.getName();
                        String age=p.getAge();
                        String bg=p.getBloodg();
                      //  String bg1=p.getAadhar();
                    //    UserTreatment.contact=p.getContact();

                        ppname.setText(name);
                        ppage.setText(age);
                        ppbg.setText(bg);
                       // qr.setText(bg1);
                       break;
                    }
                       /* if(flg == 0)
                        {
                           Intent i=new Intent(PatientDetails.this,Next.class);
                           startActivity(i);
                        }else
                        {
                            Toast.makeText(PatientDetails.this, "hellllooo", Toast.LENGTH_SHORT).show();
                        }*/
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
