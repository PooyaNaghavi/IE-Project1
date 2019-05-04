package model;

public class Endorsement {
    private String endorser, endorsed;
    private Skill skill;

    public Endorsement(){ }

    public Endorsement(String endorser, String endorsed, Skill skill){
        this.endorser = endorser;
        this.endorsed = endorsed;
        this.skill = skill;
    }

    public String getEndorser() {
        return endorser;
    }

    public void setEndorser(String endorser) {
        this.endorser = endorser;
    }

    public String getEndorsed() {
        return endorsed;
    }

    public void setEndorsed(String endorsed) {
        this.endorsed = endorsed;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
