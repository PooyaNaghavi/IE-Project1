package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import model.Project;
import model.ProjectSkill;
import model.Skill;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class ProjectSkillMapper extends Mapper<ProjectSkill, Integer> {
    private static final String COLUMNS = "skillName, projectId, point";

    public ProjectSkillMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "projectSkill" + " " + "(" +
                "skillName TEXT," +
                "projectId TEXT," +
                "point INTEGER," +
                "PRIMARY KEY (skillName, projectId)," +
                "FOREIGN KEY (projectId) REFERENCES project(id)," +
                "FOREIGN KEY (skillName) REFERENCES skill(skillName)" +
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
        ProjectSkill projectSkill = new ProjectSkill(
                rs.getString("skillName"),
                rs.getInt("point")
        );
        return projectSkill;
    }

    public void insertOne(ProjectSkill skill, Project project) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = "INSERT OR IGNORE INTO projectSkill (" +
                "skillName," +
                "projectId," +
                "point" +
                ") VALUES (" +
                "" +
                "?," +
                "?," +
                "?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, skill.getName());
        st.setString(2, project.getId());
        st.setInt(3, skill.getPoint());
        st.executeUpdate();
        st.close();
        con.close();
    }
    public ArrayList<ProjectSkill> getProjectSkills(String id) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM projectSkill WHERE projectId = \"" + id + "\"");
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
