public class Pair {
    @Override
    public String toString() {
        return index + " " + (char)index + " " + occur + "\n";
    }

    private int index;
    private int occur;

    public Pair(int index, int occur) {
        this.index = index;
        this.occur = occur;
    }

    public int getOccur() {
        return occur;
    }

    public int getIndex() {
        return index;
    }

    public void addOccur(){
        this.occur+=1;
    }
}
