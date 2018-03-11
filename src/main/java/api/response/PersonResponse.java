package api.response;

import api.models.Person;

/**
 * Represents a response containing a message and a person, inherits from MessageResponse
 */
public class PersonResponse extends MessageResponse {

    public Person person;

    /**
     * A constructor for the Person Response
     * @param message - a message describing the event
     * @param success - true if successful, false otherwise
     * @param person - the person to send as a response
     */
    public PersonResponse(String message, boolean success, Person person) {
        super(message, success);
        this.person = person;
    }

}
