package team.exp.dimagsekhelo.Utils;

public interface Codes {

    public static String COUNTRY_ID = "+91";

    //Keys for shared preferences
    public static String USER_INFORMATION_PREFERENCE="UserAccountInfo";
    public static String USER_ID = "UserId";
    public static String EMAIL_ID = "EmailId";
    public static String MOBILE_NUMBER = "MobileNumber";


    public static String MATCH_ID="MatchId";
    public static String MATCH_NAME="MatchName";


    //Error Codes
    public static String RC_SUCCESS="0";
    public static String RC_USER_PRESENT="1";
    public static String RC_USER_NOT_INSERTED="2";


    public static String RC_FAILURE="999";



    //Error Messages
    public static String RM_SUCCESS="Success";
    public static String RM_USER_PRESENT="User account already exists !!!";
    public static String RM_USER_NOT_INSERTED="User account could not be created !!!";



    public static String RM_FAILURE="Something has gone wrong !!!";
}
