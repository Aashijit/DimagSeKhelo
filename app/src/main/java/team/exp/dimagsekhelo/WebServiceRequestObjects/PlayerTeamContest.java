package team.exp.dimagsekhelo.WebServiceRequestObjects;

public class PlayerTeamContest {
    private String _PlayerId;

    public String get_PlayerId() {
        return _PlayerId;
    }

    public void set_PlayerId(String _PlayerId) {
        this._PlayerId = _PlayerId;
    }

    @Override
    public String toString() {
        return "PlayerTeamContest{" +
                "_PlayerId='" + _PlayerId + '\'' +
                '}';
    }
}
