package com.carlospinan.lolguide.data.models.realm;

import io.realm.RealmObject;

/**
 * @author Carlos Piñan
 */
public class RealmChampionPassive extends RealmObject {

    private String description;
    private RealmLOLImage image;
    private String name;
    private String sanitizedDescription;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealmLOLImage getImage() {
        return image;
    }

    public void setImage(RealmLOLImage image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSanitizedDescription() {
        return sanitizedDescription;
    }

    public void setSanitizedDescription(String sanitizedDescription) {
        this.sanitizedDescription = sanitizedDescription;
    }
}
