package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import model.Skill;
import model.User;

import java.sql.*;

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
}
