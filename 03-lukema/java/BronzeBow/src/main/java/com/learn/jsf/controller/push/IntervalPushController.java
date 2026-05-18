package com.learn.jsf.controller.push;


import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.jsf.util.FacesUtils;


@ManagedBean(eager = true)
@SessionScoped
public class IntervalPushController {
    private static final Logger LOG = LogManager.getLogger();

    private static int PUSH_INTERVAL = 60000;
    private Timer timer = new Timer(true);
    // private final PortableRenderer PORTABLE_RENDERER = PushRenderer.getPortableRenderer(FacesContext.getCurrentInstance());

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
                    // PORTABLE_RENDERER.render(BaseQuoteWatcher.INDEX_PUSH_GROUP);
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

}

/*
 * 
 *    
             try
            {
               ApplicationCounter applicationCounter = (ApplicationCounter) FacesUtils
                     .getManagedBean("applicationCounter");
               if (applicationCounter != null)
               {
                  applicationCounter.increment();
                  LOG.info("applicationCounter incremented.");
               }
            }
            catch (Throwable e)
            {
               LOG.debug("applicationCounter not incremented.");
            }

 * 
 */
