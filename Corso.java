import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Corso implements Serializable {
    private final String nome;
    private String istruttore;
    private float costo;
    private int numIscritti;
    private LinkedList<Iscritto> ListaIscritti = new LinkedList<>();
    private HashMap<LocalDateTime,Prenotazioni> calendario = new HashMap<>();
    public Corso(String nome, String istruttore,float costo) {
        this.nome = nome;
        this.istruttore = istruttore;
        this.numIscritti=0;
        this.costo=costo;
        //inizializzaCalendario();

    }

//    public void inizializzaCalendario(){
//        LocalDateTime today = LocalDateTime.now();
//        LocalDateTime newDate= today.of(LocalDate.of(today.getYear(),today.getMonth(),today.getDayOfMonth()), LocalTime.of(8,0));
//
//            while (today.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
//                if (today.getHour() == 20) {
//                    newDate = today.of(LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth()), LocalTime.of(8, 0));
//                    calendario.put(newDate.plusDays(1), new Prenotazioni(this.nome));
//                } else
//                    calendario.put(newDate.plusHours(2), new Prenotazioni(this.nome));
//            }
//    }

    public void stampaCalendario(){
        System.out.println(calendario);
    }

    public HashMap<LocalDateTime,Prenotazioni> getCalendario(){
        var hash_copy = new HashMap<LocalDateTime,Prenotazioni>(calendario) ;
        return hash_copy;
    }

    public void addPrenotazione(Iscritto i,LocalDateTime data){
        Prenotazioni p =calendario.get(data);
        p.addPrenotato(i);
        System.out.println("Numero prenotati: "+p.getNumPrenotati());
    }

    public String getNome() {
        return nome;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public String getIstruttore() {
        return istruttore;
    }

    public int getNumIscritti() {
        return numIscritti;
    }

    public void setNumIscritti() {
        this.numIscritti+=1;
    }

    public void addIscritto(Iscritto i){
        ListaIscritti.add(i);
        setNumIscritti();
    }

    public LinkedList<Iscritto> getListaIscritti(){
        var list_copy = new LinkedList<Iscritto>(ListaIscritti);
        return list_copy;
    }
    @Override
    public String toString() {
        StringBuilder output= new StringBuilder("Corso{" +
                "nome='" + nome + '\'' +
                ", istruttore='" + istruttore + '\'' +
                "Iscritti{\n");

        for(Iscritto i: ListaIscritti) {
            output.append(i.toString());
        }
        return output+"}";
    }
}
