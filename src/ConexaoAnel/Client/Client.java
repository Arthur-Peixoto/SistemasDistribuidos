package ConexaoAnel.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Client {

    String ip;
    int porta;

    // public PrintWriter saida;
    // public BufferedReader entrada;
    // public Boolean conexao = true;

    private static Map<Integer, String> logs = new HashMap<>();

    private static DatagramSocket socket;

    public Client(String ip, int porta){
        this.ip = ip;
        this.porta = porta;
    }

    public void run(String ip, int porta){
        try{
            socket = new DatagramSocket();


            System.out.println("Cuidaa...");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Digite o id do processo: (Entre 1, 2, 3 e 4)");
            int processo = scanner.nextInt();
            
            System.out.println("Digite o id do processo que deve receber a mensagem: (Entre 1, 2, 3 e 4)");
            int praOndeVai = scanner.nextInt();

            System.out.println("Digite o número que deve ser enviado");
            int oQueVai = scanner.nextInt();
            
            String mensagem = processo + "-" + oQueVai + "-" + praOndeVai;
            byte[] dados = mensagem.getBytes();

            InetAddress endereco = InetAddress.getByName(ip);

            DatagramPacket packet = new DatagramPacket(dados, dados.length, endereco, porta);

            Thread t1 = new Thread(() ->{
                try {
                    socket.send(packet);
                    String log = processo + " sent " + mensagem + " to " + getProximoIdProcesso(processo);
                    System.out.println(log);
                } catch (SocketException e) {
                    System.out.println(e);
                } catch (IOException e){
                    System.out.println(e);
                }
            });
            t1.start();

            byte[] buffer = new byte[1024];
            DatagramPacket packet2 = new DatagramPacket(buffer, buffer.length);

            Thread t2 = new Thread(() ->{
                try {
                    socket.receive(packet);

                    String received = new String(packet2.getData(), 0, packet2.getLength());
                    int processo1 = Integer.parseInt(received.split("-")[0]);
                    int num = Integer.parseInt(received.split("-")[1]);

                    String log1 = "Received " + received + " from " + geIdProcessoAnterior(processo);
                    System.out.println(log1);
                    logs.put(processo, logs.getOrDefault(processo1, "") + log1 + "\n");

                    socket.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            });
            t2.start();

            // Socket socket = new Socket(ip, porta);
            // saida = new PrintWriter(socket.getOutputStream(), true);
            // entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // InputHandler inputhandler = new InputHandler();
            // Thread t = new Thread(inputhandler);
            // t.start();
            scanner.close();

        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    private static int getProximoIdProcesso(int idAtual){
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

    private static int geIdProcessoAnterior(int idAtual){
        switch (idAtual) {
            case 1:
                return 4;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            default:
                return -1;
        }
    }

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int porta = 4096;
        Client cliente = new Client(ip, porta);
        cliente.run(ip, porta);
    }
}
