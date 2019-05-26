package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import model.Project;
import model.User;

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
                    "creationDate," +
                    "winnerId";
    // skills

    public ProjectMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "project" + " " + "(" +
                "id VARCHAR(100), " +
                "title VARCHAR(100), " +
                "description VARCHAR(10000), " +
                "imageUrl VARCHAR(1000), " +
                "budget INT, " +
                "deadline DATE, " +
                "creationDate DATE," +
                "winnerId VARCHAR(100) , " +
                "PRIMARY KEY (id), " +
                "FOREIGN KEY (winnerId) REFERENCES user(id) ON DELETE CASCADE" +
                ")");
        st.close();
        con.close();
    }

    public void setMapper(UserMapper userMapper, ProjectSkillMapper projectSkillMapper){
        this.userMapper = userMapper;
        this.projectSkillMapper = projectSkillMapper;
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
            rs.getLong("creationDate")
            //userMapper.findById(rs.getString("winnerId"))
        );
        if(rs.getString("winnerId") != null){
            project.setWinner(userMapper.findById(rs.getString("winnerId")));
        }
        return project;
    }

    public void insertOne(Project project) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = "INSERT IGNORE INTO project (" +
                "id," +
                "title," +
                "description," +
                "imageUrl," +
                "budget," +
                "deadline," +
                "creationDate," +
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
        st.setLong(6, project.getDeadline());
        st.setLong(7, project.getCreationDate());
        st.setString(8, null);
        st.executeUpdate();
        st.close();
        con.close();
    }

    public Project findById(String id) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM project WHERE id = ?";
        //Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM project WHERE id = \"" + id + "\"");
        Project project = convertResultSetToDomainModel(rs);
        statement.close();
        con.close();
        return project;
    }

    public ArrayList<Project> findAll() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM project";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM project");
        ArrayList<Project> projects = new ArrayList<>();
        while(rs.next()){
            Project pr = convertResultSetToDomainModel(rs);
            projects.add(pr);
        }
        statement.close();
        con.close();
        return projects;
    }
    // TODO : Is this ok ?
    public ArrayList<Project> getProjectsPage(int limit, int nextPageToken) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM project ORDER BY creationDate DESC LIMIT ? OFFSET ?";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setInt(1, limit);
        statement.setInt(2, nextPageToken);
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM project ORDER BY creationDate DESC LIMIT " + limit + " OFFSET " + nextPageToken);
        ArrayList<Project> projects = new ArrayList<>();
        while(rs.next()){
            Project pr = convertResultSetToDomainModel(rs);
            projects.add(pr);
        }
        statement.close();
        con.close();
        return projects;
    }

    public ArrayList<Project> getSearchedProjects(String searchContent) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM project WHERE title LIKE ? OR description LIKE ? ORDER BY creationDate DESC";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, "%" + searchContent + "%");
        statement.setString(2, "%" + searchContent + "%");
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM project WHERE title LIKE \"%" + searchContent + "%\" OR description LIKE \"%" + searchContent + "%\" ORDER BY creationDate DESC");
        ArrayList<Project> searchedProjects = new ArrayList<>();
        while(rs.next()){
            Project project = convertResultSetToDomainModel(rs);
            searchedProjects.add(project);
        }
        statement.close();
        con.close();
        return searchedProjects;
    }

    public void setProjectWinner(User winner, Project project) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "UPDATE project SET winnerId = ? WHERE id = ?";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, winner.getId());
        statement.setString(2, project.getId());
        statement.execute();
//        String sql = "UPDATE project SET winnerId = \"" + winner.getId() + "\" WHERE id = \"" + project.getId() + "\"";
//        ResultSet rs = st.executeQuery(sql);
        statement.close();
        con.close();
    }

    public ArrayList<Project> getUnresolvedProjects() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM project WHERE winnerId IS null AND deadline < ? ";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setLong(1, System.currentTimeMillis());
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM project WHERE winnerId IS null AND deadline < " + System.currentTimeMillis());
        ArrayList<Project> projects = new ArrayList<>();
        while(rs.next()){
            Project pr = convertResultSetToDomainModel(rs);
            projects.add(pr);
        }
        statement.close();
        con.close();
        return projects;
    }
}
