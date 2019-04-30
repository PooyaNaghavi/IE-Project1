package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import model.Endorsement;
import model.Project;
import model.UserSkill;

import java.sql.*;
import java.util.ArrayList;

public class EndorsementMapper extends Mapper<Endorsement, Integer> {

    private static final String COLUMNS =
            "endorserId," +
            "endorsedId," +
            "skillName";

    public EndorsementMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "endorsement" + " " + "(" +
                "endorserId TEXT, " +
                "endorsedId TEXT, " +
                "skillName TEXT, " +
                "PRIMARY KEY (endorserId, endorsedId, skillName)" +
                ")");
        st.close();
        con.close();
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS +
                " FROM userSkill" +
                " WHERE endorserId = ?";
    }

    @Override
    protected Endorsement convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        UserMapper userMapper = new UserMapper();
        SkillMapper skillMapper = new SkillMapper();
        Endorsement endorsement = new Endorsement(
                userMapper.findById(rs.getString("endorserId")),
                userMapper.findById(rs.getString("endorsedId")),
                skillMapper.findByName(rs.getString("skillName"))
        );
        return endorsement;
    }

    public void insertEndorsement(Endorsement endorsement) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = "INSERT OR IGNORE INTO userSkill (" +
                "endorserId," +
                "endorsedId" +
                "skillName," +
                ") VALUES (" +
                "?," +
                "?," +
                "?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, endorsement.getEndorser().getId());
        st.setString(2, endorsement.getEndorsed().getId());
        st.setString(3, endorsement.getSkill().getName());
        st.executeUpdate();
        st.close();
        con.close();
    }

    public ArrayList<Endorsement> getUserEndorses(String userId) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM endorsement WHERE userId = " + userId);
        ArrayList<Endorsement> endorses = new ArrayList<>();
        while(rs.next()){
            Endorsement en = convertResultSetToDomainModel(rs);
            endorses.add(en);
        }
        st.close();
        con.close();
        return endorses;
    }
}
