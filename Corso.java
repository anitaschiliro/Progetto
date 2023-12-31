import java.io.Serializable;
import java.util.*;

public class Corso implements Serializable {
    private final String nome;
    private String istruttore;
    private float costo;
    private int numIscritti;
    transient LinkedList<Iscritto> ListaIscritti;
    transient HashMap<String,Prenotazioni> calendario;

    public Corso(String nome, String istruttore,float costo) {
        this.nome = nome;
        this.istruttore = istruttore;
        this.numIscritti=0;
        this.costo=costo;
        inizializzaListaIscritti();
        inizializzaCalendario();

    }

    public void inizializzaListaIscritti(){
        ListaIscritti=new LinkedList<>();
    }

    public void inizializzaCalendario(){
        calendario=new HashMap<>();
        Calendar tmp= Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        tmp.add(Calendar.DAY_OF_MONTH,1);
        tmp.set(Calendar.HOUR_OF_DAY,8);
        tmp.set(Calendar.MINUTE,0);
        tmp.set(Calendar.SECOND,0);
        int year = tmp.get(Calendar.YEAR);
        int month = tmp.get(Calendar.MONTH);
        int dayOfMonth = tmp.get(Calendar.DAY_OF_MONTH);
        int hour=tmp.get(Calendar.HOUR_OF_DAY);
        int min=tmp.get(Calendar.MINUTE);
        int sec= tmp.get(Calendar.SECOND);
        System.out.println("Date: "+dayOfMonth+"-"+month+"-"+ year+" Hour: "+hour+":00:00");
        calendario.put("Date: "+dayOfMonth+"-"+month+"-"+ year+" Hour: "+hour+":00:00",new Prenotazioni(this.nome));
        while(!(tmp.get(Calendar.DAY_OF_MONTH) == tmp.getActualMaximum(Calendar.DAY_OF_MONTH))){
            if(tmp.get(Calendar.HOUR_OF_DAY)==20){
                tmp.add(Calendar.DAY_OF_MONTH,1);
                tmp.set(Calendar.HOUR_OF_DAY,8);
                tmp.set(Calendar.MINUTE,0);
                tmp.set(Calendar.SECOND,0);
                year = tmp.get(Calendar.YEAR);
                month = tmp.get(Calendar.MONTH);
                dayOfMonth = tmp.get(Calendar.DAY_OF_MONTH);
                hour=tmp.get(Calendar.HOUR_OF_DAY);
                min=tmp.get(Calendar.MINUTE);
                sec= tmp.get(Calendar.SECOND);
                System.out.println("Date: "+dayOfMonth+"-"+month+"-"+ year+" Hour: "+hour+":00:00");
                calendario.put("Date: "+dayOfMonth+"-"+month+"-"+ year+" Hour: "+hour+":00:00",new Prenotazioni(this.nome));
            }
            else{
                tmp.add(Calendar.HOUR_OF_DAY,2);
                year = tmp.get(Calendar.YEAR);
                month = tmp.get(Calendar.MONTH);
                dayOfMonth = tmp.get(Calendar.DAY_OF_MONTH);
                hour=tmp.get(Calendar.HOUR_OF_DAY);
                min=tmp.get(Calendar.MINUTE);
                sec= tmp.get(Calendar.SECOND);
                System.out.println("Date: "+dayOfMonth+"-"+month+"-"+ year+" Hour: "+hour+":00:00");
                calendario.put("Date: "+dayOfMonth+"-"+month+"-"+ year+" Hour: "+hour+":00:00",new Prenotazioni(this.nome));
            }
        }
    }

    public void stampaCalendario(){
        System.out.println(calendario);
    }

    public void setPagamento(String nome, String cognome,String value){
        for(Iscritto i: ListaIscritti){
            if(i.getName().equals(nome) && i.getSurname().equals(cognome)){
                if(value.equals("Pagato"))
                    i.setPagamento(true);
                if(value.equals("Da pagare"))
                    i.setPagamento(false);
            }
        }
    }
    public HashMap<String,Prenotazioni> getCalendario(){
        var hash_copy = new HashMap<String,Prenotazioni>(calendario);
        return hash_copy;
    }

    public HashMap<String,Prenotazioni> getCalendarioPrenotabili(){
        var hash_copy = new HashMap<String,Prenotazioni>();
        for (Map.Entry<String,Prenotazioni> entry : calendario.entrySet()){
            var key=entry.getKey();
            var value=entry.getValue();
            if(!value.isFull()){
                hash_copy.put(key,value);
            }
        }
        return hash_copy;
    }
    public void addPrenotazione(Iscritto i,String data){
            //System.out.println(getCalendarioPrenotabili());
            if (getCalendarioPrenotabili().containsKey(data)) {
                Prenotazioni p = getCalendarioPrenotabili().get(data);
                System.out.println(p.isFull());
                if (!p.isFull()) {
                    p.addPrenotato(i);
                    calendario.put(data, p);
                    System.out.println("Numero prenotati: " + p.getNumPrenotati());
                } else {
                    System.out.println("Posti non disponibili");
                }
            } else {
                System.out.println("Chiave non presente\n");
            }
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

    public int getNumDebitori(){
        int somma=0;
        for(Iscritto i: getListaIscritti()){
            if(!i.isPagamento()){
                somma+=1;
            }
        }
        return somma;
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
                "Iscritti{\n\t");

        for(Iscritto i: ListaIscritti) {
            output.append(i.toString());
            output.append("\n\t");
        }
        return output+"}";
    }
}
