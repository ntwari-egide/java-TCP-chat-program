package chat;

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) throws Exception{
        
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println(ip);
        Socket clientsocket = new Socket(ip, 1234);
        
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
        DataOutputStream outToServer = new DataOutputStream(clientsocket.getOutputStream());
        
        CThread write = new CThread(inFromServer, outToServer, 0);
        CThread read = new CThread(inFromServer, outToServer, 1);
        write.join();
        read.join();
        
        clientsocket.close();
    }
}

class CThread extends Thread{
    BufferedReader inFromServer;
    DataOutputStream outToServer;
    int flag;
    
    public CThread(BufferedReader in, DataOutputStream out, int f){
        inFromServer = in;
        outToServer = out;
        flag = f;
        start();
    }
    
    @Override
    public void run(){
        String input;
        try{
           while(true){
               if(flag==0){
                   BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
                   input = inFromUser.readLine();
                   outToServer.writeBytes(input + '\n');
               }
               else if(flag==1){
                   input = inFromServer.readLine();
                   String values[] = input.split("%id%");
                   System.out.println("From " + values[1] + ": " + values[0]);
               }
           }
        }catch(Exception e){
            System.out.println("Error in Client Thread: "+e);
        }
    }
}