package org.freeeed.processor;

public class StartProcessor {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Broker url is mandatory");
            return;
        }

        boolean projectConfigured = ProjectConfigurator.configureProject(args[0]);
        if (!projectConfigured) {
            System.err.println("Failed to configure project, check broker url");
        }

        System.out.println("Project configured successfully, ready to take work from file.queue");

        FileReceiverAndProcessor.receiveAndProcessFiles();

        System.out.println("Done with processing.");
    }
}
