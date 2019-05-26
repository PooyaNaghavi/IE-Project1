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
                "skillName VARCHAR(100)," +
                "userId VARCHAR(100)," +
                "PRIMARY KEY (skillName, userId)," +
                "FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE ," +
                "FOREIGN KEY (skillName) REFERENCES skill(skillName) ON DELETE CASCADE " +
                ")");
        st.close();
        con.close();
    }

    public void setMapper(EndorsementMapper endorsementMapper){
        this.endorsementMapper = endorsementMapper;
    }

    @Override
    protected UserSkill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        UserSkill userSkill = new UserSkill(
                rs.getString("skillName"),
                endorsementMapper.getUserEndorses(rs.getString("userId"), rs.getString("skillName"))
        );
        return userSkill;
    }

    public void insertOne(Skill skill, User user) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = "INSERT IGNORE INTO userSkill (" +
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
        String query = "SELECT " + COLUMNS + " FROM userSkill WHERE userId = ? AND skillName = ? ";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, user.getId());
        statement.setString(2, skillName);
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM userSkill WHERE userId = \"" + user.getId() + "\"AND skillName = \"" + skillName + "\"");
        rs.next();
        UserSkill userSkill = convertResultSetToDomainModel(rs);
        statement.close();
        con.close();
        return userSkill;
    }

    public void deleteSkill(Skill skill, User user) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "Delete FROM userSkill WHERE userId = ? AND skillName = ? ";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, user.getId());
        statement.setString(2, skill.getName());
        statement.execute();
//        st.execute("Delete FROM userSkill WHERE userId = \"" + user.getId() + "\"AND skillName = \"" + skill.getName() + "\"");
        statement.close();
        con.close();
    }

    public UserSkill findByName(String skillName) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM userSkill WHERE skillName = ? ";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, skillName);
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM userSkill WHERE skillName = \"" + skillName + "\"");
        rs.next();
        UserSkill skill = convertResultSetToDomainModel(rs);
        statement.close();
        con.close();
        return skill;
    }

    public ArrayList<UserSkill> getUserSkills(String id) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM userSkill WHERE userId = ? ";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM userSkill WHERE userId = \"" + id + "\"");
        ArrayList<UserSkill> userSkills = new ArrayList<>();
        while(rs.next()){
            UserSkill userSkill = convertResultSetToDomainModel(rs);
            userSkills.add(userSkill);
        }
        statement.close();
        con.close();
        return userSkills;
    }
}
