package com.example.medicaredoctor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


public class UserNotification extends AppCompatActivity {
    ListView listView1;
    public static String contact="";

    //   int cnt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_notification);
        listView1=findViewById(R.id.listview);

        Firebase.setAndroidContext(this);
        Firebase fb=new Firebase(Config.url+"/Appointment");

        fb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int cnt = (int) dataSnapshot.getChildrenCount();
                int flg = 0;
                cnt=0;

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    AptClass p = data.getValue(AptClass.class);
                    if (p.getDocphone().equalsIgnoreCase(contact)) {
                        cnt++;
                    }
                    }
                        String[] pname = new String[cnt];
                        String[] paddress = new String[cnt];
                        String[] pcontact = new String[cnt];
                        String[] pdate = new String[cnt];
                        String[] ptime = new String[cnt];


                int cnt1=0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    AptClass p = data.getValue(AptClass.class);
//                        symptom[i] = p.getSymptom().toString();
                    if (p.getDocphone().equalsIgnoreCase(contact)) {

                        pname[cnt1] = p.getUname().toString();
                        paddress[cnt1] = p.getUaddress().toString();
                        pcontact[cnt1] = p.getContact().toString();
                        pdate[cnt1] = p.getDate().toString();
                        ptime[cnt1] = p.getTime().toString();

                        cnt1++;
                    }
                    // }
                }
              //  Toast.makeText(Ptreatment.this,".."+symptom[0],Toast.LENGTH_LONG).show();
                CustomNotification customList = new CustomNotification(UserNotification.this,pname,paddress,pcontact,pdate,ptime);
                listView1.setAdapter(customList);


            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

    }
}
