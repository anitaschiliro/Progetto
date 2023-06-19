import java.io.Serializable;
import java.util.LinkedList;

public class Corso implements Serializable {
    private final String nome;
    private String istruttore;

    private int numIscritti;
    private LinkedList<Iscritto> ListaIscritti = new LinkedList<>();

    public Corso(String nome, String istruttore) {
        this.nome = nome;
        this.istruttore = istruttore;
        this.numIscritti=0;
    }

    public String getNome() {
        return nome;
    }

    public String getIstruttore() {
        return istruttore;
    }

    public void addIscritto(){

    }
    @Override
    public String toString() {
        String output="Corso{" +
                "nome='" + nome + '\'' +
                ", istruttore='" + istruttore + '\''+
                "Iscritti{\n";

        for(Iscritto i: ListaIscritti)
            output+=i.getName()+" "+i.getSurname()+"\n";
        return output+"}";
    }
}
