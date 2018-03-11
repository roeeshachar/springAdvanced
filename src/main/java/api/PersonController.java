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

    PersonController() {
        this.persons = new HashMap<>();
        try {
            this.addPerson(new Person(1, "Person1"));
            this.addPerson(new Person(2, "Person2"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPerson(Person person) throws Exception {
        if (this.persons.containsKey(person.getId())) {
            throw new Exception("Person " + person.getId() + " Already Exists");
        }

        this.persons.put(person.getId(), person);
    }


    @RequestMapping(method = RequestMethod.GET, path = "")
    public List<Person> get() {
        return new ArrayList<>(this.persons.values());
    }

    @RequestMapping(method = RequestMethod.POST, path = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    public PersonResponse add(@RequestBody @Valid Person personToAdd) throws PersonAlreadyExistsException {
        int personId = personToAdd.getId();
        if (this.persons.containsKey(personId)) {
            throw new PersonAlreadyExistsException(personId);
        }

        this.persons.put(personId, personToAdd);
        return new PersonResponse("Person " + personId + " Added Successfully", true, personToAdd);
    }


    @ExceptionHandler(value = PersonAlreadyExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public MessageResponse personAlreadyExists(PersonAlreadyExistsException e) {
        return new MessageResponse(e.getMessage(), false);
    }

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

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public MessageResponse badRequest(MethodArgumentNotValidException e) {
        return new MessageResponse("Empty Request", false);
    }

}