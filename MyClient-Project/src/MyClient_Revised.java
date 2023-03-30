import java.io.*;
import java.net.*;


public class MyClient_Revised {
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

            //Get ur username and send it to the server to authenticate
            String username = System.getProperty("user.name");
            dout.write(("AUTH " + username +" \n").getBytes());
            dout.flush();

            //Server replies with OK
            str = (String)dis.readLine();
            System.out.println("mesage= " + str + "\n");

            //Tell the server im ready for a job
            dout.write(("REDY\n").getBytes());
            dout.flush();

            str = (String)dis.readLine();
            System.out.println("mesage= " + str + "\n");

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
