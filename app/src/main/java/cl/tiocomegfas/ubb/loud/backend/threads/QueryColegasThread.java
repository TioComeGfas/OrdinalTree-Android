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
        Manager.getInstance().startChronometer(loudTree); //INICIO DEL CRONOMETRO

        int parentID = (int) Manager.getInstance().getParent(loudTree, position); //obtengo el padre
        int fChild = (int) Manager.getInstance().getFirstChild(loudTree,parentID); // obtengo el primer hijo del padre
        int lChild = (int) Manager.getInstance().getSibling(loudTree,fChild); // obtengo el ultimo hijo del padre

        int size = lChild - fChild + 1;
        int[] idPersons = new int[size];
        String[] strPersons = new String[size];

        idPersons[0] = parentID;
        strPersons[0] = Manager.getInstance().getPerson(loudTree,parentID).toString();

        int i = 1;
        while(fChild != lChild){
            idPersons[i] = fChild;
            strPersons[i] = Manager.getInstance().getPerson(loudTree,fChild).toString();
            fChild++;
            i++;
        }

        double time = Manager.getInstance().stopChronometer(loudTree); //FIN DEL CRONOMETRO

        listener.onReady(idPersons,strPersons,time);
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