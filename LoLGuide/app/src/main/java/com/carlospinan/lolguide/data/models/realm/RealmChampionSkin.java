package com.carlospinan.lolguide.data.models.realm;

import io.realm.RealmObject;

/**
 * @author Carlos Piñan
 */
public class RealmChampionSkin extends RealmObject {

    private int id;
    private int num;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
