package cl.tiocomegfas.ubb.cronometer;

public class CronometerAPI {

    private static final CronometerAPI API = new CronometerAPI();

    /**
     * Carga de la libreria en C++
     */
    static {
        System.loadLibrary("cronometer-lib");
    }

    private CronometerAPI(){ }

    public static CronometerAPI getInstance(){
        return API;
    }

    public native void init();

    public native void startClock();

    public native double stopClock();

}
