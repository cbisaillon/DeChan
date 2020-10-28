package com.dechan;

import com.dechan.errors.groups.GroupNotFoundException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Base64;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        Group group1 = new Group("My new computer", "Computer Science");
//        System.out.println(group1.getHash());


        System.out.println("GROUPS:");
        List<Group> groups = Group.fetchGroups();
        for(Group group: groups){
            System.out.println(group.getName() + " " + group.getHash());
        }
        System.out.println("-------");



        try {
            Group theGroup = new Group("DETZLIOKTQUCHPDDDLVBCJDYJXIRJPJBL9DAYRKJSUSKUBCDPDTHABQ9FESQFEHTULEZWBQYR99J99999");

            // Setup image
            try {
                byte[] imageContent = FileUtils.readFileToByteArray(new File(Main.class.getClassLoader().getResource("test_image_2.jpg").getPath()));
                String encodedString = Base64.getEncoder().encodeToString(imageContent);

                // Add a message to the group
                Message mess1 = new Message(encodedString, "My computer is this", theGroup);
            }catch (Exception e){
                System.out.println("Failed to load image: " + e.getMessage());
            }

            System.out.println("Fetching messages");
            List<Message> messages = theGroup.getMessages();

            for(Message message: messages){
                System.out.println(message.getMessage() + ": " + message.getTimestamp());
                System.out.println(message.getImagebase64());
            }

        }catch (GroupNotFoundException e){
            System.out.println(e.getMessage());
        }

        System.out.println("END ------");
    }
}
