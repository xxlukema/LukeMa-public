package com.learn.jsf.controller;

import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import com.learn.jsf.util.PageUrls;
import com.learn.persistence.bean.User;
import com.learn.persistence.service.AppException;
import com.learn.persistence.util.SpringServiceFacade;

@ManagedBean
public class ChangeEmailController extends BaseController {
	private static final long serialVersionUID = 1L;

	protected static final Logger LOG = Logger
			.getLogger(ChangeEmailController.class);

	private String errorMessage;
	private String newEmail;
	private String confirmNewEmail;

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
				dbUser = SpringServiceFacade.getUserByUsername(getUser()
						.getUsername());
			} catch (AppException e) {
				LOG.error("Exception retrieve User by username: "
						+ getUser().getUsername(), e);
			}

			if (dbUser != null) {
				try {
					getUser().setEmail(newEmail);
					SpringServiceFacade.saveOrUpdate(getUser());

					return PageUrls.Account;
				} catch (AppException e) {
					errorMessage = e.getMessage();
					LOG.error("Exception updating user in DB. username = "
							+ getUser().getUsername(), e);
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
