package com.dechan;

public class Group {
    private String name;
    private String about;

    public Group(String name, String about) {
        this.name = name;
        this.about = about;

        // Add it on the tangle
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
