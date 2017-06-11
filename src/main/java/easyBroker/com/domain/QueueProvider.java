package easyBroker.com.domain;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Rafal Piotrowicz on 10.06.2017.
 */
@Component
public class QueueProvider implements DestinationProvider{
    private Map<String, LinkedBlockingQueue<String>> nameToQueue =
            Collections.synchronizedMap(new HashMap<>());

    public synchronized boolean offerMessage(EasyMessage easyMessage) throws InterruptedException {

        boolean offered;

        Optional<Map.Entry<String, LinkedBlockingQueue<String>>> queue =
                getExistQueueByDestName(easyMessage);

        if (queue.isPresent()) {
            offered = queue.get().getValue().offer(easyMessage.getJsonPayload(), 5, TimeUnit.SECONDS);

        } else {
            offered = offerMessToNewQueueWithGivenName(easyMessage);
        }

        return offered;
    }

    public synchronized String pollMessage(String queueName) throws InterruptedException {
       return nameToQueue.entrySet().stream().filter(entry -> entry.getKey().equals(queueName))
                .findFirst().orElseThrow(IllegalArgumentException::new).getValue().poll(5, TimeUnit.SECONDS);
    }

    private boolean offerMessToNewQueueWithGivenName(EasyMessage easyMessage) throws InterruptedException {
        boolean offered;

        LinkedBlockingQueue<String> newQueue = new LinkedBlockingQueue<>();
        offered = newQueue.offer(easyMessage.getJsonPayload(), 5, TimeUnit.SECONDS);

        this.nameToQueue.put(easyMessage.getDestinationName(), newQueue);

        return offered;
    }

    private Optional<Map.Entry<String, LinkedBlockingQueue<String>>> getExistQueueByDestName(EasyMessage easyMessage) {
        return this.nameToQueue.entrySet().stream()
                .filter(entry -> entry.getKey().equals(easyMessage.getDestinationName()))
                .findFirst();
    }
}
