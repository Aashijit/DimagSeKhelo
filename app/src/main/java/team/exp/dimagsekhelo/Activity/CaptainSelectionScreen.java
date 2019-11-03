package team.exp.dimagsekhelo.Activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.CustomUIElements.CaptainSelectionAdapter;
import team.exp.dimagsekhelo.R;
import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.WebServiceResponseObjects.PlayerResponse;

import static team.exp.dimagsekhelo.Utils.Codes.TEAM;

public class CaptainSelectionScreen extends AppCompatActivity implements Codes {


    private List<PlayerResponse> playerResponseList;

    private ListView captainSelectionList;
    private CaptainSelectionAdapter captainSelectionAdapter;

    private String contestId;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captain_selection_screen);

        captainSelectionList = (ListView) findViewById(R.id.captainSelectionList);

        playerResponseList = getIntent().getParcelableArrayListExtra(TEAM);
        contestId = getIntent().getStringExtra(CONTEST_ID);
        amount = getIntent().getStringExtra("amount");

        Log.d(this.getClass().getName(),amount +" Captain Selection Received");


        if(playerResponseList == null)
            return;

        if(playerResponseList.size() == 0)
            return;


        //Paint the screen and implement the selection
        List<PlayerResponse> filteredPlayerResponses = new ArrayList<>();
        for(PlayerResponse playerResponse : playerResponseList){
            if(playerResponse.getPlayerSelected() != null)
                if(playerResponse.getPlayerSelected())
                    filteredPlayerResponses.add(playerResponse);
        }


        //Paint the screen
        captainSelectionAdapter = new CaptainSelectionAdapter(this,filteredPlayerResponses);
        captainSelectionList.setAdapter(captainSelectionAdapter);

    }

    public void captainSelectionGoBack(View view) {
        finish();
    }

    public void teamPreview(View view) {
        if(playerResponseList == null)
        {
            Toast.makeText(getApplicationContext(),"No players selected !!!",Toast.LENGTH_LONG).show();
            return;
        }

        if(playerResponseList.size() == 0)
        {
            Toast.makeText(getApplicationContext(),"No players selected !!!",Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(CaptainSelectionScreen.this, TeamPreviewScreen.class);
        intent.putParcelableArrayListExtra(TEAM, (ArrayList<? extends Parcelable>) playerResponseList);
        intent.putExtra(CONTEST_ID,contestId);
        intent.putExtra("amount",amount);
        startActivity(intent);
    }
}
