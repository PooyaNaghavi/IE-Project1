package model;

import java.util.Comparator;

public class SkillComparator implements Comparator<Skill> {

    public int compare(Skill skill1, Skill skill2) {
        if (skill1.getName() == skill2.getName()) {
            return 0;
        } else {
            return -1;
        }
    }
}
