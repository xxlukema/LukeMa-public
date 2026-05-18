package com.learn.session;


import javax.ejb.Local;


@Local
public interface PingLocal {

    public abstract String ping(String hello)
        throws Exception;

}
