package com.example.apple.indianmistry;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class Signup extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth firebaseauth;

    private static final String TAG = "PhoneAuth";

    private EditText editTextName;
    private EditText editTextMob;
    private EditText editTextMail;
    private EditText editTextOTP;
    private Button buttonSignup;
    private Button buttonVerify;
    private Button buttonResend;
    private TextView Logintv;
    private ImageButton imageButtonG;
    private ImageButton imageButtonfb;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    public String phoneNumber;
    private String name;
    private String email;




    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
          progressDialog =new ProgressDialog(this);
          editTextName = (EditText) findViewById(R.id.editTextName);
          editTextMail = (EditText) findViewById(R.id.editTextMail);
          editTextMob  = (EditText) findViewById(R.id.editTextMob);
          editTextOTP = (EditText) findViewById(R.id.editTextOTP);
          buttonSignup = (Button) findViewById(R.id.buttonSignup);
          buttonVerify = (Button) findViewById(R.id.buttonVerify);
          buttonResend = (Button) findViewById(R.id.buttonResend);
          Logintv = (TextView)findViewById(R.id.Logintv);
          imageButtonG =(ImageButton)findViewById(R.id.imageButtonG);
          imageButtonfb = (ImageButton)findViewById(R.id.imageButtonfb);


         firebaseauth = FirebaseAuth.getInstance();
         databaseReference = FirebaseDatabase.getInstance().getReference();


        if(firebaseauth.getCurrentUser()!=null)
        {  finish();
            startActivity(new Intent(getApplicationContext(),Servicz.class));
        }


         buttonSignup.setOnClickListener(this);
         buttonVerify.setOnClickListener(this);
        buttonResend.setOnClickListener(this);
        Logintv.setOnClickListener(this);



      }



        public void sendCode(View view) {

            Log.i("Hi","4");

            email = editTextMail.getText().toString().trim();
            name = editTextName.getText().toString().trim();
            phoneNumber = editTextMob.getText().toString().trim();

            if(TextUtils.isEmpty(name))
            {
                Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(email))
            {
                Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }


            if(phoneNumber.length()<13)
            {
                Toast.makeText(this, "Length should be atleast 13 characters", Toast.LENGTH_SHORT).show();
                return;
            }



            setUpVerificatonCallbacks();

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    verificationCallbacks);

        Log.i("Hello","1");

        }


    private void setUpVerificatonCallbacks() {

        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential)
                    {
                        Log.i("Hi","9");
                        signInWithPhoneAuthCredential(credential);


                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.d(TAG, "Invalid credential: "
                                    + e.getLocalizedMessage());
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                            Log.d(TAG, "SMS Quota exceeded.");
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {
                        //super.onCodeSent(verificationId,token);

                        phoneVerificationId = verificationId;
                        resendToken = token;
                        editTextMob.setVisibility(View.INVISIBLE);
                        editTextName.setVisibility(View.INVISIBLE);
                        editTextMail.setVisibility(View.INVISIBLE);
                        imageButtonfb.setVisibility(View.INVISIBLE);
                        imageButtonG.setVisibility(View.INVISIBLE);
                        buttonSignup.setVisibility(View.INVISIBLE);
                        buttonResend.setVisibility(View.VISIBLE);
                        buttonVerify.setVisibility(View.VISIBLE);
                        editTextOTP.setVisibility(View.VISIBLE);
                        Logintv.setVisibility(View.INVISIBLE);

                        Log.i("Hi","6");
                    }


                };

        Log.i("Hi","1");
    }




    public void verifyCode(View view) {

            String code = editTextOTP.getText().toString();

            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(phoneVerificationId, code);
              progressDialog.setMessage("Verifying User..");
              progressDialog.show();
            signInWithPhoneAuthCredential(credential);
        }

        private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
            Log.i("Hi","2");
            firebaseauth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            progressDialog.dismiss();
                            if (task.isSuccessful()) {

                                Log.i("Hi","3");

                                UserInfo userInfo =new UserInfo(name,email,phoneNumber);
                                FirebaseUser firebaseUser =firebaseauth.getCurrentUser();
                                databaseReference.child(firebaseUser.getUid()).child("UserInfo").setValue(userInfo); //Saving Info


                                Toast.makeText(Signup.this, "User registered successfully!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Servicz.class));
                               // FirebaseUser user = task.getResult().getUser();

                            } else {
                                if (task.getException() instanceof
                                        FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(Signup.this, "Failed to register!!", Toast.LENGTH_SHORT).show();
                                    // The verification code entered was invalid
                                }
                            }
                        }
                    });
        }

        public void resendCode(View view) {


            setUpVerificatonCallbacks();

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,
                    60,
                    TimeUnit.SECONDS,
                    this,
                    verificationCallbacks,
                    resendToken);
        }

    @Override
    public void onClick(View view) {
                if(view==buttonSignup)
        {
            sendCode(view);
        }

        if(view==buttonVerify)
        {
            verifyCode(view);
        }
        if(view==buttonResend)
        {
            resendCode(view);
        }
        if(view==Logintv)
        {
            startActivity(new Intent(getApplicationContext(),Login.class));
        }

    }

//        public void signOut(View view) {
//            fbAuth.signOut();
//            statusText.setText("Signed Out");
//            signoutButton.setEnabled(false);
//            sendButton.setEnabled(true);
//        }



}

