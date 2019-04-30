package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import model.ProjectSkill;
import model.Skill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProjectSkillMapper extends Mapper<ProjectSkill, Integer> {
    private static final String COLUMNS = "skillName, projectId, point";

    public ProjectSkillMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "projectSkill" + " " + "(" +
                "skillName TEXT " +
                "projectId TEXT" +
                "point INTEGER " +
                "PRIMARY KEY (skillName, projectId)" +
                ")");
        st.close();
        con.close();
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS +
                " FROM projectSkill" +
                " WHERE projectId = ?";
    }

    @Override
    protected ProjectSkill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        ProjectMapper projectMapper = new ProjectMapper();
        ProjectSkill projectSkill = new ProjectSkill(
                rs.getString("skillName"),
                projectMapper.findById(rs.getString("projectId")),
                rs.getInt("point")
        );
        return projectSkill;
    }

    public ArrayList<ProjectSkill> getProjectSkills(String projectId) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM projectSkill WHERE projectId = " + projectId);
        ArrayList<ProjectSkill> projectSkills = new ArrayList<>();
        while(rs.next()){
            ProjectSkill ps = convertResultSetToDomainModel(rs);
            projectSkills.add(ps);
        }
        st.close();
        con.close();
        return projectSkills;
    }
}
