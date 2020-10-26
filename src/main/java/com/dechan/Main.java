package com.dechan;

import java.util.ArrayList;

import org.iota.jota.IotaAPI;
import org.iota.jota.dto.response.SendTransferResponse;
import org.iota.jota.error.ArgumentException;
import org.iota.jota.model.Transfer;
import org.iota.jota.utils.SeedRandomGenerator;
import org.iota.jota.utils.TrytesConverter;
import com.dechan.utility.Addresses;

public class Main {

    public static void main(String[] args) {
        IotaAPI api = new IotaAPI.Builder()
                .protocol("https")
                .host("nodes.devnet.iota.org")
                .port(443)
                .build();


        String seed = SeedRandomGenerator.generateNewSeed();
        String address = Addresses.generateAddress(api, seed);

        String message = TrytesConverter.asciiToTrytes("Let's talk about random stuff;random");
        String tag = "DECHANGROUP";

        // Send transaction
        Transfer transfer = new Transfer(address, 0, message, tag);
        ArrayList<Transfer> transfers = new ArrayList<Transfer>();
        transfers.add(transfer);

        try{
            SendTransferResponse response = api.sendTransfer(seed, 2, 3, 9, transfers, null, null, false, false, null);
            System.out.println(response.getTransactions());
        }catch (ArgumentException e){
            e.printStackTrace();
        }

    }
}
