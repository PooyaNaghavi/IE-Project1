package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import model.Project;

import java.sql.*;
import java.util.ArrayList;

public class ProjectMapper extends Mapper<Project, Integer> {

    UserMapper userMapper;
    ProjectSkillMapper projectSkillMapper;

    private static final String COLUMNS =
                    "id," +
                    "title," +
                    "description," +
                    "imageUrl," +
                    "budget," +
                    "deadline," +
                    "createdAt," +
                    "winnerId";
    // skills

    public ProjectMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "project" + " " + "(" +
                "id TEXT PRIMARY KEY, " +
                "title TEXT, " +
                "description TEXT, " +
                "imageUrl TEXT, " +
                "budget INTEGER, " +
                "deadline DATE, " + // TODO: date or number? how to save data?
                "createdAt DATE," + // TODO: like above
                "winnerId TEXT, " +
                "FOREIGN KEY (winnerId) REFERENCES user(id)" +
                ")");
        st.close();
        con.close();
    }

    public void setMapper(UserMapper userMapper, ProjectSkillMapper projectSkillMapper){
        this.userMapper = userMapper;
        this.projectSkillMapper = projectSkillMapper;
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS +
                " FROM project" +
                " WHERE id = ?";
    }

    @Override
    protected Project convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        Project project = new Project(
            rs.getString("id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getString("imageUrl"),
            projectSkillMapper.getProjectSkills(rs.getString("id")),
            rs.getInt("budget"),
            rs.getLong("deadline"),
            rs.getLong("createdAt"),
            userMapper.findById(rs.getString("winnerId"))
        );
        return project;
    }

    public Project findById(String id) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM project WHERE id = " + id);
        Project user = convertResultSetToDomainModel(rs);
        st.close();
        con.close();
        return user;
    }

    public void insertOne(Project project) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = "INSERT OR IGNORE INTO project (" +
                "id," +
                "title," +
                "description," +
                "imageUrl," +
                "budget," +
                "deadline," +
                "createdAt," +
                "winnerId" +
                ") VALUES (" +
                "" +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?," +
                "?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, project.getId());
        st.setString(2, project.getTitle());
        st.setString(3, project.getDescription());
        st.setString(4, project.getImageUrl());
        st.setInt(5, project.getBudget());
        st.setLong(6, project.getDeadline()); // TODO: do something about this again
        st.setLong(7, project.getCreatedAt()); // TODO: do something about this again
        st.setString(8, project.getWinner().getId());
        st.executeUpdate();
        st.close();
        con.close();
    }

    public ArrayList<Project> findAll() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM project");
        ArrayList<Project> projects = new ArrayList<>();
        while(rs.next()){
            Project pr = convertResultSetToDomainModel(rs);
            projects.add(pr);
        }
        st.close();
        con.close();
        return projects;
    }

    public ArrayList<Project> getProjectsPage(int limit, int nextPageToken) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM project ORDER BY createdAt DESC LIMIT " + limit + " OFFSET " + nextPageToken);
        ArrayList<Project> projects = new ArrayList<>();
        while(rs.next()){
            Project pr = convertResultSetToDomainModel(rs);
            projects.add(pr);
        }
        st.close();
        con.close();
        return projects;
    }
}
