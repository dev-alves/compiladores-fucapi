import java.util.List;

public class AnaliseToken {

    private static Token token;

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
            System.out.println("É um ident");
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

    public static boolean abreParenteses(Token token) {
        if(token.getValor().equals("(")){
            NextOrPrevious.next();
            System.out.println("Abriu parentes");
            return true;
        }
        return false;
    }
    public static boolean fechaParenteses(Token token) {
        if(token.getValor().equals(")")) {
            NextOrPrevious.next();
            System.out.println("Fechou parentes");
            return true;
        }
        return false;
    }

    public static boolean colcheteAbeturaDeExpressao(Token token) {
        if( token.getValor().equals("[")) {
            NextOrPrevious.next();
            System.out.println("Abriu colchetes");
            return true;
        }
        return false;
    }

    public static boolean colcheteFechaDeExpressao(Token token) {
        if(token.getValor().equals("]")) {
            NextOrPrevious.next();
            System.out.println("Fechou colchetes");
            return true;
        }
        return false;
    }

    public static boolean abreChaves(Token token) {
        if(token.getValor().equals("{")) {
            NextOrPrevious.next();
            System.out.println("Abriu chaves");
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
