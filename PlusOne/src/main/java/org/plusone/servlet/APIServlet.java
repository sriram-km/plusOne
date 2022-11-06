package org.plusone.servlet;

/*
 * @author Sri Ram (zt-753)
 */

import org.json.JSONArray;
import org.json.JSONObject;
import org.plusone.db.DataKeeper;
import org.plusone.db.schema.PRODUCT_INFO;
import org.plusone.util.LoggerUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class APIServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(APIServlet.class.getName());

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request.getRequestURI().equals("/api/getproducts")) {
            String params = request.getParameter(ServletConstants.PARAMS);
            try {
                if (params != null) {
                    JSONObject jsonObject = new JSONObject(URLDecoder.decode(params, "UTF-8"));
                    String searchTerm = jsonObject.getString(ServletConstants.SEARCH_TERM);
                    ArrayList<ArrayList<String>> data = DataKeeper.getTheProducts(searchTerm);
                    JSONArray jsArray = new JSONArray(data);
                    ServletUtil.writeResponse(response, jsArray);
                } else {
                        ArrayList<ArrayList<String>> data = DataKeeper.getTheProducts();
                        JSONArray jsArray = new JSONArray(data);
                        ServletUtil.writeResponse(response, jsArray);
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "IO Exception occurred while sending the response.", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Exception while fetching data : {0}\n{1}", new Object[]{e.getMessage(), LoggerUtil.getStackTrace(e)});     //No I18N
            }
        }
        else if (request.getRequestURI().equals("/api/getinfo")) {
            try {
                int productCount = DataKeeper.getProductionCount();
                float totalWorth = DataKeeper.getTotalWorth();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(ServletConstants.PRODUCT_COUNT, productCount);
                jsonObject.put(ServletConstants.TOTAL_WORTH, totalWorth);
                ServletUtil.writeResponse(response, jsonObject);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "IO Exception occurred while sending the response.", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Exception while fetching data : {0}\n{1}", new Object[]{e.getMessage(), LoggerUtil.getStackTrace(e)});     //No I18N
            }
        }
        else if (request.getRequestURI().equals("/api/gettransactions")) {
            String params = request.getParameter(ServletConstants.PARAMS);
            try {
                if (params != null) {
                    JSONObject jsonObject = new JSONObject(URLDecoder.decode(params, "UTF-8"));
                    String searchTerm = jsonObject.getString(ServletConstants.SEARCH_TERM);
                    ArrayList<ArrayList<String>> data = DataKeeper.getTheTransactions(searchTerm);
                    JSONArray jsArray = new JSONArray(data);
                    ServletUtil.writeResponse(response, jsArray);
                } else {
                    ArrayList<ArrayList<String>> data = DataKeeper.getTheTransactions();
                    JSONArray jsArray = new JSONArray(data);
                    ServletUtil.writeResponse(response, jsArray);
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "IO Exception occurred while sending the response.", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Exception while fetching data : {0}\n{1}", new Object[]{e.getMessage(), LoggerUtil.getStackTrace(e)});     //No I18N
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (request.getRequestURI().equals("/api/addproduct")) {
            try {
                String params = request.getParameter(ServletConstants.PARAMS);
                JSONObject jsonObject = new JSONObject(URLDecoder.decode(params, "UTF-8"));
                String sku = jsonObject.getString(PRODUCT_INFO.SKU);
                String name = jsonObject.getString(PRODUCT_INFO.NAME);
                String description = jsonObject.getString(PRODUCT_INFO.DESCRIPTION);
                int units = jsonObject.getInt(PRODUCT_INFO.UNITS);
                double unitsPrice = jsonObject.getDouble(PRODUCT_INFO.UNITS_PRICE);

                DataKeeper.addTheProduct(sku, name, description, units, unitsPrice);

                JSONObject apiResponse = new JSONObject();
                apiResponse.put(ServletConstants.STATUS, ServletConstants.SUCCESS);
                ServletUtil.writeResponse(response,apiResponse);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "IO Exception occurred while sending the response.", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Exception while fetching data : {0}\n{1}", new Object[]{e.getMessage(), LoggerUtil.getStackTrace(e)});     //No I18N
            }
        }
        else if (request.getRequestURI().equals("/api/deleteproduct")) {
            try {
                String params = request.getParameter(ServletConstants.PARAMS);
                JSONObject jsonObject = new JSONObject(URLDecoder.decode(params, "UTF-8"));
                String sku = jsonObject.getString(PRODUCT_INFO.SKU);
                String name = jsonObject.getString(PRODUCT_INFO.NAME);
                DataKeeper.deleteTheProduct(sku, name);

                JSONObject apiResponse = new JSONObject();
                apiResponse.put(ServletConstants.STATUS, ServletConstants.SUCCESS);
                ServletUtil.writeResponse(response,apiResponse);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "IO Exception occurred while sending the response.", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Exception while fetching data : {0}\n{1}", new Object[]{e.getMessage(), LoggerUtil.getStackTrace(e)});     //No I18N
            }
        }
        else if (request.getRequestURI().equals("/api/updateproduct")) {
            try {
                String params = request.getParameter(ServletConstants.PARAMS);
                JSONObject jsonObject = new JSONObject(URLDecoder.decode(params, "UTF-8"));
                String sku = jsonObject.getString(PRODUCT_INFO.SKU);
                String name = jsonObject.getString(PRODUCT_INFO.NAME);
                JSONObject updateObject = jsonObject.getJSONObject(ServletConstants.UPDATE_DATA);

                Iterator keysIterator = updateObject.keys();

                ArrayList<String> keys = new ArrayList<>();
                ArrayList<String> values = new ArrayList<>();
                while (keysIterator.hasNext()) {
                    String key = (String) keysIterator.next();
                    String value = updateObject.get(key).toString();
                    keys.add(key);
                    values.add(value);
                }
                
                DataKeeper.updateTheProduct(sku, name, keys, values);

                JSONObject apiResponse = new JSONObject();
                apiResponse.put(ServletConstants.STATUS, ServletConstants.SUCCESS);
                ServletUtil.writeResponse(response,apiResponse);
                response.setStatus(HttpServletResponse.SC_OK);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "IO Exception occurred while sending the response.", e);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Exception while fetching data : {0}\n{1}", new Object[]{e.getMessage(), LoggerUtil.getStackTrace(e)});     //No I18N
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

