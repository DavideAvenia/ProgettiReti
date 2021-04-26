import java.io.Serializable;

public class Cliente implements Serializable {

    private String idCliente;
    private String nome;
    private String cognome;

    public Cliente(){

    }

    public Cliente(String idCliente, String nome, String cognome){
        this.idCliente = idCliente;
        this.nome = nome;
        this.cognome = cognome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }
}