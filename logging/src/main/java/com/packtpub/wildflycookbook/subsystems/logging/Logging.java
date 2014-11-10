package com.packtpub.wildflycookbook.subsystems.logging;

import org.jboss.logging.Logger;

/**
 * Created by lfugaro on 9/19/14.
 */
public class Logging {

    private static Logger log = Logger.getLogger(Logging.class.getName());

    public static void log() {
        log.fatal("Fatal message");
        log.error("Error message");
        log.warn("Warning message");
        log.info("Information message");
        log.debug("Debug message");
        log.trace("Trace message");
    }
}
