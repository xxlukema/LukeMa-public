package com.wellsfargo.cmsa.components.xmlgenerator;


/**
 * 
 * Windows --> Preferences --> Java --> Code Style --> Formatter
 * <br/>
 * Press the "Edit" button. Choose the last tab, the On/Off, and enable them with a checkbox.
 *
 */
public class Test {

    public void test(boolean flag) {
        // @formatter:off
        if (flag) {    System.out.print(true);   }else {
            System.out.print(true);
        }
        // @formatter:on

        if (flag) {
            System.out.print(true);
        } else {
            System.out.print(true);
        }
    }

}
