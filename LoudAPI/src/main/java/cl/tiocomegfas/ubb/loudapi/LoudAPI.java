package cl.tiocomegfas.ubb.loudapi;

public class LoudAPI {

    private String[] nameLastNameArray;
    private long[] bitArray;
    private long length;
    private int countNodes;

    /**
     * Carga de la libreria en C++
     */
    static {
        System.loadLibrary("loud-lib");
    }

    public LoudAPI(int countNodes){
        this.countNodes = countNodes;
        this.nameLastNameArray = new String[countNodes];
    }

    /**
     * Inicializa la estructura y retorna el
     * @param countNodes la cantidad de nodos a crear
     * @return arreglo de bits generados
     */
    public native long[] init(int countNodes);

    /**
     * Retorna el primer hijo del nodo x
     * @param x
     */
    public native int firstChild(long[] bitArray, int size, int x);

    /**
     * Retorna el siguiente hermano del nodo x
     * @param x
     */
    public native int nextSibling(long[] bitArray, int size, int x);

    /**
     * Retorna el padre del nodo x
     * @param x
     */
    public native int parent(long[] bitArray, int size, int x);

    /**
     * Retorna el i-ésimo hijo del nodo x
     * @param x
     * @param i
     */
    public native int child(long[] bitArray, int size, int x, int i);

    /**
     * Entrega la información asociada al nodo x
     * @param x
     */
    public String data(int x){
        return nameLastNameArray[x];
    }

    public long[] getBitArray() {
        return bitArray;
    }

    public long getLength() {
        return length;
    }
}