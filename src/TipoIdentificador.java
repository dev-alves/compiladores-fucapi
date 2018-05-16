public class TipoIdentificador {

    private String nome;
    public final static String PALAVRA_RESERVADA = "Palavra reservada";
    public final static String IDENTIFICADOR = "Identificador";

    public TipoIdentificador(String nome) {
        this.nome = nome;
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
