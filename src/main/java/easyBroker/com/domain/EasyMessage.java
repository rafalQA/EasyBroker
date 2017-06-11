package easyBroker.com.domain;


/**
 * Created by Rafal Piotrowicz on 10.06.2017.
 */
public class EasyMessage {


    private String destinationName;
    private String jsonPayload;

    public String getJsonPayload() {
        return jsonPayload;
    }

    public void setJsonPayload(String jsonPayload) {
        this.jsonPayload = jsonPayload;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }
}
