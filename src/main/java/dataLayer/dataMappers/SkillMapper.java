package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import model.Project;
import model.Skill;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class SkillMapper extends Mapper<Skill, Integer> {
    private static final String COLUMNS = "name";

    public SkillMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "skill" + " " + "(" +
                "name TEXT PRIMARY KEY " +
                ")");
        st.close();
        con.close();
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS +
                " FROM skill" +
                " WHERE name = ?";
    }

    @Override
    protected Skill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        Skill skill = new Skill(
            rs.getString("name")
        );
        return skill;
    }

    public void insertOne(Skill skill) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = "INSERT OR IGNORE INTO skill (" +
                "name" +
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
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM skill WHERE name = " + skillName);
        Skill skill = convertResultSetToDomainModel(rs);
        st.close();
        con.close();
        return skill;
    }

    public ArrayList<Skill> findAll() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM skill");
        ArrayList<Skill> skills = new ArrayList<>();
        while(rs.next()){
            Skill skill = convertResultSetToDomainModel(rs);
            skills.add(skill);
        }
        st.close();
        con.close();
        return skills;
    }
}
