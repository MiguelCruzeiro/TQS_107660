package integration;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

import geocoding.*;
import connection.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressResolverIT {

    AddressResolverService addressResolverService;


    @BeforeEach
    public void init(){
        addressResolverService = new AddressResolverService(new TqsBasicHttpClient());
        
    }


    //@Disabled
    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {


        Optional<Address> address = addressResolverService.findAddressForLocation(38.743739, -9.230243);
        Address expected = new Address("Autoestrada Radial de Sintra", "Amadora", "2720", "");
        assertTrue(address.isPresent());
        assertEquals(expected, address.get());


    }

    //@Disabled
    @Test
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {

        Optional<Address> address = addressResolverService.findAddressForLocation(-361, -361);
        assertTrue(address.isEmpty());
        
    }

}
