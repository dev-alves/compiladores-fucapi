import java.util.List;

public class AnaliseToken {

    private static Token token;

    public static boolean tokenIf(Token token) {
        if(token.getValor().equals("if")) {
            NextOrPrevious.next();
            return true;
        }

        return false;
    }

    public static boolean tokenElse(Token token) {
        if(token.getValor().equals("else")) {
            NextOrPrevious.next();
            return true;
        }

        return false;
    }

    public static boolean tokenWhile(Token token) {
        if(token.getValor().equals("while")) {
            NextOrPrevious.next();
            return true;
        }

        return false;
    }


    public static boolean logicalOp(List<Token> tokens) {
        String eLogical = "&&";
        String ouLogical = "||";
        int i=0;
        boolean result = false;
        while (i<2) {
            if(eLogical.contains(tokens.get(NextOrPrevious.getPosition()).getValor())){
                NextOrPrevious.next();
                if(i==1)
                    result = true;
            }
            i++;
        }

        return result ? true: false;
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

    public static boolean isIdent(Token token) {
        if(token.getClasseToken().getNome().equals(ClasseToken.IDENTIFICADOR)) {
            NextOrPrevious.next();
            return true;
        }
        return false;
    }

    public static boolean abreParenteses(Token token) {
        if(token.getValor().equals("(")) {
            NextOrPrevious.next();
            return true;
        }
        return false;
    }

    public static boolean fechaParenteses(Token token) {
        if(token.getValor().equals(")")) {
            NextOrPrevious.next();
            return true;
        }
        return false;
    }

    public static boolean colcheteAbeturaDeExpressao(Token token) {
        if( token.getValor().equals("[")) {
            NextOrPrevious.next();
            return true;
        }
        return false;
    }

    public static boolean colcheteFechaDeExpressao(Token token) {
        if(token.getValor().equals("]")) {
            NextOrPrevious.next();
            return true;
        }
        return false;
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

    public static boolean abreChaves(Token token) {
        if(token.getValor().equals("{")) {
            NextOrPrevious.next();
            return true;
        }
        return false;
    }

    public static boolean fechaChaves(Token token) {
        if(token.getValor().equals("}")) {
            NextOrPrevious.next();
            return true;
        }
        return false;
    }

}
