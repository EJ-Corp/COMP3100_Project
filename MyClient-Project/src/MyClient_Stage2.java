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

            //Send "REDY" to the server to recieve the first job
            clientOutput.write(("REDY\n").getBytes());
            clientOutput.flush();

            //Read the message ("JOBN...subTime ... ID ... estRunTime ... Core ... Mem ... Disk") from the server and print them on the console
            String job = (String)serverMessages.readLine();
            System.out.println("mesage= " + serverOutput + "\n");

            //Store the job Info from the message sent by the server
            String[] splitJobInfo = job.split(" ");
            //Store the job Name
            String jobName = splitJobInfo[0];
            //Store the ID
            int jobID = Integer.parseInt(splitJobInfo[2]);
            //Store the Cores
            int jobCores = Integer.parseInt(splitJobInfo[4]);
            //Store the Memory
            int jobMem = Integer.parseInt(splitJobInfo[5]);
            //Store the Disk
            int jobDisk = Integer.parseInt(splitJobInfo[6]);

            //Ask for capable server to run the job
            clientOutput.write(("GETS Capable " + jobCores + " " + jobMem + " " + jobDisk+ "\n").getBytes());
            clientOutput.flush();



        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
}
