package team.exp.dimagsekhelo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.ContestHistoryAdapter;
import team.exp.dimagsekhelo.CustomUIElements.UpcomingMatchesListAdapter;
import team.exp.dimagsekhelo.DataObject.ContestHistory;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.WebServiceRequestObjects.ContestUserRequest;
import team.exp.dimagsekhelo.WebServiceResponseObjects.ContestMasterResponse;
import team.exp.dimagsekhelo.WebServiceResponseObjects.UpcomingMatchesResponse;

import static android.Manifest.permission_group.CAMERA;

/**
 *
 */
public class MyProfileScreen extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private static Integer TAKE_IMAGE_REQUEST = 111;
    private static Integer CAMERA_PERMISSION_CODE = 112;
    private Uri imageUri;


    private ImageButton imageButtonProfilePic;
    private TextView textViewUserName;
    private ProgressBar progressBar;
    private ListView listView;
    private TextView textViewNoHistory;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceUpcomingMatches = database.getReference("UpcomingMatches");
    DatabaseReference databaseReferenceContestUser = database.getReference("ContestUser");
    DatabaseReference databaseReferenceContestMaster = database.getReference("ContestMaster");
    DatabaseReference databaseReferenceMatchStatus = database.getReference("MatchStatus");


    List<UpcomingMatchesResponse> upcomingMatchesResponseList = new ArrayList<>();
    List<ContestUserRequest> contestUserRequests = new ArrayList<>();
    List<ContestMasterResponse> contestMasterResponses = new ArrayList<>();

    ContestHistoryAdapter contestHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        //Initialize the variables
        firebaseAuth = FirebaseAuth.getInstance();
        //Initialize the variables


        //UI elements
        imageButtonProfilePic = (ImageButton) findViewById(R.id.profilePic);
        textViewUserName= (TextView) findViewById(R.id.myProfileUserName);
        progressBar = (ProgressBar) findViewById(R.id.myProfileScreenProgressBar);
        textViewNoHistory = (TextView) findViewById(R.id.myProfileScreenText);
        listView = (ListView) findViewById(R.id.listContestHistory);
        //UI elements



        //Initialize the names
        textViewUserName.setText(firebaseAuth.getCurrentUser().getPhoneNumber());

        //Fetch the contests ~ number of matches can be calculated accordingly
        //Fetch Contest Master
        //Fetch Contest User
        //Fetch Upcoming Matches
        progressBar.setVisibility(View.VISIBLE);
        fetchContestUpcomingMatches();


    }

    private void fetchContestUpcomingMatches() {


        databaseReferenceUpcomingMatches.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    UpcomingMatchesResponse upcomingMatchesResponse = dataSnapshot1.getValue(UpcomingMatchesResponse.class);
                    upcomingMatchesResponseList.add(upcomingMatchesResponse);
                }
                fetchContMaster();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Failed operation Upcoming Matches !!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchContMaster() {

        databaseReferenceContestMaster.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                 ContestMasterResponse contestMasterResponse = dataSnapshot1.getValue(ContestMasterResponse.class);
                 contestMasterResponses.add(contestMasterResponse);
                }

                fetchContestUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Failed operation Contest Master !!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchContestUser() {
        databaseReferenceContestUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ContestUserRequest contestUserRequest = dataSnapshot1.getValue(ContestUserRequest.class);
                    contestUserRequests.add(contestUserRequest);
                }
                //Populate the list
                populateList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Failed operation Contest User !!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void populateList() {

        List<ContestHistory> contestHistories = new ArrayList<>();

        for(ContestUserRequest contestUserRequest : contestUserRequests){

            if(contestUserRequest.get_UserPhoneNumber().equalsIgnoreCase(firebaseAuth.getCurrentUser().getPhoneNumber())){
                //Use this contest Id to get the Contest Type from the Contest Master
                ContestMasterResponse contestMasterResponse =  getContestMasterUsingContestId(contestUserRequest.get_ContestId());

                if(contestMasterResponse == null)
                {
                    Toast.makeText(getApplicationContext(),"Oops !! Mistake",Toast.LENGTH_SHORT).show();
                    return;
                }

                //fetch the match information

                UpcomingMatchesResponse upcomingMatchesResponse = getMatchInfoByMatchId(contestMasterResponse.get_MatchId());


                if(upcomingMatchesResponse == null)
                {
                    Toast.makeText(getApplicationContext(),"Oops !! Mistake",Toast.LENGTH_SHORT).show();
                    return;
                }


                //Populate the contest History record
                ContestHistory contestHistory = new ContestHistory();
                contestHistory.setContestId(contestMasterResponse.get_ContestId());
                contestHistory.setContestRank(contestUserRequest.get_Rank());
                contestHistory.setContestScore(contestUserRequest.get_Points());
                contestHistory.setMatchCaption(upcomingMatchesResponse.get_Caption());
                contestHistory.setContestType(contestMasterResponse.get_ContestType());
                contestHistory.setMatchTiming(upcomingMatchesResponse.get_MatchTime());
                contestHistory.setMatchId(upcomingMatchesResponse.get_Id());

                contestHistories.add(contestHistory);

            }

        }

        if(contestHistories.isEmpty()){
            textViewNoHistory.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            return;
        }

        contestHistoryAdapter= new ContestHistoryAdapter(MyProfileScreen.this,contestHistories,databaseReferenceMatchStatus);
        listView.setAdapter(contestHistoryAdapter);
        progressBar.setVisibility(View.GONE);
    }


    private ContestMasterResponse getContestMasterUsingContestId(String contestId){
        for(ContestMasterResponse contestMasterResponse1 : contestMasterResponses)
            if(contestMasterResponse1.get_ContestId().equalsIgnoreCase(contestId))
                return contestMasterResponse1;
            return null;
    }


    private UpcomingMatchesResponse getMatchInfoByMatchId(String matchId){
        for(UpcomingMatchesResponse upcomingMatchesResponse : upcomingMatchesResponseList)
            if(upcomingMatchesResponse.get_Id().equalsIgnoreCase(matchId))
                return upcomingMatchesResponse;
        return null;
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

    public void shareApp(View view) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Dimag Se Khelo App");
        share.putExtra(Intent.EXTRA_TEXT, firebaseAuth.getCurrentUser().getPhoneNumber()+" is a user of Dimag Se Khelo App. He has referred you to download the application. Please do so from http://www.dimagsekhelo.com.");
        startActivity(Intent.createChooser(share, "Share Dimag Se Khelo!"));
    }
}