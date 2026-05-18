package com.learn.session.impl;


import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.session.PingLocal;
import com.learn.session.PingRemote;


@Stateless
@EJB(beanName = "PingSessionBean", name = "PingSessionBean", beanInterface = PingRemote.class)
public class PingSessionBean
    implements Serializable, PingLocal, PingRemote {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger(PingSessionBean.class);

    @PostConstruct
    public void postConstruct() {
        LOG.info("PingSessionBean.postConstruct()");
    }

    @PreDestroy
    public void preDestroy() {
        LOG.info("PingSessionBean.preDestroy()");
    }

    /* (non-Javadoc)
     * @see com.learn.session.impl.PingLocal#ping(java.lang.String)
     */
    /* (non-Javadoc)
     * @see com.learn.session.impl.PingRemote#ping(java.lang.String)
     */
    @Override
    public String ping(String hello)
        throws Exception {
        LOG.info("PingSessionBean.ping() is called. Received param: " + hello);
        return hello + " from PingSessionBean";
    }

}
