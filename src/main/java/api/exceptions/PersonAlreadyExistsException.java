package api.exceptions;


/**
 * An Exception Thrown when trying to add a person that already exists
 */
public class PersonAlreadyExistsException extends Exception {

    /**
     * @param personId - the person id
     */
    public PersonAlreadyExistsException(int personId) {
        super("Person " + personId + " Already Exists");
    }
}
