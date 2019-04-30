package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Mapper<T, I> {

    protected Map<I, T> loadedMap = new HashMap<>();

    abstract protected String getFindStatement();

    abstract protected T convertResultSetToDomainModel(ResultSet rs) throws SQLException;


    public T find(I id) throws SQLException {
        T result = loadedMap.get(id);
        if (result != null)
            return result;

        try (Connection con = DBCPDBConnectionPool.getConnection();
             PreparedStatement st = con.prepareStatement(getFindStatement())
        ) {
            st.setString(1, id.toString());
            ResultSet resultSet;
            try {
                resultSet = st.executeQuery();
                resultSet.next();
                return convertResultSetToDomainModel(resultSet);
            } catch (SQLException ex) {
                System.out.println("error in Mapper.findByID query.");
                throw ex;
            }
        }
    }
}
