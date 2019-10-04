package team.exp.dimagsekhelo.WebServiceResponseObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class PlayerResponse implements Parcelable {
    private String _ContestId;
    private String _PlayerCredit;
    private String _PlayerId;
    private String _PlayerName;
    private String _PlayerPictureUrl;
    private String _PlayerType;

    private Boolean isPlayerSelected;
    private Boolean isPlayerCaptain;
    private Boolean isPlayerViceCaptain;

    public PlayerResponse(){

    }

    protected PlayerResponse(Parcel in) {
        _ContestId = in.readString();
        _PlayerCredit = in.readString();
        _PlayerId = in.readString();
        _PlayerName = in.readString();
        _PlayerPictureUrl = in.readString();
        _PlayerType = in.readString();
        byte tmpIsPlayerSelected = in.readByte();
        isPlayerSelected = tmpIsPlayerSelected == 0 ? null : tmpIsPlayerSelected == 1;
        byte tmpIsPlayerCaptain = in.readByte();
        isPlayerCaptain = tmpIsPlayerCaptain == 0 ? null : tmpIsPlayerCaptain == 1;
        byte tmpIsPlayerViceCaptain = in.readByte();
        isPlayerViceCaptain = tmpIsPlayerViceCaptain == 0 ? null : tmpIsPlayerViceCaptain == 1;
    }

    public static final Creator<PlayerResponse> CREATOR = new Creator<PlayerResponse>() {
        @Override
        public PlayerResponse createFromParcel(Parcel in) {
            return new PlayerResponse(in);
        }

        @Override
        public PlayerResponse[] newArray(int size) {
            return new PlayerResponse[size];
        }
    };

    public String get_ContestId() {
        return _ContestId;
    }

    public void set_ContestId(String _ContestId) {
        this._ContestId = _ContestId;
    }

    public String get_PlayerCredit() {
        return _PlayerCredit;
    }

    public void set_PlayerCredit(String _PlayerCredit) {
        this._PlayerCredit = _PlayerCredit;
    }

    public String get_PlayerId() {
        return _PlayerId;
    }

    public void set_PlayerId(String _PlayerId) {
        this._PlayerId = _PlayerId;
    }

    public String get_PlayerName() {
        return _PlayerName;
    }

    public void set_PlayerName(String _PlayerName) {
        this._PlayerName = _PlayerName;
    }

    public String get_PlayerPictureUrl() {
        return _PlayerPictureUrl;
    }

    public void set_PlayerPictureUrl(String _PlayerPictureUrl) {
        this._PlayerPictureUrl = _PlayerPictureUrl;
    }

    public String get_PlayerType() {
        return _PlayerType;
    }

    public void set_PlayerType(String _PlayerType) {
        this._PlayerType = _PlayerType;
    }

    public Boolean getPlayerSelected() {
        return isPlayerSelected;
    }

    public void setPlayerSelected(Boolean playerSelected) {
        isPlayerSelected = playerSelected;
    }

    public Boolean getPlayerCaptain() {
        return isPlayerCaptain;
    }

    public void setPlayerCaptain(Boolean playerCaptain) {
        isPlayerCaptain = playerCaptain;
    }

    public Boolean getPlayerViceCaptain() {
        return isPlayerViceCaptain;
    }

    public void setPlayerViceCaptain(Boolean playerViceCaptain) {
        isPlayerViceCaptain = playerViceCaptain;
    }

    @Override
    public String toString() {
        return "PlayerResponse{" +
                "_ContestId='" + _ContestId + '\'' +
                ", _PlayerCredit='" + _PlayerCredit + '\'' +
                ", _PlayerId='" + _PlayerId + '\'' +
                ", _PlayerName='" + _PlayerName + '\'' +
                ", _PlayerPictureUrl='" + _PlayerPictureUrl + '\'' +
                ", _PlayerType='" + _PlayerType + '\'' +
                ", isPlayerSelected=" + isPlayerSelected +
                ", isPlayerCaptain=" + isPlayerCaptain +
                ", isPlayerViceCaptain=" + isPlayerViceCaptain +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_ContestId);
        parcel.writeString(_PlayerCredit);
        parcel.writeString(_PlayerId);
        parcel.writeString(_PlayerName);
        parcel.writeString(_PlayerPictureUrl);
        parcel.writeString(_PlayerType);
        parcel.writeByte((byte) (isPlayerSelected == null ? 0 : isPlayerSelected ? 1 : 2));
        parcel.writeByte((byte) (isPlayerCaptain == null ? 0 : isPlayerCaptain ? 1 : 2));
        parcel.writeByte((byte) (isPlayerViceCaptain == null ? 0 : isPlayerViceCaptain ? 1 : 2));
    }
}