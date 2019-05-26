package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import model.Project;
import model.Skill;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class SkillMapper extends Mapper<Skill, Integer> {
    private static final String COLUMNS = "skillName";

    public SkillMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "skill" + " " + "(" +
                "skillName VARCHAR(100), " +
                "PRIMARY KEY (skillName) " +
                ")");
        st.close();
        con.close();
    }

    @Override
    protected Skill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        Skill skill = new Skill(
            rs.getString("skillName")
        );
        return skill;
    }

    public void insertOne(Skill skill) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = "INSERT OR IGNORE INTO skill (" +
                "skillName" +
                ") VALUES (" +
                "" +
                "?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, skill.getName());
        st.executeUpdate();
        st.close();
        con.close();
    }

    public Skill findByName(String skillName) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM skill WHERE skillName = ? ";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, skillName);
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM skill WHERE skillName = \"" + skillName + "\"");
        Skill skill = convertResultSetToDomainModel(rs);
        statement.close();
        con.close();
        return skill;
    }

    public ArrayList<Skill> findAll() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM skill";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM skill");
        ArrayList<Skill> skills = new ArrayList<>();
        while(rs.next()){
            Skill skill = convertResultSetToDomainModel(rs);
            skills.add(skill);
        }
        statement.close();
        con.close();
        return skills;
    }
}
