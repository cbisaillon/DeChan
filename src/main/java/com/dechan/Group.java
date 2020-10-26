package com.dechan;

import com.dechan.utility.Iota;
import org.apache.commons.lang3.StringUtils;
import org.iota.jota.model.Transaction;
import org.iota.jota.utils.TrytesConverter;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private String about;
    private String hash;
    private long timestamp;

    public Group(String name, String about){
        this.name = name;
        this.about = about;

        String message = TrytesConverter.asciiToTrytes(name + ";" + about);
        String tag = "DECHANGROUP";
        Transaction transaction = Iota.sendMessage(message, tag);

        this.hash = transaction.getHash();
        this.timestamp = transaction.getTimestamp();
    }

    public Group(String name,
                 String about,
                 String hash,
                 long timestamp) {
        this.name = name;
        this.about = about;
        this.hash = hash;
        this.timestamp = timestamp;
    }

    public static List<Group> fetchGroups(){
        ArrayList<Group> groups = new ArrayList<Group>();
        List<Transaction> transactions = Iota.api.findTransactionObjectsByTag("DECHANGROUP");
        for(Transaction t: transactions){
            String message = TrytesConverter.trytesToAscii(StringUtils.stripEnd(t.getSignatureFragments(), "9") );
            String[] information = message.split(";");
            String name = information[0];
            String about = information[1];

            groups.add(new Group(
                    name,
                    about,
                    t.getHash(),
                    t.getTimestamp()
            ));
        }

        return groups;
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

    public String getHash(){
        return hash;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
