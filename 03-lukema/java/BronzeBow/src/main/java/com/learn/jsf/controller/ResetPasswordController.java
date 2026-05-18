package com.learn.jsf.controller;


import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.learn.jsf.util.ControllerConstants;
import com.learn.jsf.util.Mailer;
import com.learn.jsf.util.PageUrls;
import com.learn.persistence.bean.User;
import com.learn.persistence.service.AccessService;
import com.learn.persistence.service.AppException;
import com.learn.persistence.service.UserService;


@Scope("request")
@Named
public class ResetPasswordController
    implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger();

    private String errorMessage;
    private String email;
    private String confirmEmail;
    private String confirmMessage;

    @Inject
    private UserService userService;

    @Inject
    private AccessService accessService;

    @Inject
    private UserLoginController userLoginController;

    public String resetPassword() {
        if (!email.equals(confirmEmail)) {
            errorMessage = "Email and Confirm Email do not match.";
        } else {
            email = email.trim().toLowerCase();

            try {
                User user = userService.getUserByEmail(email);
                if (user == null) {
                    errorMessage = "User not found for email: " + email;
                } else {
                    try {
                        String newEncryptedPassword = userLoginController.onewayEncrypte(ControllerConstants.DefaultPassword);
                        user.setPassword(newEncryptedPassword);
                        accessService.saveOrUpdate(user);

                        confirmMessage = "Your username and new password have been sent to your mail address. Check your mail Inbox or Trash or Spam folders in several minutes.";

                        String subject = "Your AskBuffett.com Password Reset";
                        String body = "<div>Thanks you for using AskBuffett.com. Your AskBuffett.com password has been reset. </div>";
                        body += "<div>Username: " + user.getUsername() + "</div>";
                        body += "<div>Password: " + ControllerConstants.DefaultPassword + "</div>";

                        Mailer.sendMail(subject, body, email);

                        return PageUrls.ResetPasswordConfirmed;
                    } catch (Exception e) {
                        errorMessage = e.getMessage();
                        LOG.error("Exception reset password password: " + ControllerConstants.DefaultPassword, e);
                    }
                }
            } catch (AppException e) {
                errorMessage = e.getMessage();
                LOG.error("Exception get user by email: " + email, e);
            }
        }

        return PageUrls.ResetPassword;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmMessage(String confirmMessage) {
        this.confirmMessage = confirmMessage;
    }

    public String getConfirmMessage() {
        return confirmMessage;
    }

}
