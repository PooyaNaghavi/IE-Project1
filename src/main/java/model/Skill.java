package model;

import java.util.ArrayList;
import java.util.Comparator;

public class Skill {

    protected String name;
    private int point = 0;

    public Skill(String name, int point) {
        this.name = name;
    }

    public Skill() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {return point;}

    public void setPoint(int point) {
        this.point = point;
    }
}

