package com.felipe.palma.githubtrends_itriad.domain.model;

import java.io.Serializable;

/**
 * Created by Felipe Palma on 06/08/2019.
 */
public class Language implements Serializable {
    public String name;

    public String path;

    private String shortName;

    public String getShortName() {
        return shortName == null ? name : shortName;
    }

    public Language() {
    }

    public Language(String name, String path) {
        this.name = name;
        this.path = path;
    }
}