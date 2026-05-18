package com.learn.jsf.controller;

import java.security.NoSuchAlgorithmException;

import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import com.learn.jsf.util.PageUrls;
import com.learn.persistence.bean.User;
import com.learn.persistence.service.AppException;
import com.learn.persistence.util.SpringServiceFacade;

@ManagedBean
public class ChangePasswordController extends BaseController {
	private static final long serialVersionUID = 1L;

	protected static final Logger LOG = Logger
			.getLogger(ChangePasswordController.class);

	private String errorMessage;
	private String currentPassword = " ";
	private String newPassword;
	private String confirmNewPassword;

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
				dbUser = SpringServiceFacade.getUserByUsername(getUser()
						.getUsername());
			} catch (AppException e) {
				LOG.error("Exception retrieve User by username: "
						+ getUser().getUsername(), e);
			}

			if (dbUser != null) {
				try {
					String encryptedPassword = getUserLoginController()
							.onewayEncrypte(currentPassword);
					if (!encryptedPassword.equals(dbUser.getPassword())) {
						errorMessage = "Current password not correct.";

						try {
							Thread.sleep(5000);
						} catch (Exception e) {
						}
					} else {
						String encryptedNewPassword = getUserLoginController()
								.onewayEncrypte(newPassword);
						getUser().setPassword(encryptedNewPassword);
						SpringServiceFacade.saveOrUpdate(getUser());

						return PageUrls.ChangePasswordConfirmed;
					}
				} catch (NoSuchAlgorithmException e) {
					errorMessage = e.getMessage();

					LOG.error("Exception encrypting password.", e);
				} catch (AppException e) {
					errorMessage = e.getMessage();

					LOG.error("Exception updating user in DB. username = "
							+ getUser().getUsername(), e);
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
