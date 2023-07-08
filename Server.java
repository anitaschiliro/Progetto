import java.io.*;
import java.net.ServerSocket;
import java.time.LocalDateTime;
import java.util.*;

public class Server {
    private LinkedList<Corso> listaCorsi= new LinkedList<>();
    public LinkedList<Iscritto> listaDebitori= new LinkedList<>();
    public synchronized void commandAddCorso(Corso c){
        listaCorsi.add(c);
    }

    public synchronized void commandAddIscritto(Iscritto i){
        for(Corso c: listaCorsi)
            if(c.getNome().equals(i.getCorso())){
                c.addIscritto(i);
                System.out.println(i);//debug
                break;
            }

    }
    public synchronized LinkedList<String> getListString(){
        //second solution
        LinkedList<String> corsi= new LinkedList<>();
        for (Corso c: listaCorsi){
            corsi.add(c.toString());
            System.out.println(c.toString()); //debug
        }
        return corsi;
    }

    public synchronized LinkedList<Corso> getListaCorsi(){
        var lista = new LinkedList<Corso>(listaCorsi);
        return lista;
    }

    public synchronized LinkedList<Iscritto> getListaDebitori(){
        var lista = new LinkedList<Iscritto>(listaDebitori);
        return lista;
    }
    public synchronized HashMap<String,Prenotazioni> getCalendario(String corso){
        var calendario= new HashMap<String,Prenotazioni>();
        for(Corso c : listaCorsi)
            if(c.getNome().equals(corso)) {
                calendario=c.getCalendario();
                break;
            }

        return calendario;
    }

    public synchronized HashMap<String,Prenotazioni> getCalendarioPrenotabili(String corso){
        var calendario= new HashMap<String,Prenotazioni>();
        for(Corso c : listaCorsi)
            if(c.getNome().equals(corso)) {
                calendario=c.getCalendarioPrenotabili();
                break;
            }
        //System.out.println(calendario);
        return calendario;
    }

    public synchronized float calcolaGuadagni(){
        var somma=0;
        for(Corso c: listaCorsi)
            somma+=c.getCosto()*(c.getNumIscritti()-c.getNumDebitori());
        return somma;
    }
    public synchronized void commandLoadListCorsi(String nome1,String nome2){
        try {
            var fin= new FileInputStream(nome1);
            var ois = new ObjectInputStream(fin);

            listaCorsi= (LinkedList<Corso>)ois.readObject();
            for(Corso c: listaCorsi){
                c.inizializzaListaIscritti();
                c.inizializzaCalendario();
            }
            var listaTmp= new LinkedList<Iscritto>();
            var fin1= new FileInputStream(nome2);
            var ois1 = new ObjectInputStream(fin1);
            listaTmp= (LinkedList<Iscritto>)ois1.readObject();

            for(Corso c: listaCorsi)
                for(Iscritto i: listaTmp){
                    if(i.getCorso().equals(c.getNome())){
                        c.addIscritto(i);
                    }
                }

            ois.close();
            ois1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println("Impossibile caricare la lista");
            e.printStackTrace();
        }
    }
    public synchronized void commandSaveList(String file1, String file2){

        try {
            var fos1 =new FileOutputStream(file1);
            var oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(listaCorsi);

            var fos2= new FileOutputStream(file2);
            var oos2= new ObjectOutputStream(fos2);


            for(Corso c: listaCorsi){
                oos2.writeObject(c.getListaIscritti());
            }

            oos2.close();
            oos1.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void commandAddPrenotazione(String nome, String cognome, String corso, String data, String ora) {

        String data_converted="Date: "+data+" Hour: "+ora+":00:00";
        for(Corso c:listaCorsi){
            if(c.getNome().equals(corso)){
                for(Iscritto i: c.getListaIscritti())
                    if(i.getName().equals(nome) && i.getSurname().equals(cognome)) {
                        System.out.println(i);
                        System.out.println(data_converted);
                        c.addPrenotazione(i, data_converted);
                    }
            }
        }
    }

    public synchronized void commandSetPagamento(String nome, String cognome, String corso, String pagamento){
        for(Corso c: listaCorsi){
            if(c.getNome().equals(corso))
                c.setPagamento(nome,cognome,pagamento);
        }
    }
    public static void main(String[] args) {

        var my_server=new Server();

        if(args.length!=1){
            System.out.println("Numero errato di argomenti");
            System.exit(-1);
        }

        int port= Integer.parseInt(args[0]);

        Runnable r = ()-> my_server.calcolaGuadagni();
        new Thread(r).start();


        new Thread(
                ()-> {
                    while (true) {
                        try {
                            Thread.sleep(100000);
                            my_server.commandSaveList("periodic_save_corso_"+new Date().toString(),
                                    "periodic_save_iscritto_"+new Date().toString());
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ).start();

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("SERVER: Attendo connessioni...");
                var client = serverSocket.accept();
                System.out.println("SERVER: Connessione accettata da " + client.getRemoteSocketAddress());

                var ClientManager = new ClientManager(client, my_server);
                new Thread(ClientManager).start();

                var verificaPagamenti = new verificaPagamentiPeriodica(my_server);
                new Thread(verificaPagamenti).start();

            }
        } catch (IOException e) {
            System.out.println("Qualcosa è andato storto!");
        }
    }
}
