package com.bswdi.beans;

import com.bswdi.utils.MyUtils;

@SuppressWarnings("unused")
public class JWTToken {

    long id, iat, exp;
    String email, userAgent, sessionID;

    public JWTToken() {

    }

    public JWTToken(long id, String email, long iat, long exp, String userAgent, String sessionID) {
        this.id = id;
        this.email = email;
        this.iat = iat;
        this.exp = exp;
        this.userAgent = userAgent;
        this.sessionID = sessionID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIat() {
        return iat;
    }

    public void setIat(long iat) {
        this.iat = iat;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public boolean validate(String userAgent, String sessionID) {
        if (this.id != -1)
            if (this.iat != -1)
                if (this.exp != -1)
                    if (this.userAgent.equals(userAgent))
                        if (this.sessionID.equals(sessionID))
                            if (MyUtils.getTime() >= this.iat)
                                return MyUtils.getTime() <= this.exp;
        return false;
    }

    @Override
    public String toString() {
        return "JWTToken{" +
                "id=" + id +
                ", iat=" + iat +
                ", exp=" + exp +
                ", email='" + email + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", sessionID='" + sessionID + '\'' +
                '}';
    }
}
