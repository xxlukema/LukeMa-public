package com.learn;


import java.io.Serializable;

import javax.inject.Named;


@Named
public class SpringBean
    implements Serializable {

    private static final long serialVersionUID = 1L;

    public String getMessage() {
        return "Jax-Ws + Spring Integration";
    }

}
