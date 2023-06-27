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
                System.out.println("3 - Salva liste corsi e iscritti");
                System.out.println("4 - Carica liste corsi e iscritti");
                System.out.println("5 - Aggiungi iscritto");
                System.out.println("6 - Aggiungi prenotazione");
                System.out.println("7 - Mostra calendario prenotazioni");
                System.out.println("8 - Mostra lista debitori");
                System.out.println("9 - Setta pagamento");
                System.out.println("10 - Esci");
                System.out.println("-------------------------");
                System.out.println("Inserisci la tua scelta: ");

                choice=input.nextLine();

                switch (choice){
                    case "1":
                        System.out.print("Inserisci nome corso: ");
                        var nome = input.nextLine();
                        System.out.print("Inserisci nome istruttore: ");
                        var istruttore = input.nextLine();
                        System.out.print("Inserisci costo: ");
                        var costo = input.nextLine();
                        pw.println("ADD_CORSO");
                        pw.flush();
                        pw.println(nome);
                        pw.flush();
                        pw.println(istruttore);
                        pw.flush();
                        pw.println(costo);
                        pw.flush();

                        pw.println("END_CMD");
                        pw.flush();
                        break;
                    case "2":
                        pw.println("MOSTRA_CORSI");
                        pw.flush();
                        boolean continue_list=true;
                        System.out.println("-----------------------");
                        System.out.println("Lista dei corsi");
                        System.out.println("-----------------------");
                        while(continue_list){
                            String line=sc.nextLine();
                            if(line.equals("END_DATA"))
                                continue_list=false;
                            else{
                                System.out.println(line);
                                System.out.println("------------------------");
                            }

                        }
                        System.out.println("*************************");
                        pw.println("END_CMD");
                        pw.flush();
                        break;
                    case "3"://salva sia corsi che iscritti
                        System.out.println("In che file vuoi salvare i corsi? ");
                        var nome1 = input.nextLine();
                        System.out.println("In che file vuoi salvare gli iscritti? ");
                        var nome2 = input.nextLine();
                        pw.println("SALVA_CORSI_ISCRITTI");
                        pw.flush();
                        pw.println(nome1);
                        pw.flush();
                        pw.println(nome2);
                        pw.flush();
                        pw.println("END_CMD");
                        break;

                    case "4":
                        System.out.println("Inserisci file corsi: ");
                        nome1 = input.nextLine();
                        System.out.println("Inserisci file iscritti: ");
                        nome2= input.nextLine();
                        pw.println("LOAD_CORSI_ISCRITTI");
                        pw.flush();
                        pw.println(nome1);
                        pw.flush();
                        pw.println(nome2);
                        pw.flush();
                        pw.println("END_CMD");
                        break;
                    case "5":
                        System.out.print("Inserisci nome: ");
                        nome = input.nextLine();
                        System.out.print("Inserisci cognome: ");
                        var cognome = input.nextLine();
                        System.out.println("Inserisci età: ");
                        var eta= input.nextLine();
                        System.out.println("Inserisci corso: ");
                        var corso=input.nextLine();

                        pw.println("ADD_ISCRITTO");
                        pw.flush();
                        pw.println(nome);
                        pw.flush();
                        pw.println(cognome);
                        pw.flush();
                        pw.println(eta);
                        pw.flush();
                        pw.println(corso);
                        pw.flush();

                        pw.println("END_CMD");
                        pw.flush();
                        break;
                    case "6":
                        System.out.print("Inserisci nome: ");
                        nome = input.nextLine();
                        System.out.print("Inserisci cognome: ");
                        cognome = input.nextLine();
                        System.out.println("Inserisci corso: ");
                        corso=input.nextLine();
                        System.out.println("Inserisci data (DD-MM-AAAA): ");
                        var data = input.nextLine();
                        System.out.println("Inserisci ora (OO):");
                        var ora = input.nextLine();
                        pw.println("ADD_PRENOTAZIONE");
                        pw.flush();
                        pw.println(nome);
                        pw.flush();
                        pw.println(cognome);
                        pw.flush();
                        pw.println(corso);
                        pw.flush();
                        pw.println(data);
                        pw.flush();
                        pw.println(ora);
                        pw.flush();
                        pw.println("END_CMD");
                        pw.flush();
                        break;
                    case "7":
                        pw.println("MOSTRA_CALENDARIO");
                        pw.flush();
                        System.out.println("Inserisci corso: ");
                        corso= input.nextLine();
                        pw.println(corso);
                        pw.flush();
                        continue_list=true;
                        System.out.println("-----------------------");
                        System.out.println("Calendario prenotazioni");
                        System.out.println("-----------------------");
                        while(continue_list){
                            String line=sc.nextLine();
                            if(line.equals("END_DATA"))
                                continue_list=false;
                            else{
                                System.out.println(line);
                                System.out.println("------------------------");
                            }

                        }
                        System.out.println("*************************");
                        pw.println("END_CMD");
                        pw.flush();
                        break;
                    case "8":
                        pw.println("MOSTRA_DEBITORI_GUADAGNI");
                        pw.flush();
                        continue_list=true;
                        System.out.println("-----------------------");
                        System.out.println("Lista debitori");
                        System.out.println("-----------------------");
                        while(continue_list){
                            String line=sc.nextLine();
                            if(line.equals("END_DATA"))
                                continue_list=false;
                            if(line.equals("GUADAGNI")){
                                System.out.println("*******************************");
                                line=sc.nextLine();
                                System.out.println("Guadagno complessivo: "+line);
                                System.out.println("*******************************");
                            }
                            else{
                                System.out.println(line);
                                System.out.println("------------------------");
                            }

                        }
                        System.out.println("*************************");
                        pw.println("END_CMD");
                        pw.flush();
                        break;
                    case "9":
                        System.out.print("Inserisci nome: ");
                        nome = input.nextLine();
                        System.out.print("Inserisci cognome: ");
                        cognome = input.nextLine();
                        System.out.println("Inserisci corso: ");
                        corso=input.nextLine();
                        System.out.println("Inserisci pagamento (scrivi 'Da pagare' o 'Pagato'): ");
                        var pagamento=input.nextLine();

                        pw.println("SET_PAGAMENTO");
                        pw.flush();
                        pw.println(nome);
                        pw.flush();
                        pw.println(cognome);
                        pw.flush();
                        pw.println(corso);
                        pw.flush();
                        pw.println(pagamento);
                        pw.flush();
                        pw.println("END_CMD");
                        pw.flush();
                        break;
                    case "10":
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
