package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import team.exp.dimagsekhelo.R;

public class SignInScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
    }
    public void signInBackButton(View view) {
        this.finish();
    }


    public void signInForgotPassword(View view) {
        Intent intent=new Intent(SignInScreen.this, ForgotPasswordScreen.class);
        startActivity(intent);
    }
}
