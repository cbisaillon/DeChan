package com.dechan.utility;

import org.iota.jota.builder.AddressRequest;
import org.iota.jota.dto.response.GetNewAddressResponse;
import org.iota.jota.error.ArgumentException;

public class Addresses {
    public static String generateAddress(String seed){
        try{
            GetNewAddressResponse response = Iota.api.generateNewAddresses(
                    new AddressRequest.Builder(seed, 2).amount(1).checksum(true).build()
            );

            return response.getAddresses().get(0);
        }catch (ArgumentException e){
            System.out.println("Error generating address");
            return null;
        }
    }
}
