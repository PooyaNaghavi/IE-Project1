package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import dataLayer.dataMappers.Mapper;
import exceptions.NotFoundException;
import model.User;
import model.UserSkill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapper extends Mapper<User, Integer> {
    UserSkillMapper userSkillMapper;
    private static final String COLUMNS =
            "id," +
            "firstName," +
            "lastName," +
            "userName," +
            "password," +
            "jobTitle," +
            "profilePictureURL," +
            "bio ";
//            userSkill

    public UserMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "user" + " " + "(" +
                "id VARCHAR(100), " +
                "firstName VARCHAR(100), " +
                "lastName VARCHAR(100), " +
                "userName VARCHAR(100) , " +
                "password VARCHAR(500), " +
                "jobTitle VARCHAR(100), " +
                "profilePictureURL VARCHAR(1000), " +
                "bio VARCHAR(10000), " +
                "PRIMARY KEY (id)," +
                "UNIQUE KEY (userName)" +
                ")");
        st.close();
        con.close();
    }

    public void setMapper(UserSkillMapper userSkillMapper){
        this.userSkillMapper = userSkillMapper;
    }

    @Override
    protected User convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        User user = new User(
                rs.getString("id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("userName"),
                rs.getString("password"),
                rs.getString("jobTitle"),
                rs.getString("profilePictureURL"),
                rs.getString("bio"),
                userSkillMapper.getUserSkills(rs.getString("id"))
        );
        return user;
    }

    public void insertOne(User user) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = "INSERT INTO user (" +
                "id," +
                "firstName," +
                "lastName," +
                "userName," +
                "password," +
                "jobTitle," +
                "profilePictureURL," +
                "bio " +
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
        st.setString(1, user.getId() != null ? user.getId() : user.getUserName());
        st.setString(2, user.getFirstName());
        st.setString(3, user.getLastName());
        st.setString(4, user.getUserName());
        st.setString(5, user.getPassword());
        st.setString(6, user.getJobTitle());
        st.setString(7, user.getProfilePictureURL());
        st.setString(8, user.getBio());
        st.executeUpdate();
        st.close();
        con.close();
    }

    public User findById(String id) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM user WHERE id = ? ";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM user WHERE id = \"" + id + "\"");
        User user = convertResultSetToDomainModel(rs);
        statement.close();
        con.close();
        return user;
    }

    public ArrayList<User> findAll() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM user";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM user");
        ArrayList<User> users = new ArrayList<>();
        while(rs.next()){
            User user = convertResultSetToDomainModel(rs);
            users.add(user);
        }
        statement.close();
        con.close();
        return users;
    }

    public User findByUsernameAndPassword(User user) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM user WHERE userName = ? AND password = ? ";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, user.getUserName());
        statement.setString(2, user.getHashedPassword());
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM user WHERE userName = \"" + user.getUserName() + "\" AND password = \"" + user.getHashedPassword() + "\"");
        User foundUser = convertResultSetToDomainModel(rs);
        statement.close();
        con.close();
        return foundUser;
    }

    public  ArrayList<User> getSearchedUsers(String searchContent) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM user WHERE firstName LIKE ? OR lastName LIKE ? ";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, "%" + searchContent + "%");
        statement.setString(2, "%" + searchContent + "%");
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM user WHERE firstName LIKE \"%" + searchContent + "%\" OR lastName LIKE \"%" + searchContent + "%\"");
        ArrayList<User> searchedUsers = new ArrayList<>();
        while(rs.next()){
            User user = convertResultSetToDomainModel(rs);
            searchedUsers.add(user);
        }
        statement.close();
        con.close();
        return searchedUsers;
    }
}
