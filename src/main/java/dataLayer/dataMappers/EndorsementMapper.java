package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import model.Endorsement;
import model.Project;
import model.UserSkill;

import java.sql.*;
import java.util.ArrayList;

public class EndorsementMapper extends Mapper<Endorsement, Integer> {

    UserMapper userMapper;
    SkillMapper skillMapper;
    private static final String COLUMNS =
            "endorserId," +
            "endorsedId," +
            "skillName";

    public EndorsementMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "endorsement" + " " + "(" +
                "endorserId VARCHAR(100), " +
                "endorsedId VARCHAR(100), " +
                "skillName VARCHAR(100), " +
                "PRIMARY KEY (endorserId, endorsedId, skillName)," +
                "FOREIGN KEY (endorserId) REFERENCES user(id) ON DELETE CASCADE," +
                "FOREIGN KEY (endorsedId) REFERENCES user(id) ON DELETE CASCADE," +
                "FOREIGN KEY (skillName) REFERENCES userSkill(skillName) ON DELETE CASCADE" +
                ")");
        st.close();
        con.close();
    }

    public void setMapper(UserMapper userMapper, SkillMapper skillMapper){
        this.userMapper = userMapper;
        this.skillMapper = skillMapper;
    }

    @Override
    protected Endorsement convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        Endorsement endorsement = new Endorsement(
                rs.getString("endorserId"),
                rs.getString("endorsedId"),
                skillMapper.findByName(rs.getString("skillName"))
        );
        return endorsement;
    }

    public void insertOne(Endorsement endorsement) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String sql = "INSERT IGNORE INTO endorsement (" +
                "endorserId," +
                "endorsedId," +
                "skillName" +
                ") VALUES (" +
                "" +
                "?," +
                "?," +
                "?)";
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, endorsement.getEndorser());
        st.setString(2, endorsement.getEndorsed());
        st.setString(3, endorsement.getSkill().getName());
        st.executeUpdate();
        st.close();
        con.close();
    }

    public ArrayList<Endorsement> getUserEndorses(String userId, String skillName) throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        String query = "SELECT " + COLUMNS + " FROM endorsement WHERE endorsedId = ? AND skillName = ?";
//        Statement st = con.createStatement();
        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, userId);
        statement.setString(2, skillName);
        ResultSet rs = statement.executeQuery();
//        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM endorsement WHERE endorsedId = \"" + userId + "\" AND skillName = \"" + skillName +"\"");
        ArrayList<Endorsement> endorses = new ArrayList<>();
        while(rs.next()){
            Endorsement en = convertResultSetToDomainModel(rs);
            endorses.add(en);
        }
        statement.close();
        con.close();
        return endorses;
    }
}
