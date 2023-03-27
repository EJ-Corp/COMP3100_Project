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

            
            dout.close(); //Close out output to the server (our messages)
            s.close(); //Close the connection to the server
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
