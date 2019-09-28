package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.UpcomingMatchesListAdapter;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.WebServiceResponseObjects.UpcomingMatchesResponse;

public class HomeScreen extends AppCompatActivity {


    //Member Variables
    private ListView listView;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReferenceUpcomingMatches = database.getReference("UpcomingMatches");

    private UpcomingMatchesListAdapter upcomingMatchesListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        listView =  (ListView) findViewById(R.id.listViewUpcomingMatches);
        progressBar = (ProgressBar) findViewById(R.id.progressBarUpcomingMatches);




        //Initialize the variables
        firebaseAuth = FirebaseAuth.getInstance();
        //Initialize the variables



        //Fetch the upcoming matches here
        progressBar.setVisibility(View.VISIBLE);
        Log.d(this.getClass().getName(),"Progress Bar : "+progressBar.getProgress());
        fetchUpcomingMatches();
    }

    public void goToProfilePage(View view) {
        Intent intent = new Intent(HomeScreen.this, MyProfileScreen.class);
        startActivity(intent);
    }


    private void fetchUpcomingMatches(){
        //Step 1 : Fetch the matches from the DB in a list
        Log.d(this.getClass().getName(),"Entered Here ");
        final List<UpcomingMatchesResponse> upcomingMatchesResponseList = new ArrayList<>();
        databaseReferenceUpcomingMatches.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dsp : dataSnapshot.getChildren()){

                    UpcomingMatchesResponse upcomingMatchesResponse = dsp.getValue(UpcomingMatchesResponse.class);
                    Log.d(this.getClass().getName(),"Entered Here : "+upcomingMatchesResponse);
                    upcomingMatchesResponseList.add(upcomingMatchesResponse);
                }
                //Step 2 : Update the List View
                upcomingMatchesListAdapter = new UpcomingMatchesListAdapter(HomeScreen.this,upcomingMatchesResponseList);
                listView.setAdapter(upcomingMatchesListAdapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"Could not fetch the upcoming matches !!! ", Toast.LENGTH_LONG).show();
            }
        });



    }

}
