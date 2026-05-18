package com.learn.jsf.controller;


import java.io.Serializable;

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
public class ChangeEmailController
    implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger();

    private String errorMessage;
    private String newEmail;
    private String confirmNewEmail;

    @Inject
    private UserService userService;

    @Inject
    private AccessService accessService;

    @Inject
    private UserLoginController userLoginController;

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getConfirmNewEmail() {
        return confirmNewEmail;
    }

    public void setConfirmNewEmail(String confirmNewEmail) {
        this.confirmNewEmail = confirmNewEmail;
    }

    public String changeEmail() {
        LOG.debug("Change email.");

        if (!confirmNewEmail.equals(newEmail)) {
            errorMessage = "New email and Confirm new email do not match.";
        } else {
            User dbUser = null;
            try {
                dbUser = userService.getUserByUsername(userLoginController.getUser().getUsername());
            } catch (AppException e) {
                LOG.error("Exception retrieve User by username: " + userLoginController.getUser().getUsername(), e);
            }

            if (dbUser != null) {
                try {
                    userLoginController.getUser().setEmail(newEmail);
                    accessService.saveOrUpdate(userLoginController.getUser());

                    return PageUrls.Account;
                } catch (AppException e) {
                    errorMessage = e.getMessage();
                    LOG.error("Exception updating user in DB. username = " + userLoginController.getUser().getUsername(), e);
                }
            }
        }

        return PageUrls.ChangeEmail;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
