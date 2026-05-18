package com.learn.session;


import javax.ejb.Remote;


@Remote
public interface PingRemote {

    /* (non-Javadoc)
     * @see com.learn.session.impl.PingLocal#ping(java.lang.String)
     */
    public abstract String ping(String hello)
        throws Exception;

}
