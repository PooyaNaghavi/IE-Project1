package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSkill extends Skill{
    private ArrayList<Endorsement> endorses;

    @Override
    public int getPoint() {
        return endorses.size();
    }

    public UserSkill() { }

    public UserSkill(String name, ArrayList<Endorsement> endorses){
        super(name);
        this.endorses = endorses;
    }

    public ArrayList<Endorsement> getEndorses() {
        return endorses;
    }

    public void setEndorses(ArrayList<Endorsement> endorses) {
        this.endorses = endorses;
    }
}
