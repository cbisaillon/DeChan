package com.dechan;

import java.util.List;

public class Main {

    public static void main(String[] args) {
//        Group group1 = new Group("My new car", "Cars");
//        System.out.println(group1.getHash());


        List<Group> groups = Group.fetchGroups();
        for(Group group: groups){
            System.out.println(group.getName() + " " + group.getTimestamp());
        }

    }
}
