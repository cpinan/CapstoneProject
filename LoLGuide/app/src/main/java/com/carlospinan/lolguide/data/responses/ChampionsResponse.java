package com.carlospinan.lolguide.data.responses;

import com.carlospinan.lolguide.data.models.Champion;

import java.util.List;

/**
 * @author Carlos Piñan
 */
public class ChampionsResponse {

    private String type;
    private String version;
    private List<Champion> champions;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Champion> getChampions() {
        return champions;
    }

    public void setChampions(List<Champion> champions) {
        this.champions = champions;
    }
}
