package com.felipe.palma.githubtrends_itriad.domain.model;

import java.io.Serializable;

/**
 * Created by Felipe Palma on 06/08/2019.
 */
public class Language implements Serializable {
    public String name;

    public String path;


    public Language() {
    }

    public Language(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}