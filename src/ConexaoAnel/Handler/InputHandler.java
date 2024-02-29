package ConexaoAnel.Handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import ConexaoAnel.Client.Client;
import ConexaoAnel.Server.Server;

public class InputHandler implements Runnable{

    public Server server;
    public Client cliente;

    @Override
    public void run(){
        try {
            BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
            while(!server.feito){
                String mensagem = leitor.readLine();
                if (mensagem.equals("fim")) {
                    leitor.close();
                    server.shutdown();
                }
                else{
                    cliente.saida.close();
                }
            }
        } catch (Exception e) {
            server.shutdown();
        }
    }
}
