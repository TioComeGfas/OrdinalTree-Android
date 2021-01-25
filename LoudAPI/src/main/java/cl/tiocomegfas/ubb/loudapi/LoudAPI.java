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

    public native void build(long[] bitArray, int size);

    /**
     * Retorna el primer hijo del nodo x
     * @param x
     */
    public native void firstChild(int x);

    /**
     * Retorna el siguiente hermano del nodo x
     * @param x
     */
    public native void nextSibling(int x);

    /**
     * Retorna el padre del nodo x
     * @param x
     */
    public native void parent(int x);

    /**
     * Retorna el i-ésimo hijo del nodo x
     * @param x
     * @param i
     */
    public native void child(int x, int i);

    /**
     * Entrega la información asociada al nodo x
     * @param x
     */
    public native void data(int x);

}