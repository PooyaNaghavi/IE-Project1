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
                "endorserId TEXT, " +
                "endorsedId TEXT, " +
                "skillName TEXT, " +
                "PRIMARY KEY (endorserId, endorsedId, skillName)," +
                "FOREIGN KEY (endorserId) REFERENCES user(id)," +
                "FOREIGN KEY (endorsedId) REFERENCES user(id)," +
                "FOREIGN KEY (skillName) REFERENCES userSkill(skillName)" +
                ")");
        st.close();
        con.close();
    }

    public void setMapper(UserMapper userMapper, SkillMapper skillMapper){
        this.userMapper = userMapper;
        this.skillMapper = skillMapper;
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS +
                " FROM endorsement" +
                " WHERE endorserId = ?";
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
        String sql = "INSERT OR IGNORE INTO endorsement (" +
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
        Statement st =
                con.createStatement();
        ResultSet rs = st.executeQuery("SELECT " + COLUMNS + " FROM endorsement WHERE endorsedId = \"" + userId + "\" AND skillName = \"" + skillName +"\"");
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
