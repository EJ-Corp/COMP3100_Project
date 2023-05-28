import java.io.*;
import java.net.*;


public class MyClient_S2_NP {
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
            // System.out.println("mesage= " + serverOutput + "\n");

            //Get ur username and send it to the server to authenticate
            String username = System.getProperty("user.name");
            clientOutput.write(("AUTH " + username +"\n").getBytes());
            clientOutput.flush();

            //Server replies with OK
            serverOutput = (String)serverMessages.readLine();
            // System.out.println("mesage= " + serverOutput + "\n");

            //Send "REDY" to the server to recieve the first job
            clientOutput.write(("REDY\n").getBytes());
            clientOutput.flush();

            //Read the message ("JOBN...subTime ... ID ... estRunTime ... Core ... Mem ... Disk") from the server and print them on the console
            String job = (String)serverMessages.readLine();
            // System.out.println("mesage= " + serverOutput + "\n");

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

            //Ask for available server to run the job
            clientOutput.write(("GETS Avail " + jobCores + " " + jobMem + " " + jobDisk+ "\n").getBytes());
            clientOutput.flush();

            //Get the DATA line
            serverOutput = (String)serverMessages.readLine();
            // System.out.println("mesage= " + serverOutput + "\n");

            //Store the DATA no of Servers
            String[] dataSplit = serverOutput.split(" ");

            //Store number of servers
            int nRecs = Integer.parseInt(dataSplit[1]);
            
            //recieve all servers info
            clientOutput.write(("OK\n").getBytes());
            clientOutput.flush();

            //Get the first capable server
            serverOutput = (String)serverMessages.readLine();
            // System.out.println("mesage= " + serverOutput + "\n");

            //Get ready to store the first capable server
            String[] splitServer = serverOutput.split(" ");

            String serverType = splitServer[0];
            String serverId = splitServer[1];

            //Read the remaining servers
            for(int i = 0; i < nRecs - 1; i++) {
                serverOutput = (String)serverMessages.readLine();
                // System.out.println("mesage= " + serverOutput + "\n");
            }

            //Send OK to the server / recieved all fo the servers
            clientOutput.write(("OK\n").getBytes());
            clientOutput.flush();

            //Read confirmation "."
            serverOutput = (String)serverMessages.readLine();
            // System.out.println("mesage= " + serverOutput + "\n");

            //Schedule the first job
            clientOutput.write(("SCHD " + jobID + " " + serverType + " " + serverId+ "\n").getBytes());
            clientOutput.flush();

            //Recieve schedule confirmation "OK"
            serverOutput = (String)serverMessages.readLine();
            // System.out.println("mesage= " + serverOutput + "\n");

            while(!job.equals("NONE")) {
                //Send "REDY" to the server to recieve the next job
                clientOutput.write(("REDY\n").getBytes());
                clientOutput.flush();

                //Recieve the next job
                job = (String)serverMessages.readLine();
                // System.out.println("mesage= " + serverOutput + "\n");

                splitJobInfo = job.split(" ");
                //Store the Name
                jobName = splitJobInfo[0];

                if(jobName.equals("JCPL")) {
                    continue;
                }

                if(jobName.equals("NONE")) {
                    break;
                }

                //Store the ID
                jobID = Integer.parseInt(splitJobInfo[2]);
                //Store the Cores
                jobCores = Integer.parseInt(splitJobInfo[4]);
                //Store the Memory
                jobMem = Integer.parseInt(splitJobInfo[5]);
                //Store the Disk
                jobDisk = Integer.parseInt(splitJobInfo[6]);

                 //Ask for capable server to run the job
                clientOutput.write(("GETS Avail " + jobCores + " " + jobMem + " " + jobDisk+ "\n").getBytes());
                clientOutput.flush();

                //Get the DATA line
                serverOutput = (String)serverMessages.readLine();
                // System.out.println("mesage= " + serverOutput + "\n");

                //Store the DATA no of Servers
                dataSplit = serverOutput.split(" ");

                //Store servers capable of running job
                nRecs = Integer.parseInt(dataSplit[1]);

                if(nRecs <= 0) {
                    clientOutput.write(("OK\n").getBytes());
                    clientOutput.flush();

                    serverOutput = (String)serverMessages.readLine();
                    // System.out.println("mesage= " + serverOutput + "\n");

                    clientOutput.write(("GETS Capable " + jobCores + " " + jobMem + " " + jobDisk+ "\n").getBytes());
                    clientOutput.flush();

                    serverOutput = (String)serverMessages.readLine();
                    // System.out.println("mesage= " + serverOutput + "\n");

                    dataSplit = serverOutput.split(" ");

                    nRecs = Integer.parseInt(dataSplit[1]);

                    clientOutput.write(("OK\n").getBytes());
                    clientOutput.flush();
                    
                    //Get the first Capable server
                    serverOutput = (String)serverMessages.readLine();
                    // System.out.println("mesage= " + serverOutput + "\n");

                    splitServer = serverOutput.split(" ");

                    serverType = splitServer[0];
                    serverId = splitServer[1];
                } else {
                    //recieve all servers info
                    clientOutput.write(("OK\n").getBytes());
                    clientOutput.flush();

                    //Get the first avail server
                    serverOutput = (String)serverMessages.readLine();
                    // System.out.println("mesage= " + serverOutput + "\n");

                    splitServer = serverOutput.split(" ");

                    serverType = splitServer[0];
                    serverId = splitServer[1];
                }

                

                //Read the remaining servers
                for(int i = 0; i < nRecs - 1; i++) {
                    serverOutput = (String)serverMessages.readLine();
                    // System.out.println("mesage= " + serverOutput + "\n");
                }

                //Send OK to the server / recieved all fo the servers
                clientOutput.write(("OK\n").getBytes());
                clientOutput.flush();

                //Read confirmation "."
                serverOutput = (String)serverMessages.readLine();
                // System.out.println("mesage= " + serverOutput + "\n");

                //Schedule the job
                clientOutput.write(("SCHD " + jobID + " " + serverType + " " + serverId+ "\n").getBytes());
                clientOutput.flush();

                //Recieve schedule confirmation "OK"
                serverOutput = (String)serverMessages.readLine();
                // System.out.println("mesage= " + serverOutput + "\n");
            }
            //Send "QUIT" to the server to close connection 
            clientOutput.write(("QUIT\n").getBytes());
            clientOutput.flush();

            //Read "QUIT" confirmation
            serverOutput = (String)serverMessages.readLine();
            // System.out.println("mesage= " + serverOutput + "\n");

            clientOutput.close();
            connection.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
