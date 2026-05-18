package com.learn.jsf.controller;


import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.learn.common.util.DataStreamer;
import com.learn.jsf.util.AccessUtils;
import com.learn.jsf.util.ControllerConstants;
import com.learn.jsf.util.JsfUtils;
import com.learn.jsf.util.PageUrls;
import com.learn.persistence.bean.User;
import com.learn.persistence.service.AppException;
import com.learn.persistence.service.UserService;


@Scope("session")
@Named
public class UserLoginController
    implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger();

    private static MessageDigest MD = null;

    private static final String AskBuffettUser = "AskBuffettUser";

    private static final Lock LOCK = new ReentrantLock();

    private static final int COOKIE_MAX_AGE = 60 * 24 * 3600; // 60 days.

    private static final String CookiePath = "/";

    private User user;
    private String errorMessage;
    private boolean autoLogin = true;

    @Inject
    private UserService userService;

    @Inject
    private AccessUtils accessUtils;

    public User getUser() {
        if (user == null) {
            user = new User();
        }

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String doLogin() {
        LOG.debug("Do user login.");

        User dbUser = null;
        try {
            dbUser = userService.getUserByUsername(user.getUsername());
        } catch (AppException e) {
            LOG.error("Exception retrieve User by username: " + user.getUsername(), e);
        }

        if (dbUser == null) {
            try {
                dbUser = userService.getUserByEmail(user.getUsername());
            } catch (AppException e) {
                LOG.error("Exception retrieve User by email: " + user.getEmail(), e);
            }
        }

        if (dbUser == null) {
            errorMessage = "User not found for username: " + user.getUsername();
        } else {
            String encryptedPassword = null;
            try {
                encryptedPassword = onewayEncrypte(user.getPassword());
            } catch (NoSuchAlgorithmException e) {
                LOG.error("Exception encrypting password.", e);
            }

            if (dbUser.getPassword().equals(encryptedPassword)) {
                if (autoLogin) {
                    doUserLoginAutoLoginTrue(dbUser);
                } else {
                    doUserLoginAutoLoginFalse(dbUser);
                }

                user = dbUser;

                if (user.getPassword().equalsIgnoreCase(ControllerConstants.DefaultPassword)) {
                    return PageUrls.ChangePassword;
                } else {
                    return PageUrls.Account;
                }
            } else {
                errorMessage = "Username/password not match.";
            }
        }

        delayMilisec(3000);

        user = null;

        return PageUrls.Login;
    }

    public String doLogout() {
        LOG.debug("Do user logout.");

        if (isUserLoggedIn()) {
            doUserLogout();
        }

        return PageUrls.LogoutConfirmed;
    }

    public String toggleLoginLogout() {
        LOG.debug("Do execute.");

        if (isUserLoggedIn()) {
            doUserLogout();
            return PageUrls.LogoutConfirmed;
        } else {
            doUserLogout();
            return PageUrls.Login;
        }
    }

    public String toLoginOrAccount() {
        if (!isUserLoggedIn()) {
            return PageUrls.Login;
        } else {
            return PageUrls.Account;
        }
    }

    public String toMyListEditor() {
        return PageUrls.MyListEditor;
    }

    public String toChangeEmail() {
        return PageUrls.ChangeEmail;
    }

    public String toChangePassword() {
        return PageUrls.ChangePassword;
    }

    private void delayMilisec(long milisec) {
        try {
            String remoteAddress = JsfUtils.getRemoteAddress();
            accessUtils.checkAccess(remoteAddress, "login");

            Thread.sleep(milisec);
        } catch (Exception e) {
        }
    }

    public boolean isUserLoggedIn() {
        if (getUser() == null || getUser().getUsername() == null) {
            return false;
        } else {
            return true;
        }
    }

    public void loginUserFromCookie() {
        String username = getUsernameFromCookie();
        User user = null;
        if (username != null) {
            user = getUserFromDB(username);
        }

        setUser(user);
    }

    public String toLogin() {
        return PageUrls.Login;
    }

    public String toUserRegister() {
        return PageUrls.UserRegister;
    }

    public String toResetPassword() {
        return PageUrls.ResetPassword;
    }

    public void doUserLoginAutoLoginFalse(User user) {
        setUser(user);
        deleteUserCookie();
    }

    public void doUserLoginAutoLoginTrue(User user) {
        setUser(user);
        addMaxAgeCookie();
    }

    public void doUserLogout() {
        deleteUserCookie();
        setUser(null);
    }

    public User getUserFromDB(String username) {
        User user = null;
        if (username != null) {
            try {
                user = userService.getUserByUsername(username);
            } catch (AppException e) {
                LOG.error("Unable to retrieve user by username for: " + username, e);
            }
        }

        return user;
    }

    public String getUsername() {
        String username = null;

        User user = getUser();
        if (user != null) {
            username = user.getUsername();
        } else {
            username = getUsernameFromCookie();
        }

        return username;
    }

    public String onewayEncrypte(String value)
        throws NoSuchAlgorithmException {
        if (value == null) {
            value = "null";
        }

        if (MD == null) {
            LOCK.lock();

            try {
                if (MD == null) {
                    MD = MessageDigest.getInstance("SHA-512");
                }
            } finally {
                LOCK.unlock();
            }
        }

        String hex = DataStreamer.bytesToHexString(MD.digest(value.getBytes()));

        return Integer.toHexString(hex.hashCode());
    }

    public String getUsernameFromCookie() {
        Cookie cookie = getUserCookie();
        String username = null;
        if (cookie != null) {
            String hexUsername = cookie.getValue();
            try {
                username = DataStreamer.hexStringDeserializeToObject(hexUsername);
            } catch (Exception e) {
                LOG.error("Unable to deserialize hex string to object.", e);
            }
        }

        return username;
    }

    private static String getCookieName() {
        String cookieName = null;
        try {
            cookieName = DataStreamer.objectSerializeToHexString(AskBuffettUser);
        } catch (Exception e) {
            LOG.error("Unable to serialize an object to hex string", e);
        }

        return cookieName;
    }

    public Cookie getUserCookie() {
        HttpServletRequest request = JsfUtils.getHttpServletRequest();

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }

        String cookieName = getCookieName();
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (name.equals(cookieName)) {
                String path = cookie.getPath();

                if (path == null || path.equalsIgnoreCase(CookiePath)) {
                    return cookie;
                }
            }
        }

        return null;
    }

    public void addMaxAgeCookie() {
        addUserCookieToHttpServletResponse(COOKIE_MAX_AGE);
    }

    public void deleteUserCookie() {
        addUserCookieToHttpServletResponse(0);
    }

    private void addUserCookieToHttpServletResponse(int expireInSeconds) {
        String username = getUsername();
        if (username != null) {
            String hexUsername = null;
            try {
                hexUsername = DataStreamer.objectSerializeToHexString(username);
            } catch (Exception e) {
                LOG.error("Unable to serialize object to hex string: " + username, e);
                hexUsername = "null";
            }

            String cookieName = getCookieName();
            Cookie cookie = new Cookie(cookieName, hexUsername);
            cookie.setMaxAge(expireInSeconds);
            cookie.setPath(CookiePath);

            JsfUtils.getHttpServletResponse().addCookie(cookie);
        }
    }

    public void printCookies() {
        HttpServletRequest request = JsfUtils.getHttpServletRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            LOG.info("No cookie.");
        } else {
            for (Cookie cookie : cookies) {
                LOG.info("cookie: " + cookie);
            }
        }
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
