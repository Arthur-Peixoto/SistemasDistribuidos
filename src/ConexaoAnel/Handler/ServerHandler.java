package ConexaoAnel.Handler;

import java.net.DatagramSocket;

public class ServerHandler {

    private DatagramSocket serverSocket;
    private DatagramSocket packet;

    public ServerHandler(DatagramSocket serverSocket, DatagramSocket packetReceive){
        this.serverSocket = serverSocket;
        this.packet = packetReceive;
    }

    public void run(){
        System.out.println(serverSocket.getLocalAddress() + "cliente com esse endereço entrou pra brincadeira");
    
        if(broadcast){
            broadcast(null);
        }
        else if(unicast){
            unicast(null);
        }
    }
    
    public void broadcast(String mensagem){
        for(ClientHandler conexao : conexoes){
            if(conexao != null){
                conexao.sendMessage(mensagem);
            }
        }
    }

    public void unicast(String mensagem){
        for(ClientHandler conexao : conexoes){
            if(conexao != null){
                conexao.sendMessage(mensagem);
            }
        }
    }
}
