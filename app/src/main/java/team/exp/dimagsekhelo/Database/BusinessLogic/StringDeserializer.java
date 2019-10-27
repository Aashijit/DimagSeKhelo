package team.exp.dimagsekhelo.Database.BusinessLogic;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import team.exp.dimagsekhelo.WebServiceResponseObjects.WicketsTaken;

public class StringDeserializer {

    public static List<WicketsTaken> deserialze(String string){

//        {Wicket_002={_Wicket=2, _Type=Caught}, Wicket_001={_Wicket=1, _Type=CB}}

        List<WicketsTaken> wicketsTakenList = new ArrayList<>();


        ArrayList<String> strings = getWordAfter(string);

        for(String s : strings){
            wicketsTakenList.add(new WicketsTaken(s));
        }

        return wicketsTakenList;
    }


    private static   ArrayList<String> getWordAfter(String string){

        int i=0;
        ArrayList<String> strings = new ArrayList<>();
        while (i<string.length()){

            i=string.indexOf("_Type=",i);

            int j=string.indexOf("}",i);

            if(i>=string.length() || j>=string.length() || i<0 || j<0)
                break;
            String str = string.substring(i+6,j);

            Log.d("CHECK",str+" : "+i+" : "+j);

            i++;
            strings.add(str);
        }
        return strings;
    }


}