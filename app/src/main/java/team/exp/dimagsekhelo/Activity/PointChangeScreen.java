package team.exp.dimagsekhelo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import team.exp.dimagsekhelo.R;

public class PointChangeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_change_screen);






    }

    public void pointsGoBack(View view) {
        finish();
    }
}
