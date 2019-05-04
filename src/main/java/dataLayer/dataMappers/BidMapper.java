package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import exceptions.NotFoundException;
import model.Bid;
import model.Endorsement;
import model.Project;
import model.User;

import java.sql.*;
import java.util.ArrayList;


public class BidMapper extends Mapper<Bid, Integer>{
    UserMapper userMapper;
    ProjectMapper projectMapper;

    private static final String COLUMNS =
            "userId," +
            "projectId," +
            "amount";

    public BidMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "bid" + " " + "(" +
                "userId TEXT, " +
                "projectId TEXT, " +
                "amount INTEGER, " +
                "PRIMARY KEY (userId, projectId)," +
                "FOREIGN KEY (userId) REFERENCES user(id)," +
                "FOREIGN KEY (projectId) REFERENCES project(id)" +
                ")");
        st.close();
        con.close();
    }

    public void setMapper(UserMapper userMapper, ProjectMapper projectMapper){
        this.userMapper = userMapper;
        this.projectMapper = projectMapper;
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS +
                " FROM bid" +
                " WHERE userId = ?";
    }

    @Override
    protected Bid convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        Bid bid = new Bid(
                userMapper.findById(rs.getString("userId")),
                projectMapper.findById(rs.getString("projectId")),
                rs.getInt("amount")
        );
        return bid;
    }

    public void insertOne(Bid bid) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = "INSERT OR IGNORE INTO bid (" +
                "userId," +
                "projectId," +
                "amount" +
                ") VALUES (" +
                "" +
                "?," +
                "?," +
                "?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, bid.getUser().getId());
        st.setString(2, bid.getProject().getId());
        st.setInt(3, bid.getAmount());
        st.executeUpdate();
        st.close();
        con.close();
    }
    public Bid findBid(User user, Project project) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM bid WHERE userId = \"" + user.getId() + "\" AND projectId = \""+ project.getId() + "\"");
        Bid bid = convertResultSetToDomainModel(rs);
        st.close();
        con.close();
        return bid;
    }
    public ArrayList<Bid> findProjectBids(Project project) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM bid WHERE projectId = \"" + project.getId() + "\"");
        ArrayList<Bid> bids = new ArrayList<>();
        while(rs.next()){
            Bid bid = convertResultSetToDomainModel(rs);
            bids.add(bid);
        }
        st.close();
        con.close();
        return bids;
    }
    public ArrayList<User> findBiddingUserInProject(Project project) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM bid WHERE projectBid = \"" + project.getId() + "\"");
        ArrayList<User> biddingUsers = new ArrayList<>();
        while(rs.next()){
            User user = userMapper.findById(rs.getString("userId"));
            biddingUsers.add(user);
        }
        st.close();
        con.close();
        return biddingUsers;
    }

    public ArrayList<Bid> findAll() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM bid");
        ArrayList<Bid> bids = new ArrayList<>();
        while(rs.next()){
            Bid bid = convertResultSetToDomainModel(rs);
            bids.add(bid);
        }
        st.close();
        con.close();
        return bids;
    }
}
