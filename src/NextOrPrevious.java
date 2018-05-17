public class NextOrPrevious {

    private static int size;
    private static  int position = 0;
    private static StatusNP statusNP = StatusNP.INCOMPLETO;

    public NextOrPrevious(int size) {
        this.size = size;
    }

    public NextOrPrevious(int size, int position) {
        this.size = size;
        this.position = position;
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        NextOrPrevious.size = size;
        NextOrPrevious.position = 0;
    }

    public static int getPosition() {
        return position;
    }

    public static void setPosition(int position) {
        NextOrPrevious.position = position;
    }

    public static void next() {
        if(position < size)
            position++;
    }

    public static void previous() {
        if(position < size)
            position--;
    }

    public static StatusNP getStatusNP() {
        return statusNP;
    }

    public static void modificarStatus() {
        if(position < size) {
            NextOrPrevious.statusNP = StatusNP.INCOMPLETO;
        } else if (position == size) {
            NextOrPrevious.statusNP = StatusNP.COMPLETO;
        }

    }
}
