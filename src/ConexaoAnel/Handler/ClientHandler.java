package ConexaoAnel.Handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import ConexaoAnel.Server.Server;

public class ClientHandler implements Runnable {

    private Socket cliente;
    private Server server;
    private PrintWriter escritor;
    private BufferedReader leitor;
    private String nickname;

    public ClientHandler(Socket cliente){
        this.cliente = cliente;
    }
    
    @Override
    public void run(){  
        try{
            escritor = new PrintWriter(cliente.getOutputStream());
            leitor = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            escritor.println("Escreva um apelido:");
            nickname = leitor.readLine();
            System.out.println(nickname + "conectado!");
            server.broadcast(nickname + "entrou na brincadeira");
            String mensagem;
            while((mensagem = leitor.readLine()) != null){
                if(mensagem.startsWith("fim")){
                    server.broadcast(nickname + "saiu do chat");
                    shutdown();
                }
                else{
                    server.broadcast(nickname + "mensagem");
                }
            }
        }catch(Exception e){
            System.out.println(e);
            shutdown();
        }
    }

    public void sendMessage(String mensagem){
        escritor.println(mensagem);
    }

    public void shutdown(){
        try{
        escritor.close();
        leitor.close();
            if(!cliente.isClosed()){
                cliente.close();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
