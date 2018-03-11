package api.response;

/**
 * Represents a response containing a message
 */
public class MessageResponse {

    public String message;
    public boolean success;


    /**
     * A constructor for the Message Response
     * @param message - a message describing the event
     * @param success - true if successful, false otherwise
     */
    public MessageResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

}
