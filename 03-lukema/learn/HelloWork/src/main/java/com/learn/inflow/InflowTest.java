package com.learn.inflow;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class InflowTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testTurnRobot() {

        String input[] = { "L", "R", "R", "R" };
        trunRobot(input);

    }

    public void trunRobot(String[] directions) {

        if (directions == null) {
            return;
        }

        int dirCounter = 0;

        for (String dir : directions) {
            switch (dir) {
                case "L":
                    dirCounter--;
                    break;
                case "R":
                    dirCounter++;
                    break;
                default:
                    break;
            }
        }

        int currDir = dirCounter % 4; //  -1w -2s -3e -4n
                                      //   1e  2s  3w  4n 

        switch (currDir) {
            case 0:
                LOG.info("North");
                break;
            case -1:
            case 3:
                LOG.info("West");
                break;
            case 1:
            case -3:
                LOG.info("East");
                break;
            case -2:
            case 2:
                LOG.info("South");
                break;
            default:
                break;

        }

    }

}
