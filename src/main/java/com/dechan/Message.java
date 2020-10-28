package com.dechan;

import com.dechan.errors.groups.GroupNotFoundException;
import com.dechan.errors.MessageNotFoundException;
import com.dechan.utility.Iota;
import org.apache.commons.lang3.StringUtils;
import org.iota.jota.model.Transaction;
import org.iota.jota.utils.TrytesConverter;

import java.util.List;

public class Message {
    private String imagebase64;
    private String message;
    private long timestamp;
    private Group group;
    private String hash;

    public Message(String hash) throws MessageNotFoundException {
        List<Transaction> tx = Iota.api.findTransactionsObjectsByHashes(hash);
        if(tx.size() <= 0){
            throw new MessageNotFoundException();
        }

        Message message = getMessageFromTransaction(tx.get(0));

        this.imagebase64 = message.getImagebase64();
        this.message = message.message;
        this.timestamp = message.getTimestamp();
        this.group = message.getGroup();
        this.hash = message.getHash();
    }

    /**
     * Create a new message and store it on the tangle
     */
    public Message(String image, String message, Group group){
        this.imagebase64 = image;
        this.message = message;
        this.group = group;

        String txMessage = TrytesConverter.asciiToTrytes(group.getHash() + ";" + this.message + ";" + this.imagebase64);
        String tag = TrytesConverter.asciiToTrytes("DM" + group.getTimestamp());

        Transaction transaction = Iota.sendMessage(txMessage, tag);

        this.hash = transaction.getHash();
        this.timestamp = transaction.getTimestamp();
    }

    /**
     * Instanciate a message that is already on the tangle
     */
    public Message(String image, String message, long timestamp, Group group, String hash){
        this.imagebase64 = image;
        this.message = message;
        this.timestamp = timestamp;
        this.group = group;
        this.hash = hash;
    }

    public static Message getMessageFromTransaction(Transaction tx){
        System.out.println(tx.getSignatureFragments().length());
        String messagetx = TrytesConverter.trytesToAscii(tx.getSignatureFragments().substring(0,2186));
        String[] information = messagetx.split(";");

        String groupHash = information[0];
        String message = information[1];
        String image = information[2];

        try {
            return new Message(
                    image,
                    message,
                    tx.getTimestamp(),
                    Group.getGroupFromHash(groupHash),
                    tx.getHash()
            );
        }catch (GroupNotFoundException e){
            return null;
        }
    }

    public String getImagebase64() {
        return imagebase64;
    }

    public void setImagebase64(String imagebase64) {
        this.imagebase64 = imagebase64;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
