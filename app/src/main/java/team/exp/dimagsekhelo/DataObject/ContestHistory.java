package team.exp.dimagsekhelo.DataObject;

public class ContestHistory {

    private String matchCaption;
    private String matchTiming;
    private String contestType;
    private String contestRank;
    private String contestScore;

    private String matchId;
    private String contestId;

    public String getMatchCaption() {
        return matchCaption;
    }

    public void setMatchCaption(String matchCaption) {
        this.matchCaption = matchCaption;
    }

    public String getMatchTiming() {
        return matchTiming;
    }

    public void setMatchTiming(String matchTiming) {
        this.matchTiming = matchTiming;
    }

    public String getContestType() {
        return contestType;
    }

    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    public String getContestRank() {
        return contestRank;
    }

    public void setContestRank(String contestRank) {
        this.contestRank = contestRank;
    }

    public String getContestScore() {
        return contestScore;
    }

    public void setContestScore(String contestScore) {
        this.contestScore = contestScore;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    @Override
    public String toString() {
        return "ContestHistory{" +
                "matchCaption='" + matchCaption + '\'' +
                ", matchTiming='" + matchTiming + '\'' +
                ", contestType='" + contestType + '\'' +
                ", contestRank='" + contestRank + '\'' +
                ", contestScore='" + contestScore + '\'' +
                ", matchId='" + matchId + '\'' +
                ", contestId='" + contestId + '\'' +
                '}';
    }
}