package team.exp.dimagsekhelo.Activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.Utils.DataValidation;


public class SignUpScreen extends AppCompatActivity implements Codes {

    //Member variables
    private EditText editTextMobileNo,editTextEmail,editTextPassword, editTextConfirmPassword, editTextInviteCode;
    private ProgressBar progressBar;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth firebaseAuth;

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
        progressBar = (ProgressBar)findViewById(R.id.signUpScreenProgressBar);
        //Wire up all fields


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


                Toast.makeText(getApplicationContext(),credential.getProvider(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                //Store the user Id, password, email id

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
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
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
}
