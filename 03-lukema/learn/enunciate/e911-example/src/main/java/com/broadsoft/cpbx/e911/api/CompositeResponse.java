/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.broadsoft.cpbx.e911.api;

import java.util.Collection;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A CompositeResponse is one in which muliple operations have been executed and
 * multiple reponses have been returned. These are collected into a Composite
 * response so that they can be examined. This extends response and the
 * getSuccess has been overridden so that if we have any failures the getSuccess
 * will return true. We still set the error code when we detect a failure but
 * just in case we want to override the method from the underlying response class.
 *
 * @author chris
 */
@XmlRootElement
public class CompositeResponse<T extends Response> extends Response {

    private static final String DEFAULT_ERROR_MESSAGE = "CONTAINS MULTIPLE FAILURES";
    private Collection<T> responses = new HashSet<T>();

    public CompositeResponse() {
    }

    public CompositeResponse(int success) {
        super(success);
    }

    public CompositeResponse(int success, String errorMessage) {
        super(success, errorMessage);
    }

    public CompositeResponse(int success, String errorMessage, Collection<T> responses) {
        super(success, errorMessage);
        addAll(responses);
    }

    public Collection<T> getResponses() {
        return responses;
    }

    /**
     * Get the list of failures for this operation.
     *
     * @return
     */
    public Collection<T> getFailures() {
        Collection<T> failures = new HashSet<T>();
        for (T response : getResponses()) {
            if (response.getStatus() == FAILURE) {
                failures.add(response);
            }
        }
        return failures;
    }

    /**
     * Add a new response to the collection of responses.
     *
     * @param response
     */
    public final void add(T response) {
        if (response == null) {
            return;
        }

        // If we receive a resonse that has failed then we
        // need to set the complex response to failed.
        if (response.getStatus() != SUCCESS) {
            setStatus(FAILURE);
            setErrorMessage(DEFAULT_ERROR_MESSAGE);
        }

        responses.add(response);
    }

    /**
     * Add a collection of responses to the the list of responses for this
     * composite operation.
     *
     * @param responses
     */
    public final void addAll(Collection<T> responses) {
        for (T response : responses) {
            add(response);
        }
    }

    /**
     * Get the number of failures for this operation.
     *
     * @return
     */
    public int getFailureCount() {
        return getFailures().size();
    }

    /**
     * Does this response contain sub failures.
     *
     * @return
     */
    public boolean hasFailures() {
        return (getFailureCount() > 0);
    }

    /**
     * Overridden just in case, we still set the status code for the parent
     * response but in case the state changes we will still return the correct
     * FAILURE or SUCCESS CODE.
     *
     * @return
     */
    @Override
    public int getStatus() {
        if (hasFailures()) {
            return FAILURE;
        }
        return SUCCESS;
    }
    
}
