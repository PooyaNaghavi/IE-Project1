package model;

public class Endorsement {
    private User endorser, endorsed;
    private Skill skill;

    public Endorsement(){ }

    public Endorsement(User endorser, User endorsed, Skill skill){
        this.endorser = endorser;
        this.endorsed = endorsed;
        this.skill = skill;
    }

    public User getEndorser() {
        return endorser;
    }

    public void setEndorser(User endorser) {
        this.endorser = endorser;
    }

    public User getEndorsed() {
        return endorsed;
    }

    public void setEndorsed(User endorsed) {
        this.endorsed = endorsed;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
