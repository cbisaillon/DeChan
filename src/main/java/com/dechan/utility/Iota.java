package com.dechan.utility;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.SendTransferResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transaction;
import org.iota.jota.model.Transfer;
import org.iota.jota.utils.SeedRandomGenerator;

import java.util.ArrayList;

public class Iota {

    public static IotaAPI api = new IotaAPI.Builder()
            .protocol("https")
            .host("nodes.thetangle.org")
            .port(443)
            .build();

    public static String seed = SeedRandomGenerator.generateNewSeed();

    /**
     * Send a message to a random address
     * @param message
     * @param tag
     * @return the hash of the transaction
     */
    public static Transaction sendMessage(String message, String tag){
        String address = Addresses.generateAddress(seed);
        return sendMessage(address, message, tag);
    }

    /**
     * Send a message to a specific address
     * @param address
     * @param message
     * @param tag
     * @return the hash of the transaction
     */
    public static Transaction sendMessage(String address, String message, String tag){
        Transfer transfer = new Transfer(address, 0, message, tag);
        ArrayList<Transfer> transfers = new ArrayList<Transfer>();
        transfers.add(transfer);

        try{
            SendTransferResponse response = api.sendTransfer(seed, 2, 3, 14, transfers, null, null, false, false, null);
            return response.getTransactions().get(0);
        }catch (ArgumentException e){
            System.err.println("Failed to send message: " + e.getMessage());
            return null;
        }
    }
}
