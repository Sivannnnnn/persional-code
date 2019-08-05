package com.liuxw.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Author  XiaoWen
 * Date  2019/8/1 16:58
 * Version 1.0
 */
public class Test {

    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Test.class);
        logger.debug("Debug Level");
        logger.info("Info Level");
        logger.warn("Warn Level");
        logger.error("Error Level");
    }

    public static void main2(String[] args) {
        String a = "xxx";
        a.replace('x','a');
        System.out.println(a);
        a = a.replace('x','a');
        System.out.println(a);

    }
}
