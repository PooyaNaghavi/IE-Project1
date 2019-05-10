package dataLayer.dataMappers;

import dataLayer.DBCPDBConnectionPool;
import model.Skill;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Mapper<T, I> {

    protected Map<I, T> loadedMap = new HashMap<>();

    abstract protected T convertResultSetToDomainModel(ResultSet rs) throws SQLException;

}
