package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import team.exp.dimagsekhelo.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }



    //UI Connector methods start from here
    public void splashScreenLetsPlay(View view) {

    }

    public void splashScreenLogIn(View view) {
    Intent intent=new Intent(SplashScreen.this, SignInScreen.class);
    startActivity(intent);
    }

    public void splashScreenSignUp(View view) {
        Intent intent = new Intent(SplashScreen.this, SignUpScreen.class);
        startActivity(intent);
    }


}
