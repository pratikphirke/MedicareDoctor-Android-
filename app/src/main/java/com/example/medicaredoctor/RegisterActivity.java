package com.example.medicaredoctor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private android.view.animation.Animation animation;
  private android.widget.EditText NameEditText;
    private EditText AddressEditText;
    private EditText ContactEditText;
    private EditText EmailEditText;
    private EditText PassEditText;
    private EditText CpassEditText;
    private EditText Experience;
    private EditText Age;
    private EditText Fees;

    private RadioGroup grg;
    private RadioButton Rmale, Rfemale;
    private Spinner spec;
    Button reg;

    private ImageView imageView;
    //Button b1;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;
    private Uri downloadUrl;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        animation   = android.view.animation.AnimationUtils.loadAnimation(this,R.anim.uptodown);
   storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //  btnChoose = (Button) findViewById(R.id.btnChoose);
        //  btnUpload = (Button) findViewById(R.id.btnUpload);
       // imageView = (android.widget.ImageView) findViewById(R.id.btnChoose);
        //   b1=(Button)findViewById(R.id.btnup);
       /* imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
*/
      /*  b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });*/

        final Spinner spec = (Spinner) findViewById(R.id.spinner1);

        final String[] items
                = new String[]{"Gynecology", "HairFall", "Orthopedies", "Nuerology", "Cardiology", "Oncology", "Diabetes"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spec.setAdapter(adapter);

        NameEditText = (EditText) findViewById(R.id.fullname);
        AddressEditText = (EditText) findViewById(R.id.address);
        ContactEditText = (EditText) findViewById(R.id.contact);
        EmailEditText = (EditText) findViewById(R.id.email);
        PassEditText = (EditText) findViewById(R.id.password);
        CpassEditText = (EditText) findViewById(R.id.cpassword);

        Experience = (EditText) findViewById(R.id.vrate);
        Age = (EditText) findViewById(R.id.vrate);
        Fees = (EditText) findViewById(R.id.atvFeesReg);

        grg = (RadioGroup) findViewById(R.id.gender_radio_group);
        grg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.male_radio_btn) {
                    Toast.makeText(getApplicationContext(), "choice: Male",
                            Toast.LENGTH_SHORT).show();

                } else if (i == R.id.female_radio_btn) {

                    Toast.makeText(getApplicationContext(), "choice: Female",

                            Toast.LENGTH_SHORT).show();

                }
            }
        });
        Rmale = (RadioButton) findViewById(R.id.male_radio_btn);
        Rfemale = (RadioButton) findViewById(R.id.female_radio_btn);

        Firebase.setAndroidContext(this);
        final Firebase cloud = new Firebase(Config.url + "/Doctor_Info");


        reg = (Button) findViewById(R.id.button);
        reg.setOnClickListener(new View.OnClickListener() {
            Boolean validationError = false;

            @Override
            public void onClick(View view) {

                StringBuilder validationErrorMessage = new StringBuilder("Please ");

                String nm[]=NameEditText.getText().toString().split(" ");

                validationError = false;

                if (isEmpty(NameEditText)) {
                    validationError = true;
                    validationErrorMessage.append("Enter First Name");

                }else if (isEmpty(NameEditText)) {
                    validationError = true;
                    validationErrorMessage.append("Enter Last Name");

                } else if (nm.length<1)
                {
                    validationError=true;
                    validationErrorMessage.append("Enter First and Last Name ");
                }
                else if(isEmpty(AddressEditText))
                {
                    validationError = true;
                    validationErrorMessage.append("Enter Address");

                }else if (isEmpty(ContactEditText)) {
                    validationError = true;
                    validationErrorMessage.append("Enter Mobile No ");
                }else  if (ContactEditText.length()!=10)
                {
                    validationError = true;
                    validationErrorMessage.append("Enter Valid Mobile No");
                }

                else if(isEmpty(EmailEditText))
                {
                    validationError = true;
                    validationErrorMessage.append("Enter Email");

                }else if(EmailEditText.getText().toString().contains("@")==false || EmailEditText.getText().toString().contains(".")==false )
                {
                    validationError=true;
                    validationErrorMessage.append("Enter Valid Email Id");
                }
                else if (isEmpty(PassEditText))
                {
                    validationError = true;
                    validationErrorMessage.append("Enter Password");
                }
                else if (PassEditText.length()<5)
                {
                    validationError=true;
                    validationErrorMessage.append("Password length should be minimum 5");
                }
                else if (isEmpty(CpassEditText))
                {
                    validationError=true;
                    validationErrorMessage.append("Enter Confirm Password");

                }
                else if(!isMatching(PassEditText, CpassEditText))
                {
                    validationError = true;

                    validationErrorMessage.append("Enter Same Passwords!");

                }



                // If there is a validation error, display the error
                if (validationError) {
                    Toast.makeText(RegisterActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    return;
                } // Set up a progress dialog
               /* final ProgressDialog dlg = new ProgressDialog(RegistrationActivity.this);
                dlg.setTitle("Please wait.");
                dlg.setMessage("Signing up.  Please wait.");
                dlg.show();
*/

                // Intent i = new Intent(Registration.this, MainActivity.class);
                //startActivity(i);
                String gender = "Male";
                if (Rfemale.isChecked()) {
                    gender = "Female";
                }

                String name = NameEditText.getText().toString();
                String address = AddressEditText.getText().toString();
                String contact = ContactEditText.getText().toString();
                String lemail = EmailEditText.getText().toString();
                String lpass = PassEditText.getText().toString();
                String lcpass = CpassEditText.getText().toString();
                String age = Age.getText().toString();
                String fees = Fees.getText().toString();

                String specialisation = spec.getSelectedItem().toString();
                String exp = Experience.getText().toString();

                //uploadImage();

                DoctorDetails p = new DoctorDetails();
                p.setName(name);
                p.setAddress(address);
                p.setContact(contact);
                p.setEmail(lemail);
                p.setPassword(lpass);
                p.setcPassword(lcpass);
                p.setGender(gender);
                p.setSpecialisation(specialisation);
                p.setExperience(exp);
                p.setAge(age);
                p.setFees(fees);
                cloud.child(contact).setValue(p);
                Toast.makeText(RegisterActivity.this, "record insert", Toast.LENGTH_SHORT).show();
            }

        });

    }

    public boolean isValidPhone(CharSequence phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
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

    private boolean isValidcPassword(String pass) {
        if (!PassEditText.getText().toString().equals(CpassEditText.getText().toString())) {
            return false;

        }
        return true;

    }

    private boolean isValidPhone(String phone) {
        boolean check = false;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() < 10 || phone.length() > 10) {
                check = false;

            } else {
                check = true;

            }
        } else {
            check = false;
        }
        return check;
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }


    private boolean isMatching(EditText etText1, EditText etText2) {
        if (etText1.getText().toString().equals(etText2.getText().toString())) {
            return true;
        } else {
            return false;
        }

    }

   /* private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }*/

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
*/
   /*
    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + ContactEditText.getText().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            //  downloadUrl=taskSnapshot.getDownloadUrl();
                            // Log.d("onsuccess","" + downloadUrl);
                        }
                    });
           /* bdownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageView imageView = findViewById(R.id.btnChoose1);
                    Picasso.with(MainActivity.this).load(downloadUrl.toString()).into(imageView);
                }
            });*/
       // }
    //}

}


