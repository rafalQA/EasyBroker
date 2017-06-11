package easyBroker.com.domain;

/**
 * Created by Rafal Piotrowicz on 10.06.2017.
 */
public interface DestinationProvider {
    boolean offerMessage(EasyMessage easyMessage) throws InterruptedException;
    String pollMessage(String queueName) throws InterruptedException;
}
