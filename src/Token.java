public class Token {

    private ClasseToken classeToken;
    private String valor;
    private int linha;
    private int coluna;

    public Token(ClasseToken tipo, String valor, int linha, int coluna) {
        this.classeToken = tipo;
        this.valor = valor;
        this.linha = linha;
        this.coluna = coluna;
    }

    public ClasseToken getClasseToken() {
        return classeToken;
    }

    public void setClasseToken(ClasseToken classeToken) {
        this.classeToken = classeToken;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    @Override
    public String toString() {
        String tp = classeToken != null ? classeToken.getNome() : "Não informado";
        return ("Tipo:".concat(tp).concat("\nValor:"+valor+"\n"));
    }
    
}
