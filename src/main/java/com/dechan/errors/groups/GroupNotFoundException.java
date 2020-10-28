package com.dechan.errors.groups;

public class GroupNotFoundException extends Exception {

    public GroupNotFoundException(){
        super("The group with the specified hash was not found");
    }
}
