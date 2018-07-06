package org.freeeed.main;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.freeeed.fileutil.FileAsByteArrayManager;
import org.freeeed.helpers.ProcessProgressUIHelper;
import org.freeeed.services.Project;

import javax.jms.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A message producer which sends the fileutil message to ActiveMQ Broker
 */
public class FileMessageProducer implements Runnable {

    private static final String ACTIVE_MQ_BROKER_URI = "tcp://localhost:61616";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String FILE_QUEUE = "file.queue";

    private ProcessProgressUIHelper processProgressUIHelper;

    private ActiveMQSession session;
    private MessageProducer msgProducer;
    private ConnectionFactory connFactory;
    private Connection connection;

    public FileMessageProducer(ProcessProgressUIHelper processProgressUIHelper) {
        this.processProgressUIHelper = processProgressUIHelper;
    }

    private FileAsByteArrayManager fileManager = new FileAsByteArrayManager();

    private void setup() throws JMSException {
        connFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, ACTIVE_MQ_BROKER_URI);
        connection = connFactory.createConnection();
        connection.start();
        session = (ActiveMQSession) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public void sendBytesMessages(String inputDirectory) throws JMSException, IOException {
        setup();
        msgProducer = session.createProducer(session.createQueue(FILE_QUEUE));

        File[] files = new File(inputDirectory).listFiles();
        if (Objects.nonNull(processProgressUIHelper) && Objects.nonNull(processProgressUIHelper.getInstance())) {
            processProgressUIHelper.setTotalSize(files.length);
        }
        AtomicInteger size = new AtomicInteger();
        for (File file : files) {
            processProgressUIHelper.setProcessingState(file.getName());
            if (file.isFile()) {
                sendFileAsBytesMessage(file);
            }
            processProgressUIHelper.updateProgress(size.incrementAndGet());
        }
        close();
    }

    private void sendFileAsBytesMessage(File file) throws JMSException, IOException {
        Instant start = Instant.now();
        BytesMessage bytesMessage = session.createBytesMessage();
        bytesMessage.setStringProperty(file.getName(), file.getName());
        bytesMessage.writeBytes(fileManager.readfileAsBytes(file));
        msgProducer.send(bytesMessage);
        Instant end = Instant.now();
        System.out.println("sendFileAsBytesMessage for [" + file.getName() + "], took " + Duration.between(start, end));
    }

    private void close() {
        try {
            if (msgProducer != null) {
                msgProducer.close();
            }
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Throwable ignore) {
        }
    }

    @Override
    public void run() {
        try {
            sendBytesMessages(Project.getCurrentProject().getInventoryFileName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setInterrupted() {
        this.setInterrupted();
    }
}
