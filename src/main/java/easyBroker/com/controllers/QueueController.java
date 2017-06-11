package easyBroker.com.controllers;

import easyBroker.com.domain.DestinationProvider;
import easyBroker.com.domain.EasyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

/**
 * Created by Rafal Piotrowicz on 10.06.2017.
 */

@RestController
public class QueueController {

    @Autowired
    private DestinationProvider queueProvider;

    @PostMapping(value= "/queueMessages")
    @ResponseStatus(HttpStatus.OK)
    public boolean offerOnQueue(@RequestBody EasyMessage message) throws InterruptedException {
        boolean bestead;

        bestead = queueProvider.offerMessage(message);

        return bestead;
    }

    @GetMapping(value = "/queueMessages/{queueName}")
    @ResponseStatus(HttpStatus.OK)
    public String pollMessage(@PathVariable String queueName) throws InterruptedException {
        return queueProvider.pollMessage(queueName);
    }
}
