package com.example.medicaredoctor;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessaging;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private ImageButton btRegister;
    private ImageView circle1;
    TextView tvLogin;

    TextView logbtn,forget,email1,pass1;
    Button blog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btRegister  = findViewById(R.id.btRegister);
        btRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent a = new Intent(MainActivity.this,RegisterActivity.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String> (tvLogin,"login");
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                startActivity(a,activityOptions.toBundle());
            }
        });
        // tvLogin     = findViewById(R.id.tvLogin);
        circle1     = findViewById(R.id.circle1);

        // btRegister.setOnClickListener(this);
        email1=(TextView) findViewById(R.id.atvEmailLog);
        pass1=(TextView) findViewById(R.id.atvPasswordLog);
        blog=(Button)findViewById(R.id.btnSignIn);
       /* txtforget=(TextView)findViewById(R.id.textforget);
        txtforget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(UserLogin.this,ForgetActivity.class);
                startActivity(i);
            }
        });*/
        Firebase.setAndroidContext(this);
        final Firebase cloud=new Firebase(Config.url+"/Doctor_Info");

        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = email1.getText().toString();
                if (!isValidEmail(email)) {
                    //Set error message for email field
                    email1.setError("Invalid Email");
                }

                final String pass = pass1.getText().toString();
                if (!isValidPassword(pass)) {
                    //Set error message for password field
                    pass1.setError("Password must be 4 characters..");
                }

                cloud.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        int cnt = (int) dataSnapshot.getChildrenCount();
                        int flg = 0;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            DoctorDetails p = data.getValue(DoctorDetails.class);
                            if (p.getEmail().equals(email1.getText().toString()) && p.getPassword().equals(pass1.getText().toString())) {
                                flg = 1;
                                   UserNotification.contact=p.getContact();
                               FirebaseMessaging.getInstance().subscribeToTopic(p.getContact());

                                break;
                            }

                        }
                        if (flg == 1) {
                            Intent i = new Intent(MainActivity.this, MainPage.class);
                            startActivity(i);
                            Toast.makeText(MainActivity.this, "Successfull", Toast.LENGTH_LONG).show();
                        } else {
                            // Intent i = new Intent(UserLogin.this,ForgetActivity.class);
                            //startActivity(i);

                            Toast.makeText(MainActivity.this, "Unsuccessfull", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });




            }
        });


    }
    public void checkLogin(View arg0) {

        final String email = email1.getText().toString();
        if (!isValidEmail(email)) {
            //Set error message for email field
            email1.setError("Invalid Email");
        }

        final String pass = pass1.getText().toString();
        if (!isValidPassword(pass)) {
            //Set error message for password field
            pass1.setError("Password cannot be empty");
        }

        if(isValidEmail(email) && isValidPassword(pass))
        {
            // Validation Completed
        }

    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 4) {
            return true;
        }
        return false;
    }
   /* @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v==btRegister){
            Intent a = new Intent(MainActivity.this, RegisterActivity.class);
            Pair[] pairs = new Pair[1];
            pairs[0] = new Pair<View,String> (tvLogin,"login");
            ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
            startActivity(a,activityOptions.toBundle());
        }
    }*/
}

