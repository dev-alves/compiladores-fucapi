import java.math.BigInteger;
import java.util.List;

public class Compiladores {

    public static int linhaAtual = 1;
    public static ClasseToken palavraReservada  = new ClasseToken(ClasseToken.PALAVRA_RESERVADA);
    public static ClasseToken identificador = new ClasseToken(ClasseToken.IDENTIFICADOR);

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
        sb.append("int x1, x2, x3;\n");
        sb.append("int main() {\n");
        sb.append("char sexo = 'M';");
        //sb.append("/*comentario de bloco*/");
        sb.append(" x1 = 22; //este é um comentário \n");
        sb.append("printf(\"Resultado: %d\", x1);");
        sb.append("return 0;\n");
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
/*
    private static boolean isIdent(String cadeia) {

    }
*/
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


    private static void analiseSintatica() {
        System.out.println("Iniciando Análise Sintática");
        for (int i=1; i<linhaAtual; i++) {
            List<Token> tokensSobAnalise = TabelaSimbolo.getTokensByLinha(i);

            NextOrPrevious.setSize(tokensSobAnalise.size());

            System.out.println("Tamanho do NP: " + NextOrPrevious.getSize());

            System.out.println("Linha "+i+" # tokens sendo analisados:");

            if (dlc(tokensSobAnalise)) {
                System.out.println("Os tokens abaixo compoe uma declaração");
                for (Token t : tokensSobAnalise) {
                    System.out.println(t.toString());
                }
            } else {
                System.out.println("NÃO É DECLARAÇÃO!!!");
            }
        }
    }

    private static boolean func(List<Token> token) {
          //TO DO
        return  true;
    }

    public static boolean virgulaOpc(Token token) {

        if(token.getValor().equals(",")) {
            NextOrPrevious.next();
            return true;
        }

        return false;
    }

    public static boolean pontoVirgula(Token token) {
        if(token.getValor().equals(";")) {
            NextOrPrevious.next();
            return true;
        }

        return false;
    }
    /**
     * Retorna o TIPO(int, char, float..) do TOKEN
     * @param token
     * @return
     */
    public static boolean type(Token token) {
        NextOrPrevious.next();
        return token.getClasseToken().getNome().equals(ClasseToken.PALAVRA_RESERVADA);
    }

    public static boolean isIdent(Token token) {
        NextOrPrevious.next();
        return token.getClasseToken().getNome().equals(ClasseToken.IDENTIFICADOR);
    }

    /**
     * Verifcar se a expressão é do tipo: [N][N]
     * @param tokenList
     * @return
     */
    public static boolean cholcheteExpressao(List<Token> tokenList) {
       if(colcheteAbeturaDeExpressao(tokenList.get(NextOrPrevious.getPosition()))) {
           if(tokenList.get(NextOrPrevious.getPosition()).getClasseToken().getNome().equals(ClasseToken.CONSTANTE_NUMERICA)){
               if(colcheteFechaDeExpressao(tokenList.get(NextOrPrevious.getPosition()))) {
                   return true;
               }
           }
       }

       return false;
    }

    public static boolean colcheteAbeturaDeExpressao(Token token) {
        NextOrPrevious.next();
        return token.getValor().equals("[");
    }

    public static boolean colcheteFechaDeExpressao(Token token) {
        NextOrPrevious.next();
        return token.getValor().equals("]");
    }


    public static boolean opcVarDlc(List<Token> tokenList) {
        if (virgulaOpc(tokenList.get(NextOrPrevious.getPosition()))
                && varDlc(tokenList.get(NextOrPrevious.getPosition()))) {
            return opcVarDlc(tokenList);
        }

        if(pontoVirgula(tokenList.get(NextOrPrevious.getPosition()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Transformando a bnf abaixo em código
     * var_decla : id [ '[' intcon ']' ]
     * @param token
     * @return
     */
    public static boolean varDlc (Token token) {
        System.out.println("token: " + token.getClasseToken().getNome());
        if(token.getClasseToken().getNome().equals(ClasseToken.IDENTIFICADOR)) {
            NextOrPrevious.next();
            return true;
        }
        return false;
    }

    public static boolean prog(List<Token> tokens) {
        return dlc(tokens);
    }

    public static boolean dlc(List<Token> tokenList) {

        if (tokenList.size() >= 3) {
            boolean primeiroValido = type(tokenList.get(NextOrPrevious.getPosition()));
            boolean segundoValido = isIdent(tokenList.get(NextOrPrevious.getPosition()));
            boolean terceiroValido = pontoVirgula(tokenList.get(NextOrPrevious.getPosition()));

            if (primeiroValido
                    && segundoValido
                    && terceiroValido) {

                return true;
            }

            boolean opcDeclacao = false;
            if(NextOrPrevious.getPosition() < NextOrPrevious.getSize()) {
                if(opcVarDlc(tokenList)) {
                    opcDeclacao = true;
                }

                return ((primeiroValido && segundoValido && opcDeclacao));
            }
        }

        return false;

    }
    
}

