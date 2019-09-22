package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import team.exp.dimagsekhelo.R;

public class SplashScreen extends AppCompatActivity {

    //Member Variables
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    //Member Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();

        //Check if the user is logged into the Application
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null){
            //Take the user directly to the home screen
            Log.d(this.getClass().getName(),"Redirecting to Home Screen");
            Intent intent = new Intent(SplashScreen.this,HomeScreen.class);
            finish();
            startActivity(intent);
        }




    }



    //UI Connector methods start from here
    public void splashScreenLetsPlay(View view) {

    }

    public void splashScreenLogIn(View view) {
    Intent intent=new Intent(SplashScreen.this, SignInScreen.class);
    finish();
    startActivity(intent);
    }

    public void splashScreenSignUp(View view) {
        Intent intent = new Intent(SplashScreen.this, SignUpScreen.class);
        finish();
        startActivity(intent);
    }


}
