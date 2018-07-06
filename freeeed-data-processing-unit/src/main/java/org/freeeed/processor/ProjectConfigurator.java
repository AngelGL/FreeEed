package org.freeeed.processor;

/**
 * This class has consumer which on start will read from config queue and configure Project
 */
public class ProjectConfigurator {

    private static final String username = "admin";
    private static final String password = "admin";
    private static final String configQueue = "config.queue";

    /**
     * wait until project is configured
     *
     * @param brokerUrl
     * @return true if project configured successfully, wait on queue and false if failed to connect
     */
    public static boolean configureProject(String brokerUrl) {
        
        return false;
    }
}
