package org.plusone.db;

import org.plusone.util.PlusOneUtil;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CockroachPGHelper {
    private final PGSimpleDataSource DS;
    private final Logger LOGGER = Logger.getLogger(CockroachPGHelper.class.getName());

    private PGSimpleDataSource getDataSource() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        Properties properties = PlusOneUtil.getPlusOneProperties();

        final String dburl = properties.getProperty("cockroach.db.url");
        final String username = properties.getProperty("cockroach.db.user");
        final String password = properties.getProperty("cockroach.db.password");

        ds.setUrl(dburl);
        ds.setUser(username);
        ds.setPassword(password);
        return ds;
    }

    public CockroachPGHelper(){
        DS = this.getDataSource();
    }

    public ArrayList<ArrayList<String>> getResultAsList(String sql) throws Exception {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<ArrayList<String>> rows = new ArrayList();

        try {
            conn = DS.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            int columnsCount = rs.getMetaData().getColumnCount();
            ArrayList<String> columnNames = new ArrayList();
            for (int i = 1; i <= columnsCount; i++) {
                columnNames.add(rs.getMetaData().getColumnName(i));
            }

            rows.add(columnNames);

            while(rs.next()) {
                ArrayList<String> rowVal = new ArrayList();

                for(int i = 1; i <= columnsCount; ++i) {
                    if (rs.getObject(i) != null) {
                        rowVal.add(String.valueOf(rs.getObject(i)));
                    } else {
                        rowVal.add("");
                    }
                }

                rows.add(rowVal);
            }
        } finally {
            safeClose(rs, stmt, conn);
        }

        return rows;
    }

    public int executeUpdate(String sql) throws Exception {

        Connection connection = DS.getConnection();
        Statement stmt = null;

        int var5;
        try {
            stmt = connection.createStatement();
            int result = stmt.executeUpdate(sql);
            var5 = result;
        } finally {
            safeClose((ResultSet)null, stmt, connection);
        }

        return var5;
    }

    public int executeInsert(String sql) throws Exception {

        Connection connection = DS.getConnection();
        Statement stmt = null;

        int var5;
        try {
            stmt = connection.createStatement();
            int result = stmt.executeUpdate(sql);
            var5 = result;
        } finally {
            safeClose((ResultSet)null, stmt, connection);
        }

        return var5;
    }

    public int executeDelete(String sql) throws Exception {

        Connection connection = DS.getConnection();
        Statement stmt = null;

        int var5;
        try {
            stmt = connection.createStatement();
            int result = stmt.executeUpdate(sql);
            var5 = result;
        } finally {
            safeClose((ResultSet)null, stmt, connection);
        }

        return var5;
    }

    public void executeQuery(String sql) throws Exception {

        Connection connection = DS.getConnection();
        Statement stmt = null;

        try {
            stmt = connection.createStatement();
            stmt.execute(sql);
        } finally {
            safeClose((ResultSet)null, stmt, connection);
        }


    }

    public void safeClose(ResultSet rs, Statement stm, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (Exception var6) {
            LOGGER.log(Level.FINEST, "", var6);
        }

        try {
            if (stm != null) {
                stm.close();
            }
        } catch (Exception var5) {
            LOGGER.log(Level.FINEST, "", var5);
        }

        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception var4) {
            LOGGER.log(Level.FINEST, "", var4);
        }

    }

    public static void main(String[] args) {
        CockroachPGHelper helper = new CockroachPGHelper();
        try {
            helper.executeQuery("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
