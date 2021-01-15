package cl.tiocomegfas.ubb.loudapi;

public class LoudAPI {

    /**
     * Carga de la libreria en C++
     */
    static {
        System.loadLibrary("loud-lib");
    }

    private static final LoudAPI API = new LoudAPI();

    private LoudAPI(){ }

    public static LoudAPI getInstance(){
        return API;
    }

    /**
     * Inicializa la estructura
     * PODRIA CARGAR DIRECTAMENTE LA INFORMACION DESDE ACA
     */
    public native void init();

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