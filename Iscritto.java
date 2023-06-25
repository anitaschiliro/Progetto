import java.io.Serializable;

public class Iscritto implements Serializable {
    private String name;
    private String surname;
    private int age;
    private String corso;

    private boolean pagamento;

    public Iscritto(String name, String surname, int age,String corso) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.corso=corso;
        this.pagamento=true;
    }

    public boolean isPagamento() {
        return pagamento;
    }

    public void setPagamento(boolean pagamento) {
        this.pagamento = pagamento;
    }

    public String getName() {
        return name;
    }

    public String getCorso() {
        return corso;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Iscritto{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }
}

