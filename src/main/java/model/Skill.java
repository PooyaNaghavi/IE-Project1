package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Comparator;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Skill {

    private String name;

    public int getPoint(){
        return 0;
    }

    public Skill(String name) {
        this.name = name;
    }

    public Skill() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}