package team.exp.dimagsekhelo.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import team.exp.dimagsekhelo.R;

public class RulesScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_screen);
    }

    public void goBack(View view) {
        finish();
    }
}
