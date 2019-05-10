package controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

public class Utils {
    private static String JWT_SECRET = "joboonja";

    public static void sendJSON(String jsonString, HttpServletResponse response, int status) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().print(jsonString);
    }

    public static String getBodyOfRequest(HttpServletRequest request) throws IOException {
        BufferedReader bufferReader = request.getReader();
        String str, body = "";
        while ((str = bufferReader.readLine()) != null) {
            body += str;
        }
        return body;
    }


    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String signJWT(String userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            long currentTime = System.currentTimeMillis();
            String token = JWT.create()
                    .withJWTId(userId)
                    .withExpiresAt(new Date(currentTime + 24 * 60 * 60 * 1000))
                    .withIssuedAt(new Date(currentTime))
                    .withIssuer("auth0")
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            return null;
            //Invalid Signing configuration / Couldn't convert Claims.
        }
    }

    public static DecodedJWT verifyJWT(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
            .withIssuer("auth0")
            .acceptExpiresAt(24 * 60 * 60)   // one day secs for exp
            .build(); // Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }
}