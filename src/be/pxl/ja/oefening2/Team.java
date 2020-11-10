package be.pxl.ja.oefening2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

public class Team<T extends Player> {
    private String name;
    private int played;
    private int won;
    private int lost;
    private int tied;
    private List<T> members;

    public Team(String name){
        this.name = name;
        members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getPlayed() {
        return played;
    }

    public int getWon() {
        return won;
    }

    public int getLost() {
        return lost;
    }

    public int getTied() {
        return tied;
    }

    public List<T> getMembers() {
        return members;
    }

    public void addPlayer(T player){
        if(members.size() > 0){
            if(player.getClass() == members.get(0).getClass()){
                members.add(player);
            }
            else {
                throw new IllegalArgumentException("Cannot add " + player.getClass() + "to ArrayList of type " + members.get(0).getClass());
            }
        }
        else {
            members.add(player);
        }
    }

    public int numberOfPlayers(){
        return members.size();
    }

    public void matchResult(Team<T> opponent, int ourScore, int theirScore){
        if(opponent.members.get(0).getClass() == this.members.get(0).getClass()) {
            if (ourScore == theirScore) {
                this.tied++;
                opponent.tied++;
            }
            else if(ourScore > theirScore){
                this.won++;
                opponent.lost++;
            }
            else{
                this.lost++;
                opponent.won++;
            }
        }
        else {
            throw new IllegalArgumentException("Team of type " + opponent.members.get(0).getClass() + " cannot play against team of type " + this.members.get(0).getClass());
        }
    }

    public int ranking(){
        if(this.tied == 0){
            return 3 * this.won + 1;
        }
        return 3 * this.won;
    }

    @Override
    public String toString(){
        return "Team: " + getName() + " ranking: " + ranking();
    }
}
