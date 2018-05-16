import java.util.HashSet;
import java.util.Set;

public class PalavraReservada {
    
    private static Set palavraReservaList = new HashSet<String>();
    
    static {
        palavraReservaList.add("asm");
        palavraReservaList.add("auto");
        palavraReservaList.add("break");
        palavraReservaList.add("case");
        palavraReservaList.add("char");
        palavraReservaList.add("const");
        palavraReservaList.add("continue");
        palavraReservaList.add("default");
        palavraReservaList.add("do.");
        palavraReservaList.add("double");
        palavraReservaList.add("else");
        palavraReservaList.add("enum");
        palavraReservaList.add("extern");
        palavraReservaList.add("float");
        palavraReservaList.add("for");
        palavraReservaList.add("goto");
        palavraReservaList.add("if");
        palavraReservaList.add("int");
        palavraReservaList.add("long");
        palavraReservaList.add("register");
        palavraReservaList.add("return");
        palavraReservaList.add("short");
        palavraReservaList.add("signed");
        palavraReservaList.add("sizeof");
        palavraReservaList.add("static");
        palavraReservaList.add("struct");
        palavraReservaList.add("switch");
        palavraReservaList.add("typedef");
        palavraReservaList.add("union");
        palavraReservaList.add("unsigned");
        palavraReservaList.add("void");
        palavraReservaList.add("volatile");
        palavraReservaList.add("while");
    }

    public static Set getPalavraReservaList() {
        return palavraReservaList;
    }
    
    public static boolean isPalavraReservada(String palavra) {
        return palavraReservaList.contains(palavra);
    }

}
