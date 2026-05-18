package com.learn.jsf.controller.push;


import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import com.learn.jsf.util.FacesUtils;


@ManagedBean(eager = false)
@SessionScoped
public class GlobalCounter
    implements Serializable {
    private static final long serialVersionUID = 1L;

    protected static final Logger LOG = Logger.getLogger(GlobalCounter.class);

    private static int PUSH_INTERVAL = 60000;
    private Timer timer = new Timer(true);

    private int count;

    static {
        String interval = FacesUtils.getFacesParameter("com.learn.push.interval");
        if (interval != null) {
            try {
                PUSH_INTERVAL = Integer.parseInt(interval);
            } catch (NumberFormatException e) {
                LOG.warn("Error applying com.learn.auction.interval, must be valid integer.", e);
            }
        }
    }

    @PostConstruct
    public void init() {
        TimerTask timerTask = new TimerTask() {
            public void run() {
                try {
                    buttonClicked(null);

                    LOG.info("Render done using " + timer);
                } catch (Throwable e) {
                    LOG.error("Error running interval timer task.", e);
                }
            }
        };

        timer.schedule(timerTask, 0, PUSH_INTERVAL);
    }

    @PreDestroy
    public void destory() {
        if (timer != null) {
            timer.purge();
            timer.cancel();
        }
        LOG.info("cleaning up " + timer);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public synchronized void buttonClicked(ActionEvent actionEvent) {
        count++;

        String threadName = Thread.currentThread().getName() + "-" + Thread.currentThread().getId();

        LOG.debug(threadName + " count = " + count);

        try {
            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/counter", String.valueOf(count));
        } catch (Throwable t) {
            LOG.error(t.getMessage(), t);
        }
    }

}
