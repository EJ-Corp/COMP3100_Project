import java.io.*;
import java.net.*;


public class MyClient_FC {
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
            //System.out.println("mesage= " + str + "\n");

            //Get ur username and send it to the server to authenticate
            String username = System.getProperty("user.name");
            dout.write(("AUTH " + username +"\n").getBytes());
            dout.flush();

            //Server replies with OK
            str = (String)dis.readLine();
            //System.out.println("mesage= " + str + "\n");

            //Tell the server im ready for a job
            dout.write(("REDY\n").getBytes());
            dout.flush();

            //recieve the first jobn
            String job = (String)dis.readLine();
            //System.out.println("mesage= " + str + "\n");

            //Store the job Info from the message sent by the server
            String[] splitJobInfo = job.split(" ");

            //Store the job Name
            String jobName = splitJobInfo[0];
            //Store the ID, cores, mem & disk
            int jobID = Integer.parseInt(splitJobInfo[2]);
            int jobCores = Integer.parseInt(splitJobInfo[4]);
            int jobMemory = Integer.parseInt(splitJobInfo[5]);
            int jobDisk = Integer.parseInt(splitJobInfo[6]);

            boolean firstJobScheduled = false;

            while(!job.equals("NONE")) {

                if(!firstJobScheduled)
                {
                    //Gets all the servers capable
                    dout.write(("GETS Capable " + jobCores + " " + jobMemory + " " + jobDisk + "\n").getBytes());
                    dout.flush();

                    //Recieve DATA line to find number of servers
                    str = (String)dis.readLine();
                    System.out.println("mesage= " + str + "\n");

                    //recieve all servers info
                    dout.write(("OK\n").getBytes());
                    dout.flush();

                    //Recieve the first capable server
                    str = (String)dis.readLine();
                    System.out.println("mesage= " + str + "\n");

                    String[] splitServer = str.split(" ");

                    String serverType = splitServer[0];
                    String serverId = splitServer[1];

                    
                    //Tell server We recieved all fo the servers info
                    dout.write(("OK\n").getBytes());
                    dout.flush();

                    //Read confimartion "."
                    str = (String)dis.readLine();
                    System.out.println("mesage= " + str + "\n");

                    dout.write(("SCHD " + jobID + " " + serverType + " " + serverId+ "\n").getBytes());
                    dout.flush();

                    str = (String)dis.readLine();
                    System.out.println("mesage= " + str + "\n");

                    firstJobScheduled = true;
                }

                //Tell the server im ready for a job
                dout.write(("REDY\n").getBytes());
                dout.flush();
                
                job = (String)dis.readLine();
                System.out.println("mesage= " + job + "\n");

                splitJobInfo = job.split(" ");
                
                jobID = Integer.parseInt(splitJobInfo[2]);
                jobCores = Integer.parseInt(splitJobInfo[4]);
                jobMemory = Integer.parseInt(splitJobInfo[5]);
                jobDisk = Integer.parseInt(splitJobInfo[6]);

                dout.write(("GETS Capable " + jobCores + " " + jobMemory + " " + jobDisk + "\n").getBytes());
                dout.flush();

                //Recieve DATA line to find number of servers
                str = (String)dis.readLine();
                System.out.println("mesage= " + str + "\n");

                //recieve all servers info
                dout.write(("OK\n").getBytes());
                dout.flush();

                //Recieve the first capable server
                str = (String)dis.readLine();
                System.out.println("mesage= " + str + "\n");

                String[] splitServer = str.split(" ");

                String serverType = splitServer[0];
                String serverId = splitServer[1];

                
                //Tell server We recieved all fo the servers info
                dout.write(("OK\n").getBytes());
                dout.flush();

                //Read confimartion "."
                str = (String)dis.readLine();
                System.out.println("mesage= " + str + "\n");

                dout.write(("SCHD " + jobID + " " + serverType + " " + serverId+ "\n").getBytes());
                dout.flush();

                str = (String)dis.readLine();
                System.out.println("mesage= " + str + "\n");
                
            }
           

            dout.write(("QUIT\n").getBytes());
            dout.flush();

            str = (String)dis.readLine();
            System.out.println("mesage= " + str + "\n");

            dout.close(); //Close out output to the server (our messages)
            s.close(); //Close the connection to the server

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

