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
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity implements View.OnClickListener {


    private FirebaseAuth firebaseauth;

    private static final String TAG = "PhoneAuth";

    private EditText editTextPhone;
    private EditText editTextOTP;
    private Button buttonSend;
    private Button buttonVerify;
    private Button buttonResend;
    private TextView textView10;
    private ProgressDialog progressDialog;
    public String phoneNumber;




    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog =new ProgressDialog(this);
        editTextPhone  = (EditText) findViewById(R.id.editTextPhone);
        editTextOTP = (EditText) findViewById(R.id.editTextOTP);
        buttonSend = (Button) findViewById(R.id.buttonSend);
        buttonVerify = (Button) findViewById(R.id.buttonVerify);
        buttonResend = (Button) findViewById(R.id.buttonResend);
        textView10 =(TextView)findViewById(R.id.textView10);



        firebaseauth = FirebaseAuth.getInstance();

        if(firebaseauth.getCurrentUser()!=null)
        {  finish();
            startActivity(new Intent(getApplicationContext(),Servicz.class));
        }

        buttonSend.setOnClickListener(this);
        buttonVerify.setOnClickListener(this);
        buttonResend.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view==buttonSend)
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

    }


    public void sendCode(View view) {

        Log.i("Hi","4");

        phoneNumber = editTextPhone.getText().toString().trim();


        if(phoneNumber.length()<10)
        {
            Toast.makeText(this, "Length should be atleast 10 characters", Toast.LENGTH_SHORT).show();
            return;
        }



        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber,        // Phone number to verify
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
                        editTextPhone.setVisibility(View.INVISIBLE);
                        textView10.setVisibility(View.INVISIBLE);
                        buttonSend.setVisibility(View.INVISIBLE);
                        buttonResend.setVisibility(View.VISIBLE);
                        buttonVerify.setVisibility(View.VISIBLE);
                        editTextOTP.setVisibility(View.VISIBLE);


                        Log.i("Hi","6");
                    }


                };

        Log.i("Hi","1");
    }




    public void verifyCode(View view) {

        String code = editTextOTP.getText().toString();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
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




                            Toast.makeText(Login.this, "User Logged in successfully!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Servicz.class));
                            // FirebaseUser user = task.getResult().getUser();

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Login.this, "Failed to Login!!", Toast.LENGTH_SHORT).show();
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public void resendCode(View view) {


        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }

}
