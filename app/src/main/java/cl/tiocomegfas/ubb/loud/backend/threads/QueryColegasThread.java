/**
 * PARA ENCONTRAR TODOS LOS COLEGAS, ES NECESARIO OBTENER EL PADRE DEL NODO INGRESADO
 * POSTERIORMENTE, BUSCAR TODOS LOS HERMANOS DE ESE PADRE
 * OBTENER TODOS LOS HIJOS DE CADA HERMANO DEL PADRE Y LOS HERMANOS DEL NODO INGRESADO
 * ASI SE OBTIENEN TODOS LOS HIJOS DE UN NIVEL EN ESPECIFICO
 */


package cl.tiocomegfas.ubb.loud.backend.threads;

import android.content.Context;

import java.util.Collection;
import java.util.LinkedList;

import cl.tiocomegfas.library.backend.parser.Parser;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryColegasListener;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import cl.tiocomegfas.ubb.loud.controller.Manager;


public class QueryColegasThread implements Runnable{

    private final int REQUEST_FIN = 0;

    private static final QueryColegasThread INSTANCE = new QueryColegasThread();
    private Context context;
    private Thread thread;
    private int loudTree;
    private int position;
    private OnQueryColegasListener listener;

    private QueryColegasThread(){ }

    public static QueryColegasThread getInstance(){
        return INSTANCE;
    }

    @Override
    public void run() {
        listener.onRunning();

        //INICIO DEL CRONOMETRO
        Manager.getInstance().startChronometer(loudTree);

        //OBTENER EL PADRE
        String padreStr = Manager.getInstance().getParent(loudTree,position);
        int idPadre = Parser.toInteger(padreStr.split("_")[0]);

        //para guardar todos los hermanos del padre
        LinkedList<Integer> padres = new LinkedList<>();
        padres.add(idPadre);
        int sizePadres = 1;

        while(true){
            int idHermano = Parser.toInteger(Manager.getInstance().getNextSibling(loudTree,idPadre).split("_")[0]);

            /**
             * RECORDAR VALIDAR CAMBIAR ESTE VALOR!!!
             */
            if(idHermano == REQUEST_FIN) break;

            padres.add(idHermano);
            sizePadres++;
        }

        //UNA VEZ OBTENIDO LOS HERMANOS DEL PADRE, HAY QUE BUSCAR TODOS LOS HIJOS QUE POSEEAN ESTOS PADRES
        LinkedList<Integer[]> idHijos = new LinkedList<>();

        //recorremos la lista con los hermanos del padre
        for(int i = 0; i < sizePadres; i++){
            int padre = padres.get(i);

            LinkedList<Integer> hijosTmp = new LinkedList<>();
            Integer[] hijos;

            int hijo = Parser.toInteger(Manager.getInstance().getFirstChild(loudTree,padre).split("_")[0]);

            // si es un hijo continuar
            if(hijo != REQUEST_FIN){
                hijosTmp.add(hijo);

                while(true){
                    hijo = Parser.toInteger(Manager.getInstance().getNextSibling(loudTree,hijo).split("_")[0]);

                    if(hijo == REQUEST_FIN) break;

                    hijosTmp.add(hijo);
                }
            }

            hijos = (Integer[]) hijosTmp.toArray();
            idHijos.add(hijos);
        }

        //FIN DEL CRONOMETRO
        double time = Manager.getInstance().stopChronometer(loudTree);

        listener.onReady(time, padres, idHijos);
    }

    public QueryColegasThread setContext(Context context) {
        this.context = context;
        return this;
    }

    public QueryColegasThread setLoudTree(int loudTree) {
        this.loudTree = loudTree;
        return this;
    }

    public QueryColegasThread setPosition(int position) {
        this.position = position;
        return this;
    }

    public QueryColegasThread setListener(OnQueryColegasListener listener) {
        this.listener = listener;
        return this;
    }

    public void start(){
        this.thread = new Thread(this);
        this.thread.start();
    }

    public void stop(){
        if(this.thread != null){
            this.thread.interrupt();
        }
    }
}