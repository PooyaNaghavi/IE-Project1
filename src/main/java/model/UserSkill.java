package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSkill extends Skill {

    private User user;
    private Skill skill;
    private ArrayList<Endorsement> endorses;

    public UserSkill(User user, Skill skill, ArrayList<Endorsement> endorses){
        this.user = user;
        this.skill = skill;
        this.endorses = endorses;
    }

    public void addEndorseUser(User user) {
        endorses.add(user);
    } // TODO: change this to somewhere in database or Mapper.

    public int getPoint() {
        return endorses.size();
    }

    public Skill getSkill() {
        return skill;
    }

    public ArrayList<Endorsement> getEndorses() {
        return endorses;
    }

    public void setEndorses(ArrayList<Endorsement> endorses) {
        this.endorses = endorses;
    }
}
