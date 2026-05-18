/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.broadsoft.cpbx.e911.domain;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.broadsoft.cpbx.e911.resource.ProjectProperties;
import com.broadsoft.cpbx.e911.servlet.CometStatusHandler;


/**
 * This is a simple class that uses a template to generate the model
 * of a session. It will expose two variables through the rest service
 * 
 * <p>
 * 1. status_url is where the btbc client will check to retrieve events
 * via the http long polling. The events are propagated to the btbc client via
 * the {@link CometStatusHandler} class.
 * 
 * 2. redirect_url The redirect url is the location of the web form that will allow
 * the user to change the address. This can be a simple html form. 
 * 
 * </p>
 * @see CometStatusHandler for long polling.
 * @author chris
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class AddressChangeSession
    implements Serializable {

    private static final ProjectProperties projectProperties = ProjectProperties.getInstance();

    /**
     * Serialization Id
     */
    private static final long serialVersionUID = -7518212015652445140L;

    private String tn;

    private static final String addressChangeStatusUrl = projectProperties.getAddressChangeStatusUrl();

    private static final String addressChangeRequestUrl = projectProperties.getAddressChangeRequestUrl();

    private static final String restResourceUrl = projectProperties.getRestResourceUrlTemplate();

    @XmlElement(name = "documentation")
    private final String documentation = "redirect_url and status_url are for desktop clients only.  All others use rest_resources_url for RESTful API.";

    public AddressChangeSession() {
    }

    public AddressChangeSession(String tn) {
        this.tn = tn;
    }

    @XmlElement(name = "redirect_url")
    public String getRedirectUrl() {
        return String.format(addressChangeRequestUrl, tn);
    }

    @XmlElement(name = "status_url")
    public String getStatusUrl() {
        return String.format(addressChangeStatusUrl, tn, "BTBC");
    }

    public String getDocumentation() {
        return documentation;
    }

    /**
     * This is the base location for all rest resources. It can
     * be appended to in order to 
     * @return
     */
    @XmlElement(name = "rest_resources_url")
    public String getRestResourcesUrl() {
        return restResourceUrl;
    }

    @XmlElement(name = "tn")
    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

}
