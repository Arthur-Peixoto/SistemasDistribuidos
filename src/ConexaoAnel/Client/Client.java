package ConexaoAnel.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import ConexaoAnel.Handler.InputHandler;


public class Client {

    String ip;
    int porta;
    public PrintWriter saida;
    public BufferedReader entrada;
    public Boolean conexao = true;

    public Client(String ip, int porta){
        this.ip = ip;
        this.porta = porta;
    }

    public void run(){
        try{
        Scanner scanner = new Scanner(System.in);
        Socket socket = new Socket(ip, porta);
        saida = new PrintWriter(socket.getOutputStream(), true);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        InputHandler inputhandler = new InputHandler();
        Thread t = new Thread(inputhandler);
        t.start();
        String mensagem;

        while(conexao){
            System.out.println("Digite sua mensagem: ");
            mensagem = scanner.nextLine();
            if(mensagem.equals("fim")){
                conexao = false;
            }
            else{
                System.out.println(mensagem);
            }
            saida.println(mensagem);
            }
            saida.close();
            scanner.close();
            socket.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        int porta = 4096;
        Client cliente = new Client(ip, porta);
        cliente.run();
    }
}
