package ConexaoEstrela.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import ConexaoAnel.Handler.ServerHandler;

public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(12345);
        System.out.println("Começou a brincadeira...");

        byte[] receiveData = new byte[1024];

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            ServerHandler handler = new ServerHandler(serverSocket, receivePacket);
            Thread thread = new Thread(handler);
            thread.start();
        }
    }
}
