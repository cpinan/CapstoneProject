package com.carlospinan.lolguide.data.models;

/**
 * @author Carlos Piñan
 */
public class ChampionPassive {

    private String description;
    private LOLImage image;
    private String name;
    private String sanitizedDescription;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LOLImage getImage() {
        return image;
    }

    public void setImage(LOLImage image) {
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
