package team.exp.dimagsekhelo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

import team.exp.dimagsekhelo.R;

import static android.Manifest.permission_group.CAMERA;

public class MyProfileScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private static Integer TAKE_IMAGE_REQUEST = 111;
    private static Integer CAMERA_PERMISSION_CODE = 112;
    private Uri imageUri;


    private ImageButton imageButtonProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        //Initialize the variables
        firebaseAuth = FirebaseAuth.getInstance();
        //Initialize the variables


        //UI elements
        imageButtonProfilePic = (ImageButton) findViewById(R.id.profilePic);
        //UI elements

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

    public void clickProfilePictureAndUpload(View view) {

        //Step 1 : Check if permission is granted
        Log.v(this.getClass().getName(),checkPermission()+"");
        if(!checkPermission())
            requestPermission();

        //Step 2 : Take a profile Picture
       takeProfilePicture();
    }

    private void takeProfilePicture()
    {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, TAKE_IMAGE_REQUEST);
    }



    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, CAMERA_PERMISSION_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 112:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted)
                    {
                        takeProfilePicture();
                    }
                }
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == TAKE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageButtonProfilePic.setImageBitmap(photo);
        }
    }

    public void addPointBalance(View view) {
        
    }
}