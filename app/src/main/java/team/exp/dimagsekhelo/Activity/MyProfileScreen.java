package team.exp.dimagsekhelo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import team.exp.dimagsekhelo.R;

public class MyProfileScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
    }


    public void goBack(View view){
        this.finish();
    }
}