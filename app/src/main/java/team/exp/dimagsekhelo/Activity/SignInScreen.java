package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.inputmethodservice.AbstractInputMethodService;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import team.exp.dimagsekhelo.DataObject.User;
import team.exp.dimagsekhelo.Database.DataLogic.LocalStorage;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.Utils.DataValidation;

public class SignInScreen extends AppCompatActivity implements  Codes {

    //Member Variables
    private EditText editTextMobileNo,editTextPassword,editTextVerifyOtp;
    private Button buttonVerifyOtp, loginButton;
    private ProgressBar progressBar;



    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private LocalStorage localStorage;


    private static final int TIMEOUT_RECEIVE_OTP = 60;
    //Member Variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);

        //Wire up
         editTextMobileNo=(EditText)findViewById(R.id.signInScreenMobileNumber);
         editTextPassword = (EditText)findViewById(R.id.signInScreenPassword);
         editTextVerifyOtp = (EditText)findViewById(R.id.signInScreenOtp);
         buttonVerifyOtp = (Button)findViewById(R.id.signInScreenVerifyOtp);
         progressBar = (ProgressBar)findViewById(R.id.signInScreenProgressBar);
         loginButton = (Button) findViewById(R.id.signInScreenLoginButton);
        //Wire up

        //Initialize member variables
        firebaseAuth = FirebaseAuth.getInstance();
        localStorage = new LocalStorage(SignInScreen.this);
        //Initialize member variables

        //Pre-fill fields
        editTextMobileNo.setText(localStorage.getUserInformation(MOBILE_NUMBER));
        //Pre-fill fields
    }
    public void signInBackButton(View view) {
        this.finish();
    }


    public void signInForgotPassword(View view) {
        Intent intent=new Intent(SignInScreen.this, ForgotPasswordScreen.class);
        startActivity(intent);
    }

    public void logIn(View view) {

        progressBar.setVisibility(View.VISIBLE);

        //Step 1 : Validate the mobile number
        if(!DataValidation.isValidMobileNumber(editTextMobileNo.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Invalid mobile number",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            view.setEnabled(Boolean.TRUE);
            return;
        }



        //Step 2 : Verify the mobile number using the Mobile Otp Verification in Firebase
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.

                //Set the sms code
                editTextVerifyOtp.setText(credential.getSmsCode());

                Toast.makeText(getApplicationContext(),"Verification done",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);

                //Login with the credential
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //Successful login done
                            Toast.makeText(getApplicationContext(),"Welcome to Dimag Se Khelo ",Toast.LENGTH_LONG).show();
                            goToHomePage();
                        }
                        else
                        {
                            //Login not successful
                            Toast.makeText(getApplicationContext(),"Login unsuccessful !!!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.

                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    Toast.makeText(getApplicationContext(),"Invalid request",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                    Toast.makeText(getApplicationContext(),"SMS Quota exceeded",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    return;
                }

                Toast.makeText(getApplicationContext(),"Some unknown error has occurred",Toast.LENGTH_LONG).show();
                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(@NonNull final String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                editTextVerifyOtp.setVisibility(View.VISIBLE);
                buttonVerifyOtp.setVisibility(View.VISIBLE);


                buttonVerifyOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Validate the otp
                        if(!DataValidation.isValidOtp(editTextVerifyOtp.getText().toString()))
                        {
                            Toast.makeText(getApplicationContext(),"Please enter a valid OTP",Toast.LENGTH_LONG).show();
                            return;
                        }

                        //Get the authenticating credential
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, editTextVerifyOtp.getText().toString());

                        //Login with the credential
                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    //Successful login done
                                    Toast.makeText(getApplicationContext(),"Welcome to Dimag Se Khelo ",Toast.LENGTH_LONG).show();
                                    goToHomePage();
                                }
                                else
                                {
                                    //Login not successful
                                    Toast.makeText(getApplicationContext(),"Login unsuccessful !!!",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
            }
        };

        //Call the verify phone number method to verify OTP of the phone Number
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                COUNTRY_ID+editTextMobileNo.getText().toString(),        // Phone number to verify
                TIMEOUT_RECEIVE_OTP,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacksPhoneAuthActivity.java


        //Re-enable the button
        view.setEnabled(Boolean.TRUE);
    }



    private void goToHomePage(){


        progressBar.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(SignInScreen.this, HomeScreen.class);
        finish();
        startActivity(intent);
    }

}