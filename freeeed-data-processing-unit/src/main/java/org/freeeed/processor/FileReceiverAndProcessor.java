package org.freeeed.processor;

import org.freeeed.main.ActionProcessing;
import org.freeeed.services.Project;

/**
 * This active me consumer will keep listening on file.queue and keep processing
 */
public class FileReceiverAndProcessor {

    public static void receiveAndProcessFiles() {
        String inventoryFileName = Project.getCurrentProject().getInventoryFileName();
        //receive file, save as inventoryFileName
        new ActionProcessing(Project.getCurrentProject().getProcessWhere());
    }
}
