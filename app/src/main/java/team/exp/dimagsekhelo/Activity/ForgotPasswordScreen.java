package team.exp.dimagsekhelo.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import team.exp.dimagsekhelo.R;

public class ForgotPasswordScreen extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_activity);
    }

    public void fotgotPasswordBackButton(View view) {
        this.finish();
    }


}
