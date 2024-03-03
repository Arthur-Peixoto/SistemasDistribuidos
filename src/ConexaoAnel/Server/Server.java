package ConexaoAnel.Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

            //Quando recebe um pacote, ele cria um objeto ServerHandler
            // para lidar com o processamento da solicitação.

            Thread t1 = new Thread(handler);
            t1.start();

            //O servidor executa o ServerHandler em uma nova thread para 
            //lidar com múltiplas solicitações simultaneamente.

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    

}
