public class ClasseToken {

    private String nome;
    public final static String IDENTIFICADOR = "Identificador";
    public final static String PALAVRA_RESERVADA = "Palavra reservada";
    public final static String SIMBOLO = "Símbolo";
    public final static String LITERAL = "Constante Literal";
    public final static String CONSTANTE_NUMERICA = "Constante numérica";

    public ClasseToken(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}
