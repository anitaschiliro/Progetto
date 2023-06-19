import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        if(args.length!=2){
            System.out.println("Wrong number of arguments");
            System.exit(-1);
        }

        String ip=args[0];
        int port=Integer.parseInt(args[1]);

        try {
            Socket socket= new Socket(ip,port);
            System.out.println("Connected!");

            var os= socket.getOutputStream();
            var is = socket.getInputStream();

            var pw = new PrintWriter(os);
            var sc= new Scanner(is);

            String choice=" ";
            var input = new Scanner(System.in);

            while(!choice.equals("q")){
                System.out.println("***** PALESTRA FITNESS WORLD *****");
                System.out.println("------------------------");
                System.out.println("1 - Aggiungi corso");
                System.out.println("2 - Mostra corsi");
                System.out.println("3 - Salva lista corsi");
                System.out.println("4 - Carica lista corsi");
                System.out.println("5 - Aggiungi iscritto");
                System.out.println("6 - Esci");
                System.out.println("-------------------------");
                System.out.println("Inserisci la tua scelta: ");

                choice=input.nextLine();

                switch (choice){
                    case "1":
                        System.out.print("Inserisci nome corso: ");
                        var nome = input.nextLine();
                        System.out.print("Inserisci nome istruttore: ");
                        var istruttore = input.nextLine();

                        pw.println("ADD_CORSO");
                        pw.flush();
                        pw.println(nome);
                        pw.flush();
                        pw.println(istruttore);

                        pw.flush();

                        pw.println("END_CMD");
                        pw.flush();
                        break;

                    case "6":
                        pw.println("CMD_QUIT");
                        pw.flush();
                        System.out.println("Uscita..");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Scelta non valida");
                }

            }


        } catch (IOException e) {
            System.out.println("Qualcosa è andato storto!");
        }
    }
}
