package com.learn.exception.mapper;


import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.element.BeanValidationError;
import com.learn.element.BeanValidationErrors;
import com.learn.element.TodoResponse;


@Provider
public class MyConstraintViolationExceptionMapper
    implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public Response toResponse(final ConstraintViolationException exception) {

        BeanValidationErrors beanValidationErrors = new BeanValidationErrors();

        TodoResponse todoResponse = new TodoResponse();
        todoResponse.setStatus(Status.BAD_REQUEST);
        todoResponse.setBeanValidationErrors(beanValidationErrors);

        Set<ConstraintViolation<?>> set = exception.getConstraintViolations();
        for (ConstraintViolation<?> cv : set) {
            BeanValidationError beanValidationError = new BeanValidationError();
            String value = cv.getInvalidValue() == null ? null : cv.getInvalidValue().toString();
            beanValidationError.setInvalidValue(value);
            beanValidationError.setMessage(cv.getMessage());
            beanValidationError.setMessageTemplate(cv.getMessageTemplate());
            beanValidationError.setPath(cv.getPropertyPath().toString());

            beanValidationErrors.getValidationErrors().add(beanValidationError);

            LOG.info(cv);
        }

        return Response.status(Status.BAD_REQUEST).entity(todoResponse).build();
    }
}
