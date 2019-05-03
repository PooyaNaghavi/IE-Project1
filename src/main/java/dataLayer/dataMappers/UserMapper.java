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
                "id TEXT PRIMARY KEY, " +
                "firstName TEXT, " +
                "lastName TEXT, " +
                "userName TEXT UNIQUE , " +
                "password TEXT, " +
                "jobTitle TEXT, " +
                "profilePictureURL TEXT, " +
                "bio TEXT " +
                ")");
        st.close();
        con.close();
    }

    public void setMapper(UserSkillMapper userSkillMapper){
        this.userSkillMapper = userSkillMapper;
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS +
                " FROM user, userSkill" +
                " WHERE id = ?";
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
        // TODO: get skills from UserSkills.
//        ArrayList<UserSkill> userSkills = getUserSkills(userId);
//        user.setSkills([]);
        return user;
    }

    public User findById(String id) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM user WHERE id = " + id);
        User user = convertResultSetToDomainModel(rs);
        st.close();
        con.close();
        return user;
    }

    public void insertOne(User user) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = "INSERT OR IGNORE INTO user (" +
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
        st.setString(1, user.getId());
        st.setString(2, user.getFirstName());
        st.setString(3, user.getLastName());
        st.setString(4, user.getUserName());
        st.setString(5, user.getPassword());
        st.setString(6, user.getJobTitle());
        st.setString(7, user.getProfilePictureURL());
        st.setString(8, user.getBio());
        st.executeUpdate();
        st.close();
        System.out.println("added to db?");
        con.close();
    }

    public ArrayList<User> findAll() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM user");
        ArrayList<User> users = new ArrayList<>();
        while(rs.next()){
            User user = convertResultSetToDomainModel(rs);
            users.add(user);
        }
        st.close();
        con.close();
        return users;
    }

    public User findByUsernameAndPassword(User user) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM user WHERE userName = " + user.getUserName() + "AND password = " + user.getPassword());
        User foundUser = convertResultSetToDomainModel(rs);
        st.close();
        con.close();
        return foundUser;
    }
}
