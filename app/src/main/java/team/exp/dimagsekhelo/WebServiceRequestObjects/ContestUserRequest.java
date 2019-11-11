package team.exp.dimagsekhelo.WebServiceRequestObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class ContestUserRequest implements Parcelable {

    private String _TeamId;
    private String _UserPhoneNumber;
    private String _ContestJoinTimeStamp;
    private String _ContestId;
    private String _Rank;
    private String _Points;

    public ContestUserRequest(){

    }

    protected ContestUserRequest(Parcel in) {
        _TeamId = in.readString();
        _UserPhoneNumber = in.readString();
        _ContestId = in.readString();
        _ContestJoinTimeStamp = in.readString();
        _Rank = in.readString();
        _Points = in.readString();
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

    public String get_ContestJoinTimeStamp() {
        return _ContestJoinTimeStamp;
    }

    public void set_ContestJoinTimeStamp(String _ContestJoinTimeStamp) {
        this._ContestJoinTimeStamp = _ContestJoinTimeStamp;
    }

    public String get_Rank() {
        return _Rank;
    }

    public void set_Rank(String _Rank) {
        this._Rank = _Rank;
    }

    public String get_Points() {
        return _Points;
    }

    public void set_Points(String _Points) {
        this._Points = _Points;
    }

    @Override
    public String toString() {
        return "ContestUserRequest{" +
                "_TeamId='" + _TeamId + '\'' +
                ", _UserPhoneNumber='" + _UserPhoneNumber + '\'' +
                ", _ContestJoinTimeStamp='" + _ContestJoinTimeStamp + '\'' +
                ", _ContestId='" + _ContestId + '\'' +
                ", _Rank='" + _Rank + '\'' +
                ", _Points='" + _Points + '\'' +
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
        parcel.writeString(_ContestJoinTimeStamp);
        parcel.writeString(_Rank);
        parcel.writeString(_Points);
    }
}