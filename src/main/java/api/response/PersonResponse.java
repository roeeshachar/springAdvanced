package api.response;

import api.models.Person;

/**
 * Created by roee on 11/03/18.
 */
public class PersonResponse extends MessageResponse {

    public Person person;


    public PersonResponse(String message, boolean success, Person person) {
        super(message, success);
        this.person = person;
    }

}
