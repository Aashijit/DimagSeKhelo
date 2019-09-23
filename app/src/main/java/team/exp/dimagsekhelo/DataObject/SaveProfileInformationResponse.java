package team.exp.dimagsekhelo.DataObject;

public class SaveProfileInformationResponse extends  ResponseBasic {

    private  String userId;

    public SaveProfileInformationResponse(){
        super();
    }

    public SaveProfileInformationResponse(String returnCode, String returnMessage){
        super(returnCode,returnMessage);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SaveProfileInformationResponse{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
