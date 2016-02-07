package com.carlospinan.lolguide.data.models.realm;

import io.realm.RealmObject;

/**
 * @author Carlos Piñan
 */
public class RealmChampionSpellTip extends RealmObject {

    private String effect; // String array
    private String label; // String array

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
