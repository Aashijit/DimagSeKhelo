package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import team.exp.dimagsekhelo.DataObject.SaveProfileInformationResponse;
import team.exp.dimagsekhelo.DataObject.User;
import team.exp.dimagsekhelo.Database.BusinessLogic.RegistrationService;
import team.exp.dimagsekhelo.Database.DataLogic.LocalStorage;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.Utils.DataValidation;


public class SignUpScreen extends AppCompatActivity implements Codes {

    //Member variables
    private EditText editTextMobileNo,editTextEmail,editTextPassword, editTextConfirmPassword, editTextInviteCode,editTextOtp;
    private ProgressBar progressBar;
    private Button buttonVerifyOtp, registerButton;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth firebaseAuth;

    private LocalStorage localStorage;

    private RegistrationService registrationService;

    private static final int TIMEOUT_RECEIVE_OTP = 60;
    //Member variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        //Wire up all fields
        editTextMobileNo = (EditText) findViewById(R.id.signUpScreenMobileNo);
        editTextEmail = (EditText) findViewById(R.id.signUpScreenEmail);
        editTextPassword = (EditText) findViewById(R.id.signUpScreenPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.signUpScreenConfirmPassword);
        editTextInviteCode = (EditText) findViewById(R.id.signUpScreenInviteCode);
        editTextOtp = (EditText)findViewById(R.id.signUpScreenOtp);
        buttonVerifyOtp = (Button) findViewById(R.id.signUpScreenVerifyOtp);
        progressBar = (ProgressBar)findViewById(R.id.signUpScreenProgressBar);
        registerButton = (Button) findViewById(R.id.signUpScreenRegisterButton);
        //Wire up all fields

        //Initialize all the objects
        firebaseAuth = FirebaseAuth.getInstance();
        registrationService = new RegistrationService();
        localStorage = new LocalStorage(SignUpScreen.this);
        //Initialize all the objects


        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser == null){
            Log.d(this.getClass().getName(),"No Current User");
            return;
        }

    }


    public void signUpBackButton(View view) {
        this.finish();
    }

    public void registerUser(View view) {

        //Step 0 : Disable the button
        view.setEnabled(Boolean.FALSE);
        progressBar.setVisibility(View.VISIBLE);

        //Step 1 : Validate the fields
        if(!DataValidation.isValidMobileNumber(editTextMobileNo.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Invalid mobile number",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            view.setEnabled(Boolean.TRUE);
            return;
        }

        if(!DataValidation.isPasswordAndConfirmPasswordSame(editTextPassword.getText().toString(),editTextConfirmPassword.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Password and confirm password does not match",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            view.setEnabled(Boolean.TRUE);
            return;
        }
        if(!DataValidation.isValidEmailId(editTextEmail.getText().toString()))
        {
            Toast.makeText(getApplicationContext(),"Invalid email id",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            view.setEnabled(Boolean.TRUE);
            return;
        }


        //Store the mobile number here
        localStorage.storeUserInformation(MOBILE_NUMBER,editTextMobileNo.getText().toString());


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
                editTextOtp.setText(credential.getSmsCode());

                Toast.makeText(getApplicationContext(),"Verification done",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                registerButton.setVisibility(View.INVISIBLE);


                //Login with the credential
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //Successful login done
                            //Store the user id, hashed password, email id in the realtime database
                            User user = new User();
                            user.populateDataObject(editTextEmail.getText().toString(),editTextPassword.getText().toString(),editTextMobileNo.getText().toString());

                           //Store the information
                           new SaveUserProfile().execute(user);
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
                editTextOtp.setVisibility(View.VISIBLE);
                buttonVerifyOtp.setVisibility(View.VISIBLE);
                registerButton.setVisibility(View.INVISIBLE);



                buttonVerifyOtp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //Validate the otp
                        if(!DataValidation.isValidOtp(editTextOtp.getText().toString()))
                        {
                            Toast.makeText(getApplicationContext(),"Please enter a valid OTP",Toast.LENGTH_LONG).show();
                            return;
                        }

                        //Get the authenticating credential
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, editTextOtp.getText().toString());

                        //Login with the credential
                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    //Successful login done
                                    //Store the user id, hashed password, email id in the realtime database
                                    User user = new User();
                                    user.populateDataObject(editTextEmail.getText().toString(),editTextPassword.getText().toString(),editTextMobileNo.getText().toString());

                                    //Store the information
                                    //Create an Async call to get the response
                                    new SaveUserProfile().execute(user);
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






    private class SaveUserProfile extends AsyncTask<User, Void, SaveProfileInformationResponse> {

        @Override
        protected SaveProfileInformationResponse doInBackground(User... params) {

            return registrationService.saveUserProfileInformation(params[0]);
        }

        @Override
        protected void onPostExecute(SaveProfileInformationResponse response) {

            progressBar.setVisibility(View.INVISIBLE);

            buttonVerifyOtp.setVisibility(View.INVISIBLE);
            editTextOtp.setVisibility(View.INVISIBLE);

            if(!response.getReturnCode().equalsIgnoreCase(RC_SUCCESS)){
                Toast.makeText(getApplicationContext(),response.getReturnMessage(),Toast.LENGTH_LONG).show();

                //Take the user out of the activity
                finish();

                return;
            }



            //Save the user id in the shared preferences
            localStorage.storeUserInformation(Codes.USER_ID,response.getUserId());

            //Go to the Home Page
            Log.d(this.getClass().getName(),"Redirecting to HomeScreen");
            Intent intent = new Intent(SignUpScreen.this, HomeScreen.class);
            finish();
            startActivity(intent);

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }
}