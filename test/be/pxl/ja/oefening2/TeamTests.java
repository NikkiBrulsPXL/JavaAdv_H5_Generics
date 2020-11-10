package be.pxl.ja.oefening2;

import be.pxl.ja.streamingservice.model.CreditCardNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.invoke.VarHandle;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTests {
    private Team<BaseballPlayer> t1;
    private Team<VolleyballPlayer> t2;
    private Team<SoccerPlayer> t3;

    @BeforeEach
    private void init(){
        t1 = new Team<>("t1");
        t2 = new Team<>("t2");
        t3 = new Team<>("t3");
    }

    @Test
    public void addPlayerShouldAddPlayersOfSameTypeToMembersArrayList(){
        t1.addPlayer(new BaseballPlayer("player1"));
        assertEquals(1,t1.numberOfPlayers());
        t1.addPlayer(new BaseballPlayer("player2"));
        assertEquals(2,t1.numberOfPlayers());
        t1.addPlayer(new BaseballPlayer("player3"));
        assertEquals(3, t1.numberOfPlayers());

        t2.addPlayer(new VolleyballPlayer("player1"));
        assertEquals(1,t2.numberOfPlayers());
        t2.addPlayer(new VolleyballPlayer("player2"));
        assertEquals(2, t2.numberOfPlayers());
        t2.addPlayer(new VolleyballPlayer("player3"));
        assertEquals(3,t2.numberOfPlayers());

        t3.addPlayer(new SoccerPlayer("player1"));
        assertEquals(1, t3.numberOfPlayers());
        t3.addPlayer(new SoccerPlayer("player2"));
        assertEquals(2, t3.numberOfPlayers());
        t3.addPlayer(new SoccerPlayer("player3"));
        assertEquals(3, t3.numberOfPlayers());
    }

    @Test
    public void matchResultShouldUpdateLostWonOrTiedForBothTeams(){
        Team<BaseballPlayer> homeTeam = new Team<>("home");
        Team<BaseballPlayer> opponent = new Team<>("opponent");

        homeTeam.addPlayer(new BaseballPlayer("homePlayer1"));
        homeTeam.addPlayer(new BaseballPlayer("homePlayer2"));
        homeTeam.addPlayer(new BaseballPlayer("homePlayer3"));

        opponent.addPlayer(new BaseballPlayer("opponentPlayer1"));
        opponent.addPlayer(new BaseballPlayer("opponentPlayer2"));
        opponent.addPlayer(new BaseballPlayer("opponentPlayer3"));

        int oldLostHomeTeam = homeTeam.getLost();
        int oldWonHomeTeam = homeTeam.getWon();
        int oldTiedHomeTeam = homeTeam.getTied();

        int oldLostOpponent = opponent.getLost();
        int oldWonOpponent = opponent.getWon();
        int oldTiedOpponent = opponent.getTied();

        homeTeam.matchResult(opponent, 5, 5);

        assertEquals(oldTiedHomeTeam + 1, homeTeam.getTied());
        assertEquals(oldTiedOpponent + 1, homeTeam.getTied());

        homeTeam.matchResult(opponent, 6, 2);

        assertEquals(oldWonHomeTeam + 1, homeTeam.getWon());
        assertEquals(oldLostOpponent + 1, opponent.getLost());
        assertEquals(oldLostHomeTeam, homeTeam.getLost());
        assertEquals(oldWonOpponent, opponent.getWon());

        homeTeam.matchResult(opponent, 2, 8);

        assertEquals(oldLostHomeTeam + 1, homeTeam.getLost());
        assertEquals(oldWonOpponent + 1, opponent.getWon());
        assertEquals(oldLostOpponent + 1, opponent.getLost());
        assertEquals(oldWonHomeTeam + 1, homeTeam.getWon());
    }

    @Test
    public void rankingShouldReturn3PointsForMatchWonAnd1ForNoMatchesTied(){
        ArrayList<Team<SoccerPlayer>> teams = new ArrayList<>();
        Team<SoccerPlayer> team1 = new Team<>("t1");
        Team<SoccerPlayer> team2 = new Team<>("t1");
        Team<SoccerPlayer> team3 = new Team<>("t1");
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);
        for (Team<SoccerPlayer> team : teams) {
            for(int i = 0; i < 11; i++) {
                team.addPlayer(new SoccerPlayer("player"));
            }
        }

        team1.matchResult(team2, 5, 7);
        team2.matchResult(team1, 2, 9);
        team1.matchResult(team3, 9, 8);
        team3.matchResult(team2, 2, 2);

        int t1ExpectedRanking = team1.getWon() * 3;
        t1ExpectedRanking += (team1.getTied()==0) ? 1 : 0;
        int t2ExpectedRanking = team2.getWon() * 3;
        t2ExpectedRanking += (team2.getTied()==0) ? 1 : 0;
        int t3ExpectedRanking = team3.getWon() * 3;
        t3ExpectedRanking += (team3.getTied()==0) ? 1 : 0;
        assertEquals(t1ExpectedRanking, team1.ranking());
        assertEquals(t2ExpectedRanking, team2.ranking());
        assertEquals(t3ExpectedRanking, team3.ranking());
    }
}
