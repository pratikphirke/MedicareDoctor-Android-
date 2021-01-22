package com.example.medicaredoctor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;


public class UserTreatment extends AppCompatActivity {
    EditText sym, disease1;
    Button save;
    TextView medicine1;
     static int totalEditTexts = 0;
    LinearLayout containerLayout;
    ImageButton ib;
    public static int count=0;
    public static String contact="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_treatment);
        sym = (EditText) findViewById(R.id.symptom);
        disease1 = (EditText) findViewById(R.id.disease);
        medicine1 = (TextView) findViewById(R.id.medicine);
        save=(Button)findViewById(R.id.btnsave);
        ib=(ImageButton)findViewById(R.id.ivadd);
       // ibsub=(ImageButton)findViewById(R.id.ivsub);

        final TextView tvcnt=(TextView)findViewById(R.id.tvcnt);

        // add medicine


        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count++;
                //tvcnt.setText(String.valueOf(count));

                LinearLayout ll = (LinearLayout)findViewById(R.id.linear1);
                LinearLayout lh=new LinearLayout(UserTreatment.this);
                lh.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams linearLayoutSubParentParams =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT, 100f);
                EditText et = new EditText(UserTreatment.this);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                et.setLayoutParams(p);
                et.setHint("Enter medicine name");
                et.setId(count);

                EditText et2 = new EditText(UserTreatment.this);
                et2.setLayoutParams(p);
                et2.setHint("Enter medicine QTY");
                et2.setId(count+50);

                ll.addView(lh);
                lh.addView(et);
                lh.addView(et2);


            }
        });

       /* ibsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count--;

                if (count <= 0) {
                    count = 0;
                    tvcnt.setText("0");


                } else {
                    tvcnt.setText(String.valueOf(count));
                }
            }
        });


*/


        Firebase.setAndroidContext(this);
        final Firebase fb = new Firebase(Config.url + "/Treatment/"+contact);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Symptoms = sym.getText().toString().trim();
                String Disease = disease1.getText().toString().trim();
                String Medicine = medicine1.getText().toString().trim();

                String[]med=new String[count];
                String[]qty=new String[count];

                for(int id=1;id<=count;id++) {


                    EditText et1 = findViewById(id);
                    EditText et2 = findViewById(id+50);
                    med[id-1]=et1.getText().toString();
                    qty[id-1]=et2.getText().toString();

                    //   Toast.makeText(Treatment.this, ":" + et.getText().toString(), Toast.LENGTH_LONG).show();
                }
                Treatmentclass tc = new Treatmentclass();

                tc.setDisease(Disease);
                tc.setSymptom(Symptoms);

                tc.setMedicine(med);
                tc.setQty(qty);

                fb.push().setValue(tc);
                Toast.makeText(UserTreatment.this.getApplicationContext(),"Take Care", Toast.LENGTH_SHORT).show();

            }
        });
    }
}