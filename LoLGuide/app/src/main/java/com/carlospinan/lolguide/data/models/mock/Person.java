package com.carlospinan.lolguide.data.models.mock;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * @author Carlos Piñan
 */
public class Person extends RealmObject {
    @Required // Name is not nullable
    private String name;
    private String imageUrl; // imageUrl is an optional field
    private RealmList<Dog> dogs; // A person has many dogs (a relationship)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RealmList<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(RealmList<Dog> dogs) {
        this.dogs = dogs;
    }
}