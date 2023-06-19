import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientManager implements Runnable{
    Socket client;
    Server my_server;

    public ClientManager(Socket client,Server my_server) {
        System.out.println("Nuovo client manager creato!");
        this.client = client;
        this.my_server=my_server;
    }

    public void go(){
        Scanner sc=null;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(client.getOutputStream());

            sc= new Scanner(client.getInputStream());

            String cmd="";

            while(!cmd.equals("CMD_QUIT")){
                cmd=sc.nextLine();

                System.out.println("Comando ricevuto: "+cmd);
                switch (cmd){
                    case "ADD_CORSO":

                        var nome = sc.nextLine();
                        var istruttore = sc.nextLine();

                        var end_cmd= sc.nextLine();
                        if(!end_cmd.equals("END_CMD")){
                            System.out.println("Format error");
                        }
                        System.out.println("Aggiungo corso: "+nome + " "+ istruttore);
                        var corso = new Corso(nome,istruttore);
                        my_server.commandAddCorso(corso);

                        break;

                    case "CMD_QUIT":
                        System.out.println("Closing connection...");
                        break;
                    default:
                        if(!cmd.isBlank())
                            System.out.println("Unknown command");
                        break;
                }
            }
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        System.out.println("Starting the thread! ");
        go();
    }
}
