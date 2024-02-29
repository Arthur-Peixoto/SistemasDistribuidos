package ConexaoAnel.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ConexaoAnel.Handler.ClientHandler;

public class Server {
    public ServerSocket socketServer;
    private int porta;
    private Socket cliente;
    private ArrayList<ClientHandler> conexoes;
    public boolean feito = false;
    private ExecutorService pool;

    public Server(int porta){
        this.conexoes = new ArrayList<>();
        this.porta = porta;
        this.run();
    }

    private void run(){
        try {
            socketServer = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            while(!feito){
            Socket client = socketServer.accept();
            ClientHandler handler = new ClientHandler(client);
            conexoes.add(handler);
            pool.execute(handler);
            }
        } catch (Exception e) {
            System.out.println(e);
            shutdown();
        }
    }

    public void broadcast(String mensagem){
        for(ClientHandler conexao : conexoes){
            if(conexao != null){
                conexao.sendMessage(mensagem);
            }
        }
    }

    public void shutdown(){
     try{
        feito = true;
        if(!socketServer.isClosed()){
            socketServer.close();
        }
        for(ClientHandler conexao : conexoes){
            conexao.shutdown();
        }
     }
        catch(Exception e){
        System.out.println(e);
     }
    }
    

}
