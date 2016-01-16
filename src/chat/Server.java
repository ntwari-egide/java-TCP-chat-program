package chat;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws Exception{
        ServerSocket serversocket = new ServerSocket(1234);
        System.out.println(serversocket.isClosed());
        
        Socket clientsocket[] = new Socket[4];
        BufferedReader inFromClient[] = new BufferedReader[4];
        DataOutputStream outToClient[] = new DataOutputStream[4];
        
        for(int i=0; i<4; i++){
            System.out.println("Waiting...");
            clientsocket[i] = serversocket.accept();
            System.out.println("connected "+i);
            
            inFromClient[i] = new BufferedReader(new InputStreamReader(clientsocket[i].getInputStream()));
            outToClient[i] = new DataOutputStream(clientsocket[i].getOutputStream());
        }
        
        SThread thread[] = new SThread[4];
        for(int i=0; i<4; i++){
            thread[i] = new SThread(inFromClient[i], outToClient, i);
        }
        for(int i=0; i<4; i++){
            thread[i].join();
        }
    }
}

class SThread extends Thread{
    BufferedReader inFromClient;
    DataOutputStream outToClient[];
    int srcId;
    String input;
    
    public SThread(BufferedReader in, DataOutputStream out[], int id){
        inFromClient = in;
        outToClient = out;
        srcId = id;
        start();
    }
    
    @Override
    public void run(){
        try{
            while(true){
                input = inFromClient.readLine();
                System.out.println("From client " + srcId + ": " + input);
                
                for(int i=0; i<4; i++){
                    if(i!=srcId) outToClient[i].writeBytes(input + "%id%" + srcId + '\n');
                }
            }
        }catch(Exception e){
            System.out.println("Error in Server Thread: "+e);
        }
    }
}
