package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import model.*;

import java.sql.*;
import java.util.ArrayList;

public class UserSkillMapper extends Mapper<UserSkill, Integer> {
    private static final String COLUMNS =
            "userId," +
            "skillName";
    EndorsementMapper endorsementMapper;

    public UserSkillMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "userSkill" + " " + "(" +
                "skillName TEXT," +
                "userId TEXT," +
                "PRIMARY KEY (skillName, userId)," +
                "FOREIGN KEY (userId) REFERENCES user(id)," +
                "FOREIGN KEY (skillName) REFERENCES skill(skillName)" +
                ")");
        st.close();
        con.close();
    }

    public void setMapper(EndorsementMapper endorsementMapper){
        this.endorsementMapper = endorsementMapper;
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS +
                " FROM userSkill" +
                " WHERE userId = ?";
    }

    @Override
    protected UserSkill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        UserSkill userSkill = new UserSkill(
                rs.getString("skillName"),
                endorsementMapper.getUserEndorses(rs.getString("userId"))
        );
        return userSkill;
    }

    public void insertOne(Skill skill, User user) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = "INSERT OR IGNORE INTO userSkill (" +
                "skillName," +
                "userId " +
                ") VALUES (" +
                "" +
                "?," +
                "?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, skill.getName());
        st.setString(2, user.getId());
        st.executeUpdate();
        st.close();
        con.close();
    }

    public UserSkill findUserSkill(String skillName, User user) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM userSkill WHERE userId = \"" + user.getId() + "\"AND skillName = \"" + skillName + "\"");
        UserSkill userSkill = convertResultSetToDomainModel(rs);
        st.close();
        con.close();
        return userSkill;
    }

    public void deleteSkill(Skill skill, User user) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        //TODO : is this ok ?
        ResultSet rs = st.executeQuery("Delete " + COLUMNS + " FROM userSkill WHERE userId = \"" + user.getId() + "\"AND skillName = \"" + skill.getName() + "\"");
        st.close();
        con.close();
    }

    public UserSkill findByName(String skillName) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM userSkill WHERE skillName = \"" + skillName + "\"");
        UserSkill skill = convertResultSetToDomainModel(rs);
        st.close();
        con.close();
        return skill;
    }

    public ArrayList<UserSkill> getUserSkills(String id) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM userSkill WHERE userId = \"" + id + "\"");
        ArrayList<UserSkill> userSkills = new ArrayList<>();
        while(rs.next()){
            UserSkill userSkill = convertResultSetToDomainModel(rs);
            userSkills.add(userSkill);
        }
        st.close();
        con.close();
        return userSkills;
    }
}
