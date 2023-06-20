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

                    case "LOAD_CORSI_ISCRITTI":
                        System.out.println("Caricamento corsi...");
                        var corsi=sc.nextLine();

                        System.out.println("Caricamento iscritti...");
                        var iscritti=sc.nextLine();

                        end_cmd= sc.nextLine();
                        if(!end_cmd.equals("END_CMD")){
                            System.out.println("Format error");
                        }
                        my_server.commandLoadListCorsi(corsi,iscritti);//synchronized
                        break;
                    case "MOSTRA_CORSI":

                        for(String s: my_server.getListString()){
                            pw.println(s);
                            pw.flush();
                        }

                        pw.println("END_DATA");
                        pw.flush();
                        end_cmd= sc.nextLine();
                        if(!end_cmd.equals("END_CMD")){
                            System.out.println("Format error");
                        }
                        break;
                    case "SALVA_CORSI_ISCRITTI":
                        System.out.println("Salvo la lista dei corsi..");
                        corsi=sc.nextLine();
                        System.out.println("Salvo la lista degli iscritti..");
                        iscritti=sc.nextLine();
                        my_server.commandSaveList(corsi,iscritti);
                        end_cmd= sc.nextLine();
                        if(!end_cmd.equals("END_CMD")){
                            System.out.println("Format error");
                        }
                        break;
                    case "ADD_ISCRITTO":

                        nome = sc.nextLine();
                        var cognome = sc.nextLine();
                        var eta= Integer.parseInt(sc.nextLine());
                        String nome_corso= sc.nextLine();

                        end_cmd= sc.nextLine();
                        if(!end_cmd.equals("END_CMD")){
                            System.out.println("Format error");
                        }
                        System.out.println("Aggiungo iscritto: "+nome + " "+ cognome+" "+eta+" "+nome_corso);
                        var iscritto = new Iscritto(nome,cognome,eta,nome_corso);
                        my_server.commandAddIscritto(iscritto);

                        break;
                    case "CMD_QUIT":
                        System.out.println("Chiudo la connessione...");
                        break;
                    default:
                        if(!cmd.isBlank())
                            System.out.println("Comando sconosciuto!");
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
