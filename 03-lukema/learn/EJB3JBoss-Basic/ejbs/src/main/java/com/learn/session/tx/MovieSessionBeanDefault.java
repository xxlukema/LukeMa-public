package com.learn.session.tx;

//import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.learn.entity.Movie;

@Stateless
@DeclareRoles({ "Employee", "Manager" })
// Use default TransactionAttributeType.REQUIRED
// @TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MovieSessionBeanDefault implements MovieSessionBeanDefaultLocal,
		MovieSessionBeanDefaultRemote {
	private static final long serialVersionUID = 1L;

	protected static final Logger LOG = Logger
			.getLogger(MovieSessionBeanDefault.class);

	@Resource
	SessionContext sessionContext;

	@EJB
	private MovieSessionBeanMandatoryLocal movieSessionBeanMandatoryLocal;

	@Override
	@RolesAllowed({ "Employee", "Manager" })
	public void addMovie(Movie movie) throws Exception {
		if (sessionContext == null) {
			LOG.error("############## SessionContext is null.");
		} else {
			LOG.info("############## SessionContext is injected.");

			/*
			 * Principal principal = sessionContext.getCallerPrincipal(); String
			 * name = principal.getName();
			 * 
			 * LOG.info("Name: " + name);
			 * 
			 * if (sessionContext.isCallerInRole("Employee")) {
			 * LOG.info("User is in Employee Role."); } else {
			 * LOG.info("User is NOT in Employee Role."); }
			 */
		}

		movieSessionBeanMandatoryLocal.addMovie(movie);
	}

	@Override
	@RolesAllowed({ "Employee" })
	public void updateMovie(Movie movie) throws Exception {
		movieSessionBeanMandatoryLocal.updateMovie(movie);
	}

	@Override
	@RolesAllowed({ "Manager" })
	public void deleteMovie(Movie movie) throws Exception {
		movieSessionBeanMandatoryLocal.deleteMovie(movie);
	}

	@Override
	@PermitAll
	public List<Movie> getMovies() throws Exception {
		return movieSessionBeanMandatoryLocal.getMovies();
	}

}
