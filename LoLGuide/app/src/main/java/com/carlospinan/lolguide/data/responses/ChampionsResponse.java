package com.carlospinan.lolguide.data.responses;

import com.carlospinan.lolguide.data.models.Champion;

import java.util.Map;

/**
 * @author Carlos Piñan
 */
public class ChampionsResponse {

    private String type;
    private String version;
    private Map<String, Champion> data;

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

    public Map<String, Champion> getData() {
        return data;
    }

    public void setData(Map<String, Champion> data) {
        this.data = data;
    }
}
