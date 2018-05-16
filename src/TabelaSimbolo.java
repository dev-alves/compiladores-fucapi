import java.util.ArrayList;
import java.util.List;

public class TabelaSimbolo {
    private static List<Token> tokens = new ArrayList<>();

    public static List getTokens() {
        return tokens;
    }

    public static void addToken(Token token) {
        tokens.add(token);
    }
    
    public static List<Token> getTokensByLinha(int linha) {
        List<Token> tokensDaLinha = new ArrayList<>();
        for (Token token : tokens) {
            if (token.getLinha() == linha) {
                tokensDaLinha.add(token);
            }
            if (token.getLinha() > linha) {
                return tokensDaLinha;
            }
        }
        return tokensDaLinha;
    }
}
