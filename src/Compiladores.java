import java.util.List;

public class Compiladores {

    public static int linhaAtual = 1;

    public static ClasseToken palavraReservada  = new ClasseToken(ClasseToken.PALAVRA_RESERVADA);
    public static ClasseToken identificador = new ClasseToken(ClasseToken.IDENTIFICADOR);

    private static boolean isLetra(char charAt) {
        return "abcdefghijlkmnopqrstuxwz".contains(charAt+"");
    }

    private static boolean isNumero(char charAt) {
        return "0123456789".contains(charAt+"");
    }

    private static int procurarIdentificador(String codigoFonte, int i) {
        int inicio = i;
        while (isLetra(codigoFonte.charAt(i))
                || isNumero(codigoFonte.charAt(i))
                || codigoFonte.charAt(i) == '_') {
            i++;
        }

        String ident = codigoFonte.substring(inicio,i);

        if (PalavraReservada.isPalavraReservada(ident)) {
            TabelaSimbolo.addToken(new Token(palavraReservada, ident, linhaAtual,inicio));
        } else {
            TabelaSimbolo.addToken(new Token(identificador, ident, linhaAtual,inicio));
        }

        return i;
    }
    private static int procurarNumero(String codigoFonte, int i) {
        int inicio = i;
        while (isNumero(codigoFonte.charAt(i))) {
            i++;
        }

        String numero = codigoFonte.substring(inicio,i);
        TabelaSimbolo.addToken(new Token(new ClasseToken(ClasseToken.CONSTANTE_NUMERICA), numero, linhaAtual, inicio));
        return i;
    }

    private static int pularLinha(String codigoFonte, int i) {
        do {
            i++;
        } while (codigoFonte.charAt(i) != '\n');
        return i;
    }

    private static int pularBloco(String codigoFonte, int i) {
        do {
            i++;
        } while (!codigoFonte.substring(i, i+2).equals("*/")
                && i <= codigoFonte.length());
        return i++;
    }

    private static boolean isLiteral(char elem) {
        return elem == '\'' || elem == '"';
    }

    private static int procurarLiteral(String codigoFonte, int i) {
        ClasseToken constanteLiteral = new ClasseToken(ClasseToken.LITERAL);
        // "este é um literal"
        int inicio = i;
        char caracterTerminal = codigoFonte.charAt(i);
        do {
            i++;
        } while (codigoFonte.charAt(i) != caracterTerminal);

        String literal = codigoFonte.substring(inicio,++i);
        TabelaSimbolo.addToken(new Token(constanteLiteral, literal, linhaAtual, inicio));
        return i;
    }

    private static boolean isSimbolo(char elem) {
        String simbolos = "<>=!-+*/%{}[];|&(),";
        return simbolos.contains(elem+"");
    }

    private static int procurarSimbolo(String codigoFonte, int i) {
        ClasseToken simboloClasse = new ClasseToken(ClasseToken.SIMBOLO);
        TabelaSimbolo.addToken(new Token(simboloClasse, codigoFonte.substring(i, i+1), linhaAtual,i));
        return i++;
    }

    public static int lex(String codigoFonte, int i) {

        char elem = codigoFonte.charAt(i);

        if (elem == '\n') {
            linhaAtual++;
        }

        if (elem == '/') {
            char prox = codigoFonte.charAt(i+1);
            if (prox == '/') {
                return pularLinha(codigoFonte,i);
            } else if (prox == '*') {
                return pularBloco(codigoFonte,i);
            }
        } 
        //if (elem == '/' && codigoFonte.charAt(i+1))
        if (isLetra(elem)) {
            i = procurarIdentificador(codigoFonte,i);
            return --i;
        } else if (isNumero(elem)) {
            i = procurarNumero(codigoFonte,i);
            return i;
        } else if (isLiteral(elem)) {
            i = procurarLiteral(codigoFonte,i);
            return i;
        } else if (isSimbolo(elem)) {
            i = procurarSimbolo(codigoFonte,i);
            return i;
        } else {
            return i;
        }
        
    }

    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        sb.append("int teste(){\n");
        sb.append("int p;");
        sb.append("}\n");
        sb.append("int main() {\n");
        sb.append("int x1, x2, x3;\n");
        sb.append("int x;\n");
        sb.append("}\n");
        //sb.append("/*comentario de bloco*/");
        //sb.append("return 0;\n");
        sb.append("int x4;\n");
        sb.append("}\n");
        String codigoFonte = sb.toString();
        //System.out.println(codigoFonte);
        
        for (int i = 0; i < codigoFonte.length(); i++) {
            char elem = codigoFonte.charAt(i);
            //System.out.println(elem);
            int f = lex(codigoFonte,i);
            //System.out.println(codigoFonte.substring(i,f)
            //       +" i="+i+" f="+f);
            i=f;
            
        }
        System.out.println("Qtde de simbolos na tabela:"+TabelaSimbolo.getTokens().size());
        /*for (Object simbolo : TabelaSimbolo.getTokens()) {
            System.out.println(simbolo);
        }*/
        analiseSintatica();
    }

    private static void analiseSintatica() {
        System.out.println("Iniciando Análise Sintática");
        while (NextOrPrevious.getRow()<linhaAtual){
            List<Token> tokensSobAnalise = TabelaSimbolo.getTokensByLinha(NextOrPrevious.getRow());

            NextOrPrevious.setSize(tokensSobAnalise.size());
            NextOrPrevious.resetPosition();

            System.out.println("Tamanho do NP: " + NextOrPrevious.getSize());

            System.out.println("Linha "+NextOrPrevious.getRow()+" # tokens sendo analisados:");
            for (Token t : tokensSobAnalise) {
                System.out.println("Tokens: " + t.toString());
            }

            if ( (dlc(tokensSobAnalise))) {
                System.out.println("Os tokens abaixo compoe uma declaração");
                for (Token t : tokensSobAnalise) {
                    System.out.println(t.toString());
                }
            } else {

                System.out.println("NÃO É DECLARAÇÃO!!!");
                NextOrPrevious.resetPosition();


                System.out.println("Verificando a função");
                if(func(tokensSobAnalise)) {
                    System.out.println("Fim da analise da função");
                } else  {
                    System.out.println("NÃO É UMA FUNÇÃO!");
                }
            }

            NextOrPrevious.nextRow();
        }
    }

    public static boolean dlc(List<Token> tokenList) {

        if(tokenList.size() >= 3) {

            boolean primeiroValido = type(tokenList.get(NextOrPrevious.getPosition()));
            boolean segundoValido = AnaliseToken.isIdent(tokenList.get(NextOrPrevious.getPosition()));
            boolean terceiroValido = AnaliseToken.pontoVirgula(tokenList.get(NextOrPrevious.getPosition()));

            if(primeiroValido
                    && segundoValido
                    && terceiroValido) {

                NextOrPrevious.modificarStatus();
                return true;
            }

            boolean opcDeclacao = false;
            NextOrPrevious.modificarStatus();

            if(NextOrPrevious.getStatusNP().equals(StatusNP.INCOMPLETO)) {
                if(opcVarDlc(tokenList)) {
                    opcDeclacao = true;
                }

                if(primeiroValido && segundoValido && opcDeclacao) {
                    NextOrPrevious.modificarStatus();
                    return true;
                }
            }

            if(primeiroValido
                    && segundoValido
                    && !terceiroValido
                    && !tokenList.get(NextOrPrevious.getPosition()).getValor().equals(",")
                    && tokenList.size()==3){
            }
        }

        return false;
    }

    public static boolean varDlc (Token token) {
        if(token.getClasseToken().getNome().equals(ClasseToken.IDENTIFICADOR)) {
            NextOrPrevious.next();
            return true;
        }
        return false;
    }

    public static boolean opcVarDlc(List<Token> tokenList) {
        if(AnaliseToken.virgulaOpc(tokenList.get(NextOrPrevious.getPosition()))) {
            if(varDlc(tokenList.get(NextOrPrevious.getPosition()))){
                return opcVarDlc(tokenList);
            }
        }

        return AnaliseToken.pontoVirgula(tokenList.get(NextOrPrevious.getPosition()));
    }

    /**
     * Retorna o TIPO(int, char, float..) do TOKEN
     * @param token
     * @return
     */
    public static boolean type(Token token) {
        if(token.getClasseToken().getNome().equals(ClasseToken.PALAVRA_RESERVADA)) {
            NextOrPrevious.next();
            return true;
        }
        return false;
    }

    public static boolean parmTypes(List<Token> tokenList) {
        return (type(tokenList.get(NextOrPrevious.getPosition()))
                && AnaliseToken.isIdent(tokenList.get(NextOrPrevious.getPosition())));
    }

    // sb.append("int teste(int i[]) {\n");
    private static boolean func(List<Token> tokenList) {
        if(tokenList.size()>= 3) {

            boolean estruturaValida = type(tokenList.get(NextOrPrevious.getPosition()))
                    && AnaliseToken.isIdent(tokenList.get(NextOrPrevious.getPosition()))
                    && AnaliseToken.abreParenteses(tokenList.get(NextOrPrevious.getPosition()))
                    && AnaliseToken.fechaParenteses(tokenList.get(NextOrPrevious.getPosition()))
                    && (isBloco(tokenList)) || AnaliseToken.pontoVirgula(tokenList.get(NextOrPrevious.getPosition()));

           if (estruturaValida) {
                System.out.println("Tokens de <Função> encontrados");
                for (Token listFunc: tokenList) {
                    System.out.println(listFunc.getValor());
                }

                NextOrPrevious.modificarStatus();

                if(NextOrPrevious.getStatusNP().equals(StatusNP.COMPLETO)) {
                    System.out.println("Verificando o bloco dentro da função");
                    do{
                        NextOrPrevious.nextRow();

                        tokenList = TabelaSimbolo.getTokensByLinha(NextOrPrevious.getRow());
                        NextOrPrevious.setSize(tokenList.size());
                        NextOrPrevious.resetPosition();

                        if(dlc(tokenList)) {
                            NextOrPrevious.resetPosition();
                            System.out.println("Encontrou uma dlc na linha: " + NextOrPrevious.getRow());
                            for (Token token: tokenList) {
                                System.out.println(token.getValor());
                            }
                        } else {
                            System.out.println("Não encontrou uma dlc na linha: " + NextOrPrevious.getRow());
                        }
                    } while (!AnaliseToken.fechaChaves(tokenList.get(NextOrPrevious.getPosition())));
                }
                System.out.println("Fim da função");
                return true;
           }
        }
        return false;
    }

    // {void} || {bloco}
    private static boolean isBloco(List<Token> tokenList) {
        boolean abreChaves = AnaliseToken.abreChaves(tokenList.get(NextOrPrevious.getPosition()));

        return false;
    }

    public static boolean stmt(List<Token> tokenList) {
        return true;
    }

}


