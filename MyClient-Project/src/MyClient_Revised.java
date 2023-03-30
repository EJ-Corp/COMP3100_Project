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

            //recieve the first jobn
            str = (String)dis.readLine();
            System.out.println("mesage= " + str + "\n");

            //Store the job Info from the message sent by the server
            String[] splitJobInfo = str.split(" ");

            //Store the job Name
            String jobName = splitJobInfo[0];
            //Store the ID
            int jobID = Integer.parseInt(splitJobInfo[2]);

            //Gets all the servers
            dout.write(("GETS All\n").getBytes());
            dout.flush();

            //Recieve DATA line to find number of servers
            str = (String)dis.readLine();
            System.out.println("mesage= " + str + "\n");

            String[] dataSplit = str.split(" ");

            //Store number of servers
            int nRecs = Integer.parseInt(dataSplit[1]);

            //recieve all servers info
            dout.write(("OK\n").getBytes());
            dout.flush();

            //Prepare to store server info (Type, ID, Cores)
            String[] serverTypes = new String[nRecs];
            String[] serverIDs = new String[nRecs];
            int[] serverCores = new int[nRecs];

            //Store Largest Server Info
            int largestServerCores = -1; //How many cores the largest server has
            int largestServerIdx = -1; //The Index position of the first largest server
            int largestServerAmount = 0; //How many of the largest server are there

            //Store all of the servers info
            for(int i = 0; i < nRecs; i++) {
                str = (String)dis.readLine();
                System.out.println("mesage= " + str + "\n");

                String[] splitServer = str.split(" ");
                serverTypes[i] = splitServer[0];
                serverIDs[i] = splitServer[1];
                serverCores[i] = Integer.parseInt(splitServer[4]);

                //Register the largest server (Core based) & the position of the first largest
                if(serverCores[i] > largestServerCores) {
                    largestServerIdx = i;
                    largestServerCores = serverCores[i];
                }
            }

            

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
