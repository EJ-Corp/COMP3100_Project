import java.io.*;
import java.net.*;


public class MyClient_Stage2 {
    public static void main(String[] args) {
        try {
            //Connect with server
            Socket connection = new Socket("localhost", 50000);

            //Prepare to send messages
            DataOutputStream clientOutput = new DataOutputStream(connection.getOutputStream());

            //Prepare to recieve messages
            BufferedReader serverMessages = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            //Do handshake / Authenticate
            clientOutput.write(("HELO\n").getBytes());
            clientOutput.flush();

            //Read the messages from the server and print them on the console
            String serverOutput = (String)serverMessages.readLine();
            System.out.println("mesage= " + serverOutput + "\n");

            //Get ur username and send it to the server to authenticate
            String username = System.getProperty("user.name");
            clientOutput.write(("AUTH " + username +"\n").getBytes());
            clientOutput.flush();

            //Server replies with OK
            serverOutput = (String)serverMessages.readLine();
            System.out.println("mesage= " + serverOutput + "\n");


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
}
