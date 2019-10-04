package team.exp.dimagsekhelo.WebServiceRequestObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class ContestUserRequest implements Parcelable {

    private String _TeamId;
    private String _UserPhoneNumber;
    private String _ContestId;

    public ContestUserRequest(){

    }

    protected ContestUserRequest(Parcel in) {
        _TeamId = in.readString();
        _UserPhoneNumber = in.readString();
        _ContestId = in.readString();
    }

    public static final Creator<ContestUserRequest> CREATOR = new Creator<ContestUserRequest>() {
        @Override
        public ContestUserRequest createFromParcel(Parcel in) {
            return new ContestUserRequest(in);
        }

        @Override
        public ContestUserRequest[] newArray(int size) {
            return new ContestUserRequest[size];
        }
    };

    public String get_TeamId() {
        return _TeamId;
    }

    public void set_TeamId(String _TeamId) {
        this._TeamId = _TeamId;
    }

    public String get_UserPhoneNumber() {
        return _UserPhoneNumber;
    }

    public void set_UserPhoneNumber(String _UserPhoneNumber) {
        this._UserPhoneNumber = _UserPhoneNumber;
    }

    public String get_ContestId() {
        return _ContestId;
    }

    public void set_ContestId(String _ContestId) {
        this._ContestId = _ContestId;
    }

    @Override
    public String toString() {
        return "ContestUserRequest{" +
                "_TeamId='" + _TeamId + '\'' +
                ", _UserPhoneNumber='" + _UserPhoneNumber + '\'' +
                ", _ContestId='" + _ContestId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_TeamId);
        parcel.writeString(_UserPhoneNumber);
        parcel.writeString(_ContestId);
    }
}