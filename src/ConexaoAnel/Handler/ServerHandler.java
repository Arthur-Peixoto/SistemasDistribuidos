package ConexaoAnel.Handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerHandler implements Runnable{

    private DatagramSocket serverSocket;
    private DatagramPacket packet;

    public ServerHandler(DatagramSocket serverSocket, DatagramPacket packetReceive){
        this.serverSocket = serverSocket;
        this.packet = packetReceive;
    }

    @Override
        public void run() {

            int porta = serverSocket.getLocalPort();

            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received message: " + message);

            // Formatando e enviando a mensagem
            String[] parts = message.split("-");
            int value = Integer.parseInt(parts[1]);
            String newMessage = "" + value;
            byte[] sendData = newMessage.getBytes();

            // broadcast
            for (int i = 1; i <= 4; i++) {
                try {
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), porta + i);
                    serverSocket.send(sendPacket);
                    System.out.println("Sent message to process " + i + ": " + newMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Unicast
            String received = new String(packet.getData(), 0, packet.getLength());
            int processId1 = Integer.parseInt(received.split("-")[0]);
            int dest = Integer.parseInt(received.split("-")[2]);

            if (dest != processId1) {
                int nextProcessId = getProximoIdProcesso(processId1);
                sendToProcess(sendData, packet.getAddress(), nextProcessId);
                System.out.println("Passando mensagem do processo " + processId1 + " para o processo " + nextProcessId + ": " + newMessage);
            } else {
                System.out.println("Recebeu a mensagem: " + newMessage);
            }
        }

    private static void sendToProcess(byte[] sendData, InetAddress address, int processId) {
        int port = 12345; // Assumindo que a porta sempre será a mesma
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port + processId);

        Thread sendThread = new Thread(() -> {
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sendThread.start();
    }

    private static int getProximoIdProcesso(int idAtual) {
        switch (idAtual) {
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 1;
            default:
                return -1;
        }
    }
}
