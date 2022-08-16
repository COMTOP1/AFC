package com.bswdi.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bswdi.beans.JWTToken;
import com.bswdi.beans.Users;
import com.bswdi.connection.ConnectionUtils;
import com.bswdi.dotenv.Dotenv;

/**
 * Utilities for java and user data
 *
 * @author BSWDI
 * @version 1.0
 */
public class MyUtils {
    /**
     * Attribute for connection
     */
    private static final String ATT_NAME_CONNECTION = "Q09OTkVDVElPTl9BVFRSSUJVVEU";                // Base64 encoded
    private static final String ATT_JWT_TOKEN = "SldUX1RPS0VOX0FUVFJJQlVURQ";
    private static final Random secureRandom = new SecureRandom();

    /**
     * Sets connection in attributes
     *
     * @param request request
     * @param con     connection
     */
    public static void storeConnection(ServletRequest request, Connection con) {
        request.setAttribute(ATT_NAME_CONNECTION, con);
    }

    /**
     * Return connection from attributes
     *
     * @param request request
     * @return Connection con
     */
    public static Connection getStoredConnection(ServletRequest request) {
        return (Connection) request.getAttribute(ATT_NAME_CONNECTION);
    }

    /**
     * Sets user in cookies
     *
     * @param response response
     * @param user     user
     */
    public static void storeUser(HttpServletRequest request, HttpServletResponse response, Connection con, Users user) {
        try {
            if (con == null)
                con = ConnectionUtils.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dotenv dotenv = Dotenv.load();
        JWTToken jwtToken = new JWTToken(0, user.getEmail(), getTime(), getTime1Day(), request.getHeader("user-agent"));
        try {
            try {
                jwtToken.setId(DBUtils.insertJWTToken(con, jwtToken));
            } catch (Exception e) {
                e.printStackTrace();
            }
            Algorithm algorithm = Algorithm.HMAC512(dotenv.get("JWT_SECRET"));
            String token = JWT.create()
                    .withIssuer("BSWDI")
                    .withAudience("AFC")
                    .withExpiresAt(new Date(jwtToken.getExp()))
                    .withIssuedAt(new Date(jwtToken.getIat()))
                    .withKeyId(String.valueOf(jwtToken.getId()))
                    .sign(algorithm);
            Cookie cookieJWT = new Cookie(ATT_JWT_TOKEN, Base64.getEncoder().encodeToString(token.getBytes()));
            cookieJWT.setMaxAge(24 * 60 * 60);
            response.addCookie(cookieJWT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete all cookies
     *
     * @param request  request
     * @param response response
     */
    public static void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    /**
     * Delete user from cookies
     *
     * @param response response
     */
    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(ATT_JWT_TOKEN, null);
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }

    public static long getTime1Day() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime().getTime();
    }

    /**
     * Return time
     *
     * @return String time
     */
    public static long getTime() {
        Date date = new Date();
        return date.getTime();
    }

    /**
     * Return year
     *
     * @return int year
     */
    @SuppressWarnings("deprecation")
    public static int getYear() {
        Date date = new Date();
        return date.getYear() + 1900;
    }

    /**
     * Return epoch
     *
     * @return long epoch
     */
    public static long getEpoch() {
        LocalDate date = LocalDate.now();
        return date.toEpochDay();
    }

    /**
     * Returns a random salt to be used to hash a password.
     *
     * @return a random salt with specified length
     */
    public static byte[] getNextSalt() {
        byte[] salt = new byte[KEY_LENGTH / 8];
        secureRandom.nextBytes(salt);
        return salt;
    }

    /**
     * Returns a salted and hashed password using the provided hash.<br>
     * Note - side effect: the password is destroyed (the char[] is filled with zeros)
     *
     * @param password the password to be hashed
     * @param salt     a 64 bytes salt, ideally obtained with the getNextSalt method
     *
     * @return the hashed password with a pinch of salt
     */
    public static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512").generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     * Returns true if the given password and salt match the hashed value, false otherwise.<br>
     * Note - side effect: the password is destroyed (the char[] is filled with zeros)
     *
     * @param password     the password to check
     * @param salt         the salt used to hash the password
     * @param expectedHash the expected hashed value of the password
     *
     * @return true if the given password and salt match the hashed value, false otherwise
     */
    public static boolean verifyPassword(char[] password, byte[] salt, byte[] expectedHash) {
        byte[] pwdHash = hash(password, salt);
        Arrays.fill(password, Character.MIN_VALUE);
        if (pwdHash.length != expectedHash.length) return false;
        for (int i = 0; i < pwdHash.length; i++)
            if (pwdHash[i] != expectedHash[i]) return false;
        return true;
    }
}
