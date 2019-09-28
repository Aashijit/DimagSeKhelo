package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import team.exp.dimagsekhelo.R;

public class MyProfileScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        //Initialize the variables
        firebaseAuth = FirebaseAuth.getInstance();
        //Initialize the variables

    }


    public void goBack(View view){
        this.finish();
    }

    public void logOut(View view) {
        firebaseAuth.signOut();
        Intent intent = new Intent(MyProfileScreen.this, SplashScreen.class);
        finish();
        startActivity(intent);
    }
}