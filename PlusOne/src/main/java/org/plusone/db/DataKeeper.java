package org.plusone.db;/*
 * @author Sri Ram (zt-753)
 */

import org.plusone.util.LoggerUtil;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataKeeper {
    private static final Logger LOGGER = Logger.getLogger(DataKeeper.class.getName());

    public static int getProductionCount(){
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) as Count FROM PRODUCT_INFO";
            CockroachPGHelper cockroachPGHelper = new CockroachPGHelper();
            ArrayList<ArrayList<String>> result = cockroachPGHelper.getResultAsList(sql);
            count = Integer.parseInt(result.get(1).get(0));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, LoggerUtil.getStackTrace(e));
        }
        return count;
    }

    public static float getTotalWorth(){
        float count = 0f;
        try {
            String sql = "SELECT Sum(UNITS*UNITS_PRICE) as Cost FROM PRODUCT_INFO";
            CockroachPGHelper cockroachPGHelper = new CockroachPGHelper();
            ArrayList<ArrayList<String>> result = cockroachPGHelper.getResultAsList(sql);
            count = Float.parseFloat(result.get(1).get(0));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, LoggerUtil.getStackTrace(e));
        }
        return count;
    }

    public static ArrayList<ArrayList<String>> getTheProducts() throws Exception {
        final String SELECT_QUERY = "SELECT * FROM PRODUCT_INFO";
        String query = "";
        try {
            query = SELECT_QUERY;
            CockroachPGHelper cockroachPGHelper = new CockroachPGHelper();
            ArrayList<ArrayList<String>> data = cockroachPGHelper.getResultAsList(query);
            return data;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception while executing the query : {0}\n{1}", new Object[]{query, LoggerUtil.getStackTrace(e)});
            throw e;
        }
    }

    public static ArrayList<ArrayList<String>> getTheProducts(String queryString) throws Exception {
        final String SELECT_QUERY = "SELECT * FROM PRODUCT_INFO WHERE position('%s' in SKU) > 0 OR position('%s' in NAME) > 0;";
        String query = "" ;
        try {
            query = String.format(SELECT_QUERY,queryString,queryString);
            CockroachPGHelper cockroachPGHelper = new CockroachPGHelper();
            ArrayList<ArrayList<String>> data = cockroachPGHelper.getResultAsList(query);
            return data;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception while executing the query : {0}\n{1}", new Object[]{query, LoggerUtil.getStackTrace(e)});
            throw e;
        }
    }

    public static ArrayList<ArrayList<String>> getTheTransactions() throws Exception {
        final String SELECT_QUERY = "SELECT * FROM PRODUCT_TRANSACTIONS";
        String query = "";
        try {
            query = SELECT_QUERY;
            CockroachPGHelper cockroachPGHelper = new CockroachPGHelper();
            ArrayList<ArrayList<String>> data = cockroachPGHelper.getResultAsList(query);
            return data;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception while executing the query : {0}\n{1}", new Object[]{query, LoggerUtil.getStackTrace(e)});
            throw e;
        }
    }

    public static ArrayList<ArrayList<String>> getTheTransactions(String queryString) throws Exception {
        final String SELECT_QUERY = "SELECT * FROM PRODUCT_TRANSACTIONS WHERE position('%s' in SKU) > 0 OR position('%s' in NAME) > 0;";
        String query = "" ;
        try {
            query = String.format(SELECT_QUERY,queryString,queryString);
            CockroachPGHelper cockroachPGHelper = new CockroachPGHelper();
            ArrayList<ArrayList<String>> data = cockroachPGHelper.getResultAsList(query);
            return data;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception while executing the query : {0}\n{1}", new Object[]{query, LoggerUtil.getStackTrace(e)});
            throw e;
        }
    }



    public static void addTheProduct(String sku, String name, String description, int units, double unitPrice) {
        final String INSERT_QUERY = "INSERT INTO PRODUCT_INFO (SKU, NAME, DESCRIPTION, UNITS, UNITS_PRICE) VALUES ('%s', '%s', '%s', %d, %f)";
        String query = "";
        try {
            query = String.format(INSERT_QUERY, sku, name, description,units, unitPrice);
            CockroachPGHelper cockroachPGHelper = new CockroachPGHelper();
            cockroachPGHelper.executeInsert(query);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception while executing the query : {0}\n{1}", new Object[]{query, LoggerUtil.getStackTrace(e)});
        }
    }

    public static void deleteTheProduct(String sku, String name){
        final String DELETE_QUERY = "DELETE FROM PRODUCT_INFO WHERE SKU = '%s' AND NAME = '%s'";
        String query= "";
        try {
            query = String.format(DELETE_QUERY, sku, name);
            CockroachPGHelper cockroachPGHelper = new CockroachPGHelper();
            cockroachPGHelper.executeInsert(query);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception while executing the query : {0}\n{1}", new Object[]{query, LoggerUtil.getStackTrace(e)});
        }
    }

    public static void updateTheProduct(String sku, String name, ArrayList<String> keys, ArrayList<String> values) {
        final String UPDATE_QUERY = "UPDATE PRODUCT_INFO SET %s WHERE SKU = '%s' AND NAME = '%s'";
        String setValue = "";
        boolean updatedUnits = false;
        int newUnits = 0;
        for (int i = 0; i < keys.size(); i++) {
            String currentKey = keys.get(i);
            String currentValue = values.get(i);

            if (i == keys.size() - 1) {
                setValue += currentKey + " = " + currentValue;
            } else {
                setValue += currentKey + " = " + currentValue + ", ";
            }

            if (currentKey.equals("UNITS")) {
                updatedUnits = true;
                newUnits = Integer.valueOf(currentValue);
            }
        }

        String query = "";

        try {
            CockroachPGHelper cockroachPGHelper = new CockroachPGHelper();

            if(updatedUnits){
                String getUnitsQuery = String.format("SELECT UNITS FROM PRODUCT_INFO WHERE SKU = '%s' AND NAME = '%s'", sku, name);
                int units = Integer.valueOf(cockroachPGHelper.getResultAsList(getUnitsQuery).get(1).get(0));
                addTransactionLog(sku, name, units, newUnits);
            }

            query = String.format(UPDATE_QUERY, setValue, sku, name);
            cockroachPGHelper.executeUpdate(query);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception while executing the query : {0}\n{1}", new Object[]{query, LoggerUtil.getStackTrace(e)});
        }
    }

    public static void addTransactionLog(String sku, String name, int oldUnits, int newUnits) {
        final String INSERT_QUERY = "INSERT INTO PRODUCT_TRANSACTIONS (SKU, NAME, COUNT_BEFORE, COUNT_AFTER, TRANSACTION) VALUES ('%s', '%s', '%d', %d, %d)";
        String query = "";
        try {
            query = String.format(INSERT_QUERY, sku, name, oldUnits, newUnits, newUnits-oldUnits);
            CockroachPGHelper cockroachPGHelper = new CockroachPGHelper();
            cockroachPGHelper.executeInsert(query);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception while executing the query : {0}\n{1}", new Object[]{query, LoggerUtil.getStackTrace(e)});
        }
    }
}
