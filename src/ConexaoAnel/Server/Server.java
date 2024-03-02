package ConexaoAnel.Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ConexaoAnel.Handler.ClientHandler;
import ConexaoAnel.Handler.ServerHandler;

public class Server {
    public DatagramSocket socketServer;
    public DatagramSocket pacoteRcv;
    public DatagramSocket pacoteSnd;

    // public Server(int porta){
    //     this.porta = porta;
    //     this.run();
    // }

    private void run(){
        try {
            socketServer = new DatagramSocket(12345);

            System.out.println("Começou a brincadeira");

            byte[] receiveData = new byte[1024];

            while(true){

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            socketServer.receive(receivePacket);

            ServerHandler handler = new ServerHandler(socketServer, receivePacket);

            Thread t1 = new Thread(handler);
            t1.start();
            // Socket client = socketServer.accept();
            // ClientHandler handler = new ClientHandler(client);
            // conexoes.add(handler);
            // pool.execute(handler);
            }
        } catch (Exception e) {
            System.out.println(e);
            //shutdown();
        }
    }

    

    
    

}
