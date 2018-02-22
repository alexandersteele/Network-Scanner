import java.io.IOException;
import java.net.*;
import java.lang.*;
import java.util.Scanner;

public class cwk1 {

    private static Scanner kbdReader = null;
    private InetAddress inet;


    //
    // addressCommonality - Splits hostnames into integers to be stored in addressSplit
    //
    public void addressCommonality (String[] hosts) {

        int currentAddress = 0;
        String [] [] addressesSplit = new String[hosts.length][];

        for (String host : hosts) {
            try {
                //Create instance
                inet = InetAddress.getByName( host );

                //Split the address
                String[] addressSplit = inet.getHostAddress().split("\\.");


                //Store split address in multi-dimensional array
                addressesSplit[currentAddress] = addressSplit;

                //Increment to the next address in array
                currentAddress++;
            }
            catch ( UnknownHostException e) {
                e.printStackTrace();
            }
        }

        //Algorithm comparing for hierarchical communality of addresses
        resolveCommonality(addressesSplit);


    }


    //
    // resolveCommonality - Algorithm for comparing split addresses for commonality
    //
    public void resolveCommonality (String [][] addressesSplit) {
        int currentAddress = 0;
        boolean commonToggle = false; //true if previous number is unique, rather than common

        System.out.print("Hierarchical Commonality: ");

        for (int j = 0; j < addressesSplit[0].length; j++) { //Loop through corresponding numbers in each separate address
            if (addressesSplit[currentAddress][j].equals(addressesSplit[currentAddress+1][j]) && !commonToggle) { //If numbers match
                    System.out.print(addressesSplit[currentAddress][j] + ".");
            }
            else { //If numbers don't match
                    System.out.print("*.");
                    commonToggle = true;

            }
        }


        System.out.println("");
        System.out.println("");

    }


    //
    // resolve - Outputs host name, canonical host name, IP address, reachability and IPV version of input hosts
    //
    public void resolve(String [] hosts) {

        //Loop through hosts
        for (String host : hosts) {
            // Try: Create an instance of InetAddress using the factory method
            // Fail: Throw UnknownHostException

            try {
                inet = InetAddress.getByName( host );

                // Use three getter methods to get host, canonical host and host address
                System.out.println( "Host name : " + inet.getHostName   () );
                System.out.println( "Canonical Host name : " + inet.getCanonicalHostName() );
                System.out.println( "IP Address: " + inet.getHostAddress() );
            }
            // Catch: Failed instance
            catch( UnknownHostException e ){ 		// If an exception was thrown, echo to stdout.
                e.printStackTrace();
            }


            //Try: Test InetAddress instance to see if reachable within 1 sec
            //Fail: Throw IOException
            try {
                System.out.println("Reachable: " + inet.isReachable(1000));
            }
            //Catch: Failed IO
            catch (IOException e) {
                e.printStackTrace();
            }

            //Check if InetAddress instance is IPV4 or IPV6
            if (inet instanceof Inet4Address){
                System.out.println("IP Version: IPV4");
            }
            else {
                System.out.println("IP Version: IPV6");
            }
            System.out.println("");

        }



    }

    public static void main( String[] args ) {
        cwk1 lookup = new cwk1(); //Create instance

        //Continuous input
        if (args.length == 0) {

            kbdReader = new Scanner( System.in );

            while( true ) {
                System.out.println("Enter hostname: ");
                // Read a line from stdin
                String cmd = kbdReader.nextLine();

                // Split into segments separated by spaces.
                String[] segmented = cmd.split(" ");
                System.out.println();

                lookup.resolve(segmented);

                if (segmented.length == 2) {
                    lookup.addressCommonality(segmented);
                }
                else {
                    System.out.println("(Hierarchical Commonality only available for two hosts)");
                    System.out.println("");
                }
            }
        }
        //argument input
        else {
            lookup.resolve(args);				// The first command line argument is args[0].
            if (args.length == 2) {
                lookup.addressCommonality(args);
            }
            else {
                System.out.println("(Hierarchical Commonality only available for two hosts)");
                System.out.println("");
            }

        }

    }
}
