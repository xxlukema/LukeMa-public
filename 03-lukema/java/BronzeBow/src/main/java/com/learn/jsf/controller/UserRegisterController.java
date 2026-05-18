package com.learn.jsf.controller;


import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.learn.jsf.util.AccessUtils;
import com.learn.jsf.util.JsfUtils;
import com.learn.jsf.util.PageUrls;
import com.learn.persistence.bean.User;
import com.learn.persistence.service.AccessService;
import com.learn.persistence.service.AppException;
import com.learn.persistence.service.UserService;


@Scope("session")
@Named(value = "userRegisterController")
public class UserRegisterController
    implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger();

    private User user;
    private String confirmEmail;
    private String confirmPassword;
    private String errorMessage;
    private boolean showResetPassword = false;
    private boolean autoLogin = true;

    @Inject
    private UserService userService;

    @Inject
    private AccessService accessService;

    @Inject
    private AccessUtils accessUtils;

    @Inject
    private UserLoginController userLoginController;

    public User getUser() {
        if (user == null) {
            user = new User();
        }

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String register() {
        LOG.debug("Do user register.");

        if (!user.getEmail().equalsIgnoreCase(confirmEmail)) {
            errorMessage = "Email and Confirm Email do not match.";
            return PageUrls.UserRegister;
        }

        if (!user.getPassword().equals(confirmPassword)) {
            errorMessage = "Password and Confirm Password do not match.";
            return PageUrls.UserRegister;
        }

        User dbUser = null;
        try {
            dbUser = userService.getUserByUsername(user.getUsername());
        } catch (AppException e) {
            LOG.error("Exception retrieve User by username: " + user.getUsername(), e);
        }

        if (dbUser != null) {
            errorMessage = "Username exists. Please choose a different username.";
            return PageUrls.UserRegister;
        }

        try {
            dbUser = userService.getUserByEmail(user.getEmail());
        } catch (AppException e) {
            LOG.error("Exception retrieve User by email: " + user.getEmail(), e);
        }

        if (dbUser != null) {
            errorMessage = "Email exists. If you forgot the username/password and want to reset password for that username, Click the \"Reset Password\" button.";
            showResetPassword = true;
            return PageUrls.UserRegister;
        }

        try {
            String encryptedPassword = userLoginController.onewayEncrypte(user.getPassword());
            user.setPassword(encryptedPassword);
            String remoteAddress = JsfUtils.getRemoteAddress();
            user.setRemoteAddress(remoteAddress);
            Date date = new Date();
            user.setCreateDate(date);
            user.setUpdateDate(date);

            accessService.saveOrUpdate(user);

            if (autoLogin) {
                userLoginController.doUserLoginAutoLoginTrue(user);
            } else {
                userLoginController.doUserLoginAutoLoginFalse(user);
            }

            delayMilisec(1000);

            return PageUrls.MyListEditor;
        } catch (Exception e) {
            LOG.error("Exception saving user bean.", e);
        }

        errorMessage = "User register failed.";

        delayMilisec(3000);

        return PageUrls.UserRegister;
    }

    private void delayMilisec(long milisec) {
        try {
            String remoteAddress = JsfUtils.getRemoteAddress();
            accessUtils.checkAccess(remoteAddress, "rgstr");

            Thread.sleep(milisec);
        } catch (Exception e) {
        }
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setShowResetPassword(boolean showResetPassword) {
        this.showResetPassword = showResetPassword;
    }

    public boolean isShowResetPassword() {
        return showResetPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

}
