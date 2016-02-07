package com.carlospinan.lolguide.data.models.mock;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * @author Carlos Piñan
 */
// Define you model class by extending the RealmObject
public class Dog extends RealmObject {
    @Required // Name cannot be null
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}