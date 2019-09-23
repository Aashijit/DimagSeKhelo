package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import team.exp.dimagsekhelo.R;

public class HomeScreen extends AppCompatActivity {


    //Member Variables
    private FirebaseAuth firebaseAuth;
    //Member Variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Initialize the variables
            firebaseAuth = FirebaseAuth.getInstance();
        //Initialize the variables



    }


    public void logOut(View view) {
        firebaseAuth.signOut();

        Intent intent = new Intent(HomeScreen.this, SplashScreen.class);
        finish();
        startActivity(intent);
    }
}
