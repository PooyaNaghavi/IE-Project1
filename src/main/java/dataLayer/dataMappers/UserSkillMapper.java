package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import model.ProjectSkill;
import model.UserSkill;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserSkillMapper extends Mapper<UserSkill, Integer> {
    private static final String COLUMNS = "name";

    public UserSkillMapper() throws SQLException {
        Connection con = DBCPDBConnectionPool.getConnection();
        Statement st =
                con.createStatement();
        st.executeUpdate("CREATE TABLE IF NOT EXISTS " + "userSkill" + " " + "(" +
                "skillName TEXT " +
                "userId TEXT" +
                "PRIMARY KEY (skillName, userId)" +
                ")");
        st.close();
        con.close();
    }

    @Override
    protected String getFindStatement() {
        return "SELECT " + COLUMNS +
                " FROM userSkill" +
                " WHERE userId = ?";
    }

    @Override
    protected UserSkill convertResultSetToDomainModel(ResultSet rs) throws SQLException {
        UserMapper userMapper = new UserMapper();
        SkillMapper skillMapper = new SkillMapper();
        EndorsementMapper endorsementMapper = new EndorsementMapper();
        UserSkill userSkill = new UserSkill(
                userMapper.findById(rs.getString("userId")),
                skillMapper.findByName(rs.getString("skillName")),
                endorsementMapper.getUserEndorses(rs.getString("userId"))
        );
        return userSkill;
    }

}
