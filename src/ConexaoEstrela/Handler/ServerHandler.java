package ConexaoEstrela.Handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerHandler implements Runnable {
    private DatagramSocket serverSocket;
    private DatagramPacket receivePacket;

    public ServerHandler(DatagramSocket serverSocket, DatagramPacket receivePacket) {
        this.serverSocket = serverSocket;
        this.receivePacket = receivePacket;
    }

    @Override
    public void run() {
        String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("Received message: " + message);

        int port = serverSocket.getLocalPort();

        // Formatando e enviando a mensagem
        String[] parts = message.split("-");
        int value = Integer.parseInt(parts[1]);
        String newMessage = "" + value;
        byte[] sendData = newMessage.getBytes();

        // broadcast
        for (int i = 1; i <= 4; i++) {
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), port + i);
                serverSocket.send(sendPacket);
                System.out.println("Sent message to process " + i + ": " + newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        //unicast
        System.out.println("----------------------------------");
        String received = new String(receivePacket.getData(), 0, receivePacket.getLength());
        int processId1 = Integer.parseInt(received.split("-")[0]);
        int dest = Integer.parseInt(received.split("-")[2]); 

        String log1 = dest + " Received " + received + " from " + processId1;
        System.out.println(log1);
    }
}
