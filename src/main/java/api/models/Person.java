package api.models;

import javax.validation.constraints.NotNull;

/**
 * Created by roee on 11/03/18.
 */

public class Person {

    @NotNull
    public int id;
    @NotNull
    public String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
