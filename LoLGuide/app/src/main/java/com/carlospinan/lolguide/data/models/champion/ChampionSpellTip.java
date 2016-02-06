package com.carlospinan.lolguide.data.models.champion;

import java.util.List;

/**
 * @author Carlos Piñan
 */
public class ChampionSpellTip {

    private List<String> effect;
    private List<String> label;

    public List<String> getEffect() {
        return effect;
    }

    public void setEffect(List<String> effect) {
        this.effect = effect;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

}
