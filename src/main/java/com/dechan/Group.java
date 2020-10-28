package com.dechan;

import com.dechan.errors.groups.GroupNotFoundException;
import com.dechan.errors.groups.InvalidGroupTransactionException;
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

    /**
     * Instanciate a group from an hash
     * @param hash
     */
    public Group(String hash) throws GroupNotFoundException {
        Group group = getGroupFromHash(hash);
        if(group == null){
            throw new GroupNotFoundException();
        }

        this.name = group.getName();
        this.about = group.getAbout();
        this.hash = group.getHash();
        this.timestamp = group.getTimestamp();
    }

    public List<Message> getMessages(){
        ArrayList<Message> messages = new ArrayList<>();
        String tagToSearch = TrytesConverter.asciiToTrytes("DM" + getTimestamp());
        System.out.println("Tag to search: " + tagToSearch);

        List<Transaction> tx = Iota.api.findTransactionObjectsByTag(tagToSearch);
        for(Transaction transaction: tx){
            try {
                messages.add(new Message(transaction.getHash()));
            }catch (Exception e){

            }
        }

        return messages;
    }

    public static List<Group> fetchGroups(){
        ArrayList<Group> groups = new ArrayList<Group>();
        List<Transaction> transactions = Iota.api.findTransactionObjectsByTag("DECHANGROUP");
        for(Transaction t: transactions){
            try {
                groups.add(getGroupFromTransaction(t));
            }catch (Exception e){
                System.out.println("Invalid group information");
            }
        }

        return groups;
    }

    public static Group getGroupFromHash(String hash) throws GroupNotFoundException {
        List<Transaction> transactions = Iota.api.findTransactionsObjectsByHashes(hash);
        if(transactions.size() <= 0){
            throw new GroupNotFoundException();
        }

        try {
            return getGroupFromTransaction(transactions.get(0));
        }catch (Exception e){
            return null;
        }
    }

    public static Group getGroupFromTransaction(Transaction transaction) throws InvalidGroupTransactionException {
        String message = TrytesConverter.trytesToAscii(StringUtils.stripEnd(transaction.getSignatureFragments(), "9") );
        String[] information = message.split(";");

        if(information.length != 2){
            throw new InvalidGroupTransactionException();
        }

        String name = information[0];
        String about = information[1];

        return new Group(
                name,
                about,
                transaction.getHash(),
                transaction.getTimestamp()
        );
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
