import java.io.Serializable;
import java.util.LinkedList;

public class Prenotazioni implements Serializable {
    //private LocalDateTime data;
    private String corso;
    private LinkedList<Iscritto> prenotati;
    private int numPrenotati;

    public Prenotazioni(String corso) {
        //this.data = data;
        this.corso = corso;
        this.numPrenotati=0;
        prenotati = new LinkedList<>();
    }

    public String getCorso() {
        return corso;
    }

    public void setCorso(String corso) {
        this.corso = corso;
    }

    public void addPrenotato(Iscritto i){
        System.out.println(i+"dentro prenotazioni");
        prenotati.add(i);
        setNumPrenotati();
    }

    public int getNumPrenotati() {
        return numPrenotati;
    }

    public void setNumPrenotati() {
        this.numPrenotati+=1;
    }

    public boolean isFull(){
        if(getNumPrenotati()>=2)
            return true;
        else return false;
    }
    @Override
    public String toString() {
        String output= "Prenotazioni{" +
                ", corso='" + corso + '\'' +
                ", numPrenotati=" + numPrenotati+"\n\t"+
                "Prenotati{\n";

        for(Iscritto i: prenotati){
         output+=i.getName()+" "+i.getSurname();
        }
        return output+="\n}";
    }
}
