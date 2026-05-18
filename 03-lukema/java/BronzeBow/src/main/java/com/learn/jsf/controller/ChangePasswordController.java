package com.learn.jsf.controller;


import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.learn.jsf.util.PageUrls;
import com.learn.persistence.bean.User;
import com.learn.persistence.service.AccessService;
import com.learn.persistence.service.AppException;
import com.learn.persistence.service.UserService;


@Scope("request")
@Named
public class ChangePasswordController
    implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger();

    private String errorMessage;
    private String currentPassword = " ";
    private String newPassword;
    private String confirmNewPassword;

    @Inject
    private UserService userService;

    @Inject
    private AccessService accessService;

    @Inject
    private UserLoginController userLoginController;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String changePassword() {
        LOG.debug("Change password.");

        if (!confirmNewPassword.equals(newPassword)) {
            errorMessage = "New password and Confirm new password do not match.";
        } else if (newPassword.equals(currentPassword)) {
            errorMessage = "New password and current password are the same.";
        } else {
            User dbUser = null;
            try {
                dbUser = userService.getUserByUsername(userLoginController.getUser().getUsername());
            } catch (AppException e) {
                LOG.error("Exception retrieve User by username: " + userLoginController.getUser().getUsername(), e);
            }

            if (dbUser != null) {
                try {
                    String encryptedPassword = userLoginController.onewayEncrypte(currentPassword);
                    if (!encryptedPassword.equals(dbUser.getPassword())) {
                        errorMessage = "Current password not correct.";

                        try {
                            Thread.sleep(5000);
                        } catch (Exception e) {
                        }
                    } else {
                        String encryptedNewPassword = userLoginController.onewayEncrypte(newPassword);
                        userLoginController.getUser().setPassword(encryptedNewPassword);
                        accessService.saveOrUpdate(userLoginController.getUser());

                        return PageUrls.ChangePasswordConfirmed;
                    }
                } catch (NoSuchAlgorithmException e) {
                    errorMessage = e.getMessage();

                    LOG.error("Exception encrypting password.", e);
                } catch (AppException e) {
                    errorMessage = e.getMessage();

                    LOG.error("Exception updating user in DB. username = " + userLoginController.getUser().getUsername(), e);
                }
            }
        }

        return PageUrls.ChangePassword;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

}
