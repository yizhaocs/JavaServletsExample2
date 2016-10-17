package com.yizhao.services.dataservice;

import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * Created by yzhao on 10/17/16.
 */
public class MySQLDaoBase {

    static Logger log = Logger.getLogger("MySQLDaoBase.class");

    protected DataSource datasource;

    public DataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }


    public void execute(String sql) throws Exception{
        execute(sql, null, null);
    }

    public void execute(String sql, Object[] params) throws Exception{
        List<Object> list = new ArrayList<Object>();
        for(Object obj : params){
            list.add(obj);
        }
        execute(sql, list, null);
    }

    public void execute(String sql, List<Object> params) throws Exception{
        execute(sql, params, null);
    }

    public void execute(List<String> sqls){
        Connection connection = null;
        Statement statement = null;
        String tSql = "";
        try {
            connection = datasource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            for(String sql : sqls){
                Long start = System.currentTimeMillis();
                statement.execute(sql);
                log.info(sql + " et:" + (System.currentTimeMillis() - start));
            }
            connection.commit();

        } catch (Exception ex) {
            log.error("sql error:" + tSql, ex);
            try{
                connection.rollback();
            }
            catch(Exception rex){
                log.error("rollback error", rex);
            }
        }
        finally{
            try{
                connection.setAutoCommit(true);
                closeQuietly(connection, statement);
            }
            catch(Exception ex){ log.error("sql error", ex); }
        }
    }

    public int getInt(String sql, Object[] params) {
        long start = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try{
            connection = datasource.getConnection();
            statement = connection.prepareStatement(sql);
            if(params != null){
                int index = 0;
                for(Object param : params) {
                    statement.setObject(++index, param);
                }
            }
            statement.execute();
            rs = statement.getResultSet();

            if(rs != null && rs.next()) {
                return rs.getInt(1);
            }
        }
        catch(Exception ex) {
            log.error("sql error", ex);
        }
        finally{
            StringBuilder sb = new StringBuilder();
            if(params != null){
                for(Object param : params) {
                    sb.append("param:").append(param).append(" ");
                }
            }
            log.info(sb.toString());
            log.info("sql:" + sql + " et:" + (System.currentTimeMillis() - start));
            closeQuietly(connection, statement, rs);
        }
        return -1;
    }

    public List<Map<String, Object>> getResults(String sql ) throws Exception{
        return getResults(sql, null, null);
    }

    public List<Map<String, Object>> getResults( String sql, Object[] params ) throws Exception{
        return getResults(sql, Arrays.asList(params), null);
    }

    public List<Map<String, Object>> getResults( String sql, List<Object> params ) throws Exception{
        return getResults(sql, params, null);
    }

    public List<Map<String, Object>> getResults( String sql, List<Object> params, List<Integer> types ) throws Exception{
        Long start = System.currentTimeMillis();
        List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            connection = datasource.getConnection();
            statement = connection.prepareStatement(sql);

            executeStatement(params, types, connection, statement);

            rs = statement.getResultSet();

            while (rs.next()){
                Map<String, Object> row = new HashMap<String, Object>();
                for(int i = 1; i < rs.getMetaData().getColumnCount()+1; i++){
                    row.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
                }
                rows.add(row);
            }

        }
        catch(Exception ex){
            log.error("getResults:" + sql, ex);
            throw ex;
        }
        finally{
            log.info("sql:" + sql + " et(ms):" + (System.currentTimeMillis() - start));
            closeQuietly(connection, statement, rs);
        }
        return rows;
    }


    public List<Map<String, Object>> getResult(String sql) throws Exception{
        return getResults(sql, null, null);
    }

    public int executeInsert( String sql ) {
        return executeInsert(sql, null);
    }

    /**
     * method to execute insert statements and return the auto generated id
     * @param sql
     * @param params
     * @return auto generated id
     */
    public int executeInsert( String sql, List<Object> params ) {
        Long start = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            connection = datasource.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            executeUpdateStatement(params, null, connection, statement);

            rs = statement.getGeneratedKeys();
            if(rs != null && rs.next()) {
                return rs.getInt(1);
            }
        }
        catch(Exception ex) {
            log.error("executeInsert:" + sql, ex);
        }
        finally {
            log.info("sql:" + sql + " et(ms):" + (System.currentTimeMillis() - start));
            closeQuietly(connection, statement, rs);
        }
        return -1;
    }


	/* PRIVATE METHODS */

    /**
     * helper method to execute sql statements with params
     * @param sql
     * @param params - null-able
     * @param types - null-able
     */
    private void execute(String sql, List<Object> params, List<Integer> types) throws Exception{
        Long start = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement statement = null;
        String tSql = "";
        try {
            connection = datasource.getConnection();
            statement = connection.prepareStatement(sql);
            if(params != null){
                int index = 0;
                StringBuilder sb = new StringBuilder();

                for(Object param : params){
                    sb.append("param(" + index + ")=").append(param).append(" ");
                    if(types != null){
                        statement.setObject(++index, param, types.get(index-1));
                    }
                    else{
                        statement.setObject(++index, param);
                    }
                }
                log.info(sb.toString());
            }
            statement.execute();

            log.info(sql + " et:" + (System.currentTimeMillis() - start));

        } catch (Exception ex) {
            // TODO: return error somehow, to display appropriate message to the user. Edit/Create successful vs Edit/Create failed
            log.error("sql error:" + tSql, ex);
            try{
                connection.rollback();
            }
            catch(Exception rex){
                log.error("rollback error", rex);
            }
            throw ex;
        }
        finally{
            closeQuietly(connection, statement);
        }
    }

    /**
     * an utility method to call execute on a prepared statement
     * @param params
     * @param types
     * @param connection
     * @param statement
     * @throws SQLException
     */
    protected void executeStatement ( List<Object> params, List<Integer> types, Connection connection, PreparedStatement statement ) throws SQLException {
        executeStatementHelper(params, null, connection, statement);
        statement.execute();
    }

    /**
     * an utility method to call executeUpdate on a prepared statement
     * @param params
     * @param types
     * @param connection
     * @param statement
     * @throws SQLException
     */
    private void executeUpdateStatement ( List<Object> params, List<Integer> types, Connection connection, PreparedStatement statement ) throws SQLException {
        executeStatementHelper(params, null, connection, statement);
        statement.executeUpdate();
    }

    /**
     * an utility method to fill in param values and param types for a statement
     * @param params
     * @param types
     * @param connection
     * @param statement
     * @throws SQLException
     */
    private void executeStatementHelper ( List<Object> params, List<Integer> types, Connection connection, PreparedStatement statement ) throws SQLException {
        if(params != null){
            int index = 0;
            for(Object param : params){
                log.info("param (" + index + ")=" + param);
                if(types != null){
                    statement.setObject(++index, param, types.get(index + 1));
                }
                else{
                    statement.setObject(++index, param);
                }
            }
        }
    }

    /**
     * Get Scalar (single column) values
     * @param sql
     * @param params
     * @return
     */
    private List<Object> getScalar( String sql, List<Object> params ) throws Exception{
        Long start = System.currentTimeMillis();
        List<Object> rows = new ArrayList<Object>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            connection = datasource.getConnection();
            statement = connection.prepareStatement(sql);

            executeStatement(params, null, connection, statement);

            rs = statement.getResultSet();

            while (rs.next()){
                Object row = rs.getObject(1);
                rows.add(row);
            }
        }
        catch(Exception ex){
            log.error("getScalar:" + sql, ex);
            throw ex;
        }
        finally{
            log.info("sql:" + sql + " et(ms):" + (System.currentTimeMillis() - start));
            closeQuietly(connection, statement, rs);
        }
        return rows;
    }

    protected void closeQuietly(Connection connection, Statement statement, ResultSet resultset){
        closeQuietly(resultset);
        closeQuietly(statement);
        closeQuietly(connection);
    }

    private void closeQuietly(Connection connection, Statement statement){
        closeQuietly(statement);
        closeQuietly(connection);
    }

    private void closeQuietly(Connection connection){
        try{
            connection.close();
        }
        catch(Exception ex){}
    }

    private void closeQuietly(Statement statement){
        try{
            statement.close();
        }
        catch(Exception ex){}
    }

    private void closeQuietly(ResultSet resultset){
        try{
            resultset.close();
        }
        catch(Exception ex){}
    }

    public Map<Integer, List<Map<String, Object>>> getResultsMap( String sql, String keyColumn ) {
        return getResultsMap(sql, null, null, keyColumn);
    }

    public Map<String, Object> getFirstResult(String sql, Object[] params) throws Exception{
        List<Map<String, Object>> results = getResults(sql, Arrays.asList(params), null);
        if(results == null || results.isEmpty()){
            return null;
        }
        else{
            return results.get(0);
        }
    }

    public Map<String, Object> getFirstResult(String sql) throws Exception{
        List<Map<String, Object>> results = getResults(sql, null, null);
        if(results == null || results.isEmpty()){
            return null;
        }
        else{
            return results.get(0);
        }
    }

    public Map<Integer, List<Map<String, Object>>> getResultsMap( String sql, List<Object> params, List<Integer> types, String keyColumn ) {
        Map<Integer, List<Map<String, Object>>> rows = new HashMap<Integer, List<Map<String, Object>>>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try{
            connection = datasource.getConnection();
            statement = connection.prepareStatement(sql);

            executeStatement(params, types, connection, statement);

            rs = statement.getResultSet();

            while (rs.next()){
                Map<String, Object> row = new HashMap<String, Object>();
                for(int i = 1; i < rs.getMetaData().getColumnCount()+1; i++){
                    row.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
                }
                if(!rows.containsKey((Integer)row.get(keyColumn))){
                    rows.put((Integer)(row.get(keyColumn)), new ArrayList<Map<String, Object>>());
                }

                rows.get((Integer)(row.get(keyColumn))).add(row);
            }

        }
        catch(Exception ex){
            log.error("getResultsMap", ex);
        }
        finally{
            closeQuietly(connection, statement, rs);
        }
        return rows;
    }

    public Map<String, Object> getRow(String tablename, int pkId) throws Exception{
        String sql = String.format("select * from %s where id = ?", tablename);
        return getFirstResult(sql, new Object[]{pkId});
    }

   /* @Override
    public String toString() {
        return "MySQLDao [db_server=" + ((BasicDataSource)datasource).getUrl() + "]";
    }
*/
}