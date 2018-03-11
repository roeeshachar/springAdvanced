package api;

import api.exceptions.PersonAlreadyExistsException;
import api.models.Person;
import api.response.MessageResponse;
import api.response.PersonResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A controller for managing Persons
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final HashMap<Integer, Person> persons;

    /**
     * A constructor for the person controller. Initialized the collection of persons
     */
    public PersonController() {
        this.persons = new HashMap<>();
        try {
            this.addPerson(new Person(1, "Person1"));
            this.addPerson(new Person(2, "Person2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * An endpoint for retrieving the persons in the system
     *
     * @return - The list of persons in the system
     */
    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<Person> get() {
        return this.getPersons();
    }


    /**
     * An endpoint for adding a person to the system
     * @param personToAdd - the person to add, as a json
     * @return - A Person Response containing the person added, if successful
     * @throws PersonAlreadyExistsException - if the person trying to add already exists in the system
     */
    @RequestMapping(method = RequestMethod.POST, path = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    public PersonResponse add(@RequestBody @Valid Person personToAdd) throws PersonAlreadyExistsException {
        if (this.personExists(personToAdd)) {
            throw new PersonAlreadyExistsException(personToAdd.getId());
        }

        this.addPerson(personToAdd);
        return new PersonResponse("Person " + personToAdd.getId() + " Added Successfully", true, personToAdd);
    }


    /**
     * An Exception handler for the PersonAlreadyExistsException Exception
     * @param e - the exception caught
     * @return - A Message Response that extracts the information from the exception to a user visible message
     */
    @ExceptionHandler(value = PersonAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public MessageResponse personAlreadyExists(PersonAlreadyExistsException e) {
        return new MessageResponse(e.getMessage(), false);
    }


    /**
     * An Exception handler for the InvalidFormatException Exception, in case of an invalid request
     * @param e - the exception caught
     * @return - A Message Response that extracts the information from the exception to a user visible message
     */
    @ExceptionHandler(value = InvalidFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public MessageResponse badRequest(InvalidFormatException e) {
        List<JsonMappingException.Reference> refs = e.getPath();
        String message;
        if (refs.size() == 0) {
            message = "Empty Request";
        } else {
            String fieldName = refs.get(0).getFieldName();
            Object value = e.getValue();
            message = "Invalid Value: " + value + " For Field " + fieldName;
        }
        return new MessageResponse(message, false);
    }


    /**
     * An Exception handler for the InvalidFormatException Exception, in case of an empty request
     * @param e - the exception caught
     * @return - A Message Response that extracts the information from the exception to a user visible message
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public MessageResponse badRequest(MethodArgumentNotValidException e) {
        return new MessageResponse("Empty Request", false);
    }


    /**
     * Adds a person to the collection of Persons
     *
     * @param person - the person to add
     */
    private void addPerson(Person person) {
        this.persons.put(person.getId(), person);
    }


    /**
     * Checks whether the given person exists in the system
     *
     * @param person - the person to check if exists
     * @return true if exists, false otherwise
     */
    private boolean personExists(Person person) {
        return (this.persons.containsKey(person.getId()));
    }

    /**
     * Returns the list of persons in the system
     *
     * @return - The list of persons in the system
     */
    private List<Person> getPersons() {
        return new ArrayList<>(this.persons.values());
    }

}