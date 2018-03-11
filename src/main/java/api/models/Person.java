package api.models;

import javax.validation.constraints.NotNull;

/***
 * Represents a person
 */
public class Person {

    @NotNull
    public int id;
    @NotNull
    public String name;

    /**
     * A constructor for the Person class
     * @param id - the person's id
     * @param name - the person's name
     */
    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }
}
