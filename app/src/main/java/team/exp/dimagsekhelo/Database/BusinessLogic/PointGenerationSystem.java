package team.exp.dimagsekhelo.Database.BusinessLogic;

import android.support.annotation.NonNull;

import team.exp.dimagsekhelo.Utils.Codes;
import team.exp.dimagsekhelo.WebServiceResponseObjects.CurrentMatchPoints;
import team.exp.dimagsekhelo.WebServiceResponseObjects.WicketsTaken;

public class PointGenerationSystem implements Codes {


    /**
     *
     * @param currentMatchPoints
     * @return
     */
    public Double getPointsForT20(@NonNull CurrentMatchPoints currentMatchPoints){
        //Step 1 : A part of the starting XI
        Double points = 4.0;

        //Step 2 : Every run scored
        if(currentMatchPoints.get_RunsScored() != null)
        {
            points = points + 2.00 * Double.parseDouble(currentMatchPoints.get_RunsScored());
        }

        //Step 3 : Every wicket taken
//        for(WicketsTaken wicketsTaken : currentMatchPoints.getWicketsTaken()){
//
//            points = points + 30.00;
//
//            if(wicketsTaken.get_Type().equalsIgnoreCase(WICKET_TYPE_CAUGHT))
//                points += 10.00;
//
//            if(wicketsTaken.get_Type().equalsIgnoreCase(WICKET_TYPE_CB))
//                points += 35.00;
//
//            if(wicketsTaken.get_Type().equalsIgnoreCase(WICKET_TYPE_STUMPING))
//                points += 15.00;
//        }

            if(currentMatchPoints.get_PlayerType().equalsIgnoreCase(BATSMAN) || currentMatchPoints.get_PlayerType().equalsIgnoreCase(ALL_ROUNDER))
            {
                if(currentMatchPoints.get_RunsScored() != null && currentMatchPoints.get_BattingStatus() != null)
                {
                    if(Double.parseDouble(currentMatchPoints.get_RunsScored()) == 0.00 && currentMatchPoints.get_BattingStatus().equalsIgnoreCase(BATTING_STATUS_OUT))
                    {
                        points -= 3.00;
                    }
                }
            }

            //Bonus

        if(currentMatchPoints.get_BoundariesHit() != null)
        {
            if(Double.parseDouble(currentMatchPoints.get_BoundariesHit()) > 0)
                points = points + 2.00 * Double.parseDouble(currentMatchPoints.get_BoundariesHit());
        }

        if(currentMatchPoints.get_OverBoundariesHit() != null)
        {
            if(Double.parseDouble(currentMatchPoints.get_OverBoundariesHit()) > 0)
                points = points + 3.00 * Double.parseDouble(currentMatchPoints.get_OverBoundariesHit());
        }

        if(currentMatchPoints.get_RunsScored() != null){

            if(Double.parseDouble(currentMatchPoints.get_RunsScored()) >= 50)
                points += 10.00;
        }

        if(currentMatchPoints.get_RunsScored() != null){

            if(Double.parseDouble(currentMatchPoints.get_RunsScored()) >= 100)
                points += 20.00;
        }


        if(currentMatchPoints.get_MaidenOvers() != null){

            if(Double.parseDouble(currentMatchPoints.get_MaidenOvers()) > 0)
                points += 10.00 * Double.parseDouble(currentMatchPoints.get_MaidenOvers());
        }
//
//        if(currentMatchPoints.getWicketsTaken() != null){
//
//            if(currentMatchPoints.getWicketsTaken().size() == 4){
//                points += 10.00;
//            }
//
//            if(currentMatchPoints.getWicketsTaken().size() == 5){
//                points += 20.00;
//            }
//
//            if(currentMatchPoints.getWicketsTaken().size() == 2){
//                points += 1.00;
//            }
//
//            if(currentMatchPoints.getWicketsTaken().size() == 3){
//                points += 1.00;
//            }
//
//            if(currentMatchPoints.get_RunsScored() != null)
//                if(currentMatchPoints.getWicketsTaken().size() == 3 && Double.parseDouble(currentMatchPoints.get_RunsScored()) == 30.00){
//                points += 20.00;
//            }
//        }

        if(currentMatchPoints.get_BowledOvers() != null){
            if(Double.parseDouble(currentMatchPoints.get_BowledOvers()) > 0.00){
                points += Double.parseDouble(currentMatchPoints.get_BowledOvers());
            }
        }

        //Economy Rate

        double presentEconomyRate = 0.00;

        if(currentMatchPoints.get_BowledOvers() != null && currentMatchPoints.get_RunsGiven() != null)
        {
            if(Double.parseDouble(currentMatchPoints.get_BowledOvers()) > 0.00){
                presentEconomyRate = Double.parseDouble(currentMatchPoints.get_RunsGiven()) / Double.parseDouble(currentMatchPoints.get_BowledOvers());
            }
        }


        if(presentEconomyRate >= 5.00 && presentEconomyRate <= 6.00)
            points += 2.00;

        if(presentEconomyRate >= 4.00 && presentEconomyRate <= 4.99)
            points += 5.00;

        if(presentEconomyRate < 4.00)
            points += 5.00;

        if(presentEconomyRate >= 9.00 && presentEconomyRate <= 10.00)
            points -= 3.00;

        if(presentEconomyRate >= 10.01 && presentEconomyRate <= 11.00)
            points -= 5.00;

        if(presentEconomyRate > 11)
            points -= 7.00;

        if(presentEconomyRate >= 3.5 && presentEconomyRate <= 4.5)
            points += 1.00;

        if(presentEconomyRate >= 2.5 && presentEconomyRate <= 3.49)
            points += 2.00;

        if(presentEconomyRate < 2.5)
            points += 4.00;


        if(presentEconomyRate >= 7.00 && presentEconomyRate <= 8.00)
            points -= 1.00;

        if(presentEconomyRate >= 8.01 && presentEconomyRate <= 9.00)
            points -= 3.00;

        if(presentEconomyRate > 9)
            points -= 4.00;

        if(presentEconomyRate >= 6.00 && presentEconomyRate <= 6.99)
            points += 2.00;

        if(presentEconomyRate >= 7.00 && presentEconomyRate <= 8.00)
            points += 1.00;

        if(presentEconomyRate < 6.00)
            points += 4.00;


        if(presentEconomyRate >= 11.00  && presentEconomyRate <= 12.00)
            points -= 1.00;

        if(presentEconomyRate >= 12.01  && presentEconomyRate <= 13.00)
            points -= 4.00;

        if(presentEconomyRate > 9)
            points -= 5.00;


        if(currentMatchPoints.get_OversFaced() != null){
            if(Double.parseDouble(currentMatchPoints.get_OversFaced()) > 0.00)
                points += 6.00 * Double.parseDouble(currentMatchPoints.get_OversFaced());
        }

        //Strike Rate

        double presentStrikeRate = 0.00;

        if(currentMatchPoints.get_OversFaced() != null && currentMatchPoints.get_RunsScored() != null)
        {
            if(Double.parseDouble(currentMatchPoints.get_OversFaced()) > 0.00){
                Double balls = Double.parseDouble(currentMatchPoints.get_OversFaced()) * 6.00;

                presentStrikeRate =  ( Double.parseDouble(currentMatchPoints.get_RunsScored()) / balls ) * 100.00;
            }
        }

        if(presentStrikeRate > 60.00 && presentStrikeRate < 70.00)
            points -= 3.00;

        if(presentStrikeRate > 50.00 && presentStrikeRate < 50.99)
            points -= 6.00;

        if(presentStrikeRate < 50.00)
            points -= 7.00;

        if(presentStrikeRate > 50.00 && presentStrikeRate < 60.00)
            points -= 1.00;

        if(presentStrikeRate > 40.00 && presentStrikeRate < 49.99)
            points -= 2.00;

        if(presentStrikeRate < 40.00)
            points -= 1.00;

        return points;
    }


    /**
     *
     * @param currentMatchPoints
     * @return
     */
    public Double getPointsForODI(CurrentMatchPoints currentMatchPoints){

        //Step 1 : A part of the starting XI
        Double points = 4.0;

        //Step 2 : Every run scored
        if(currentMatchPoints.get_RunsScored() != null)
        {
            points = points + 2.00 * Double.parseDouble(currentMatchPoints.get_RunsScored());
        }

        //Step 3 : Every wicket taken
//        for(WicketsTaken wicketsTaken : currentMatchPoints.getWicketsTaken()){
//
//            points = points + 30.00;
//
//            if(wicketsTaken.get_Type().equalsIgnoreCase(WICKET_TYPE_CAUGHT))
//                points += 10.00;
//
//            if(wicketsTaken.get_Type().equalsIgnoreCase(WICKET_TYPE_CB))
//                points += 35.00;
//
//            if(wicketsTaken.get_Type().equalsIgnoreCase(WICKET_TYPE_STUMPING))
//                points += 15.00;
//        }

        if(currentMatchPoints.get_PlayerType().equalsIgnoreCase(BATSMAN) || currentMatchPoints.get_PlayerType().equalsIgnoreCase(ALL_ROUNDER))
        {
            if(currentMatchPoints.get_RunsScored() != null && currentMatchPoints.get_BattingStatus() != null)
            {
                if(Double.parseDouble(currentMatchPoints.get_RunsScored()) == 0.00 && currentMatchPoints.get_BattingStatus().equalsIgnoreCase(BATTING_STATUS_OUT))
                {
                    points -= 5.00;
                }
            }
        }

        //Bonus

        if(currentMatchPoints.get_BoundariesHit() != null)
        {
            if(Double.parseDouble(currentMatchPoints.get_BoundariesHit()) > 0)
                points = points + 2.00 * Double.parseDouble(currentMatchPoints.get_BoundariesHit());
        }

        if(currentMatchPoints.get_OverBoundariesHit() != null)
        {
            if(Double.parseDouble(currentMatchPoints.get_OverBoundariesHit()) > 0)
                points = points + 3.00 * Double.parseDouble(currentMatchPoints.get_OverBoundariesHit());
        }

        if(currentMatchPoints.get_RunsScored() != null){

            if(Double.parseDouble(currentMatchPoints.get_RunsScored()) >= 50)
                points += 5.00;
        }

        if(currentMatchPoints.get_RunsScored() != null){

            if(Double.parseDouble(currentMatchPoints.get_RunsScored()) >= 100)
                points += 10.00;
        }


        if(currentMatchPoints.get_MaidenOvers() != null){

            if(Double.parseDouble(currentMatchPoints.get_MaidenOvers()) > 0)
                points += 6.00 * Double.parseDouble(currentMatchPoints.get_MaidenOvers());
        }

//        if(currentMatchPoints.getWicketsTaken() != null){
//
//            if(currentMatchPoints.getWicketsTaken().size() == 4){
//                points += 5.00;
//            }
//
//            if(currentMatchPoints.getWicketsTaken().size() == 5){
//                points += 10.00;
//            }
//
//            if(currentMatchPoints.getWicketsTaken().size() == 2){
//                points += 1.00;
//            }
//
//            if(currentMatchPoints.getWicketsTaken().size() == 3){
//                points += 1.00;
//            }
//
//            if(currentMatchPoints.get_RunsScored() != null)
//                if(currentMatchPoints.getWicketsTaken().size() == 4 && Double.parseDouble(currentMatchPoints.get_RunsScored()) == 50.00){
//                    points += 25.00;
//                }
//        }

        //FIXME: Change here minimum number of balls to be incorporated
//        if(currentMatchPoints.get_BowledOvers() != null){
//            if(Double.parseDouble(currentMatchPoints.get_BowledOvers()) > 0.00){
//                points += Double.parseDouble(currentMatchPoints.get_BowledOvers());
//            }
//        }

        //Economy Rate

        double presentEconomyRate = 0.00;

        if(currentMatchPoints.get_BowledOvers() != null && currentMatchPoints.get_RunsGiven() != null)
        {
            if(Double.parseDouble(currentMatchPoints.get_BowledOvers()) > 0.00){
                presentEconomyRate = Double.parseDouble(currentMatchPoints.get_RunsGiven()) / Double.parseDouble(currentMatchPoints.get_BowledOvers());
            }
        }


        if(presentEconomyRate >= 5.00 && presentEconomyRate <= 6.00)
            points += 3.00;

        if(presentEconomyRate >= 4.00 && presentEconomyRate <= 4.99)
            points += 6.00;

        if(presentEconomyRate < 4.00)
            points += 6.00;

        if(presentEconomyRate >= 9.00 && presentEconomyRate <= 10.00)
            points -= 3.00;

        if(presentEconomyRate >= 10.01 && presentEconomyRate <= 11.00)
            points -= 5.00;

        if(presentEconomyRate > 11)
            points -= 8.00;

        if(presentEconomyRate >= 3.5 && presentEconomyRate <= 4.5)
            points += 3.00;

        if(presentEconomyRate >= 2.5 && presentEconomyRate <= 3.49)
            points += 5.00;

        if(presentEconomyRate < 2.5)
            points += 4.00;


        if(presentEconomyRate >= 7.00 && presentEconomyRate <= 8.00)
            points -= 3.00;

        if(presentEconomyRate >= 8.01 && presentEconomyRate <= 9.00)
            points -= 6.00;

        if(presentEconomyRate > 9)
            points -= 8.00;

        if(presentEconomyRate >= 6.00 && presentEconomyRate <= 6.99)
            points += 2.00;

        if(presentEconomyRate >= 7.00 && presentEconomyRate <= 8.00)
            points += 1.00;

        if(presentEconomyRate < 6.00)
            points += 4.00;


        if(presentEconomyRate >= 11.00  && presentEconomyRate <= 12.00)
            points -= 1.00;

        if(presentEconomyRate >= 12.01  && presentEconomyRate <= 13.00)
            points -= 4.00;

        if(presentEconomyRate > 9)
            points -= 5.00;


        //FIXME: Change here minimum number of balls to be incorporated
//        if(currentMatchPoints.get_OversFaced() != null){
//            if(Double.parseDouble(currentMatchPoints.get_OversFaced()) > 0.00)
//                points += 6.00 * Double.parseDouble(currentMatchPoints.get_OversFaced());
//        }

        //Strike Rate

        double presentStrikeRate = 0.00;

        if(currentMatchPoints.get_OversFaced() != null && currentMatchPoints.get_RunsScored() != null)
        {
            if(Double.parseDouble(currentMatchPoints.get_OversFaced()) > 0.00){
                Double balls = Double.parseDouble(currentMatchPoints.get_OversFaced()) * 6.00;

                presentStrikeRate =  ( Double.parseDouble(currentMatchPoints.get_RunsScored()) / balls ) * 100.00;
            }
        }

        if(presentStrikeRate > 60.00 && presentStrikeRate < 70.00)
            points -= 1.00;

        if(presentStrikeRate > 50.00 && presentStrikeRate < 50.99)
            points -= 2.00;

        if(presentStrikeRate < 50.00)
            points -= 3.00;

        if(presentStrikeRate > 50.00 && presentStrikeRate < 60.00)
            points -= 2.00;

        if(presentStrikeRate > 40.00 && presentStrikeRate < 49.99)
            points -= 3.00;

        if(presentStrikeRate < 40.00)
            points -= 5.00;

        return points;


    }


    /**
     *
     * @param currentMatchPoints
     * @return
     */
    public Double getPointsForTest(CurrentMatchPoints currentMatchPoints){

        return null;
    }


    /**
     *
     * @param currentMatchPoints
     * @return
     */
    public Double getPointsForT10(CurrentMatchPoints currentMatchPoints){
        return null;
    }
}