package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Utils {
    public static void sendJSON(String jsonString, HttpServletResponse response, int status) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().print(jsonString);
    }
    public static String getBodyOfRequest(HttpServletRequest request) throws IOException{
        BufferedReader bufferReader = request.getReader();
        String str, body ="";
        while ((str = bufferReader.readLine()) != null) {
            body += str;
        }
        return body;
    }
}
