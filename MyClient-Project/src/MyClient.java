import java.io.*;
import java.net.*;

public class MyClient {
    public static void main(String[] args) {
        try {
            //Socket to connect to the server
            Socket s = new Socket("localhost", 50000);

            //Client output (Our Messages)
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            //Write Our messages to the server
            dout.write(("HELO\n").getBytes());
            dout.flush();

            //The messages sent by the server
            BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()));

            //Read the messages from the server and print them on the console
            String str = (String)dis.readLine();
            System.out.println("mesage= " + str + "\n");

            String username = System.getProperty("user.name");
            dout.write(("AUTH " + username +" \n").getBytes());
            dout.flush();

            str = (String)dis.readLine();
            System.out.println("mesage= " + str + "\n");

            //END OF HANDSHAKE

            //Store Variables
            int nRecs;
            String[] dataSplit;
            int nRectsIdx = 1;

            String[] serverTypes;
		    String[] serverIDs;
            int[] serverCores;

            //Inside a loop:
            dout.write(("REDY\n").getBytes());
            dout.flush();

            str = (String)dis.readLine();
            System.out.println("mesage= " + str + "\n");

            //Store the job Info from the message sent by the server
            String[] splitJobInfo = str.split(" ");

            //Store the job Name
            String jobName = splitJobInfo[0];
            //Store the ID
            int jobID = Integer.parseInt(splitJobInfo[2]);

            dout.write(("GETS All\n").getBytes());
		    dout.flush();

            str = (String)dis.readLine();
            System.out.println("mesage= " + str + "\n");

            dataSplit = str.split(" ");
            nRecs = Integer.parseInt(dataSplit[nRectsIdx]);

            serverTypes = new String[nRecs];
            serverIDs = new String[nRecs];
            serverCores = new int[nRecs];

            System.out.println("BEFORE LOOP:");

            for(int i = 0; i < nRecs; i++) {
                dout.write(("OK\n").getBytes());
                dout.flush();

                str = (String)dis.readLine();
                System.out.println("mesage= " + str + "\n");

                String[] splitServer = str.split(" ");
                serverTypes[i] = splitServer[0];
                serverIDs[i] = splitServer[1];
                serverCores[i] = Integer.parseInt(splitServer[4]) ;
            }


            dout.close(); //Close out output to the server (our messages)
            s.close(); //Close the connection to the server
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
