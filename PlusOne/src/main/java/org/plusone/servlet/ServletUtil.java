package org.plusone.servlet;/*
 * @author Sri Ram (zt-753)
 */

import org.json.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletUtil {
    public static void writeResponse(HttpServletResponse response, Object responseData) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(responseData.toString());
    }
}
