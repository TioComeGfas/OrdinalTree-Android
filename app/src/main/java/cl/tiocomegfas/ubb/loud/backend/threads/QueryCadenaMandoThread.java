package cl.tiocomegfas.ubb.loud.backend.threads;

import java.util.LinkedList;

import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryCadenaMandoListener;
import cl.tiocomegfas.ubb.loud.controller.Manager;

public class QueryCadenaMandoThread implements Runnable{

    private static final QueryCadenaMandoThread INSTANCE = new QueryCadenaMandoThread();
    private Thread thread;
    private int loudTree;
    private int position;
    private OnQueryCadenaMandoListener listener;

    private QueryCadenaMandoThread(){ }

    public static QueryCadenaMandoThread getInstance(){
        return INSTANCE;
    }

    @Override
    public void run() {
        listener.onRunning();

        Manager.getInstance().startChronometer(loudTree); //INICIO DEL CRONOMETRO

        LinkedList<Integer> ids = new LinkedList<>();

        int personID = -1;
        while(personID != 0){
            personID = (int) Manager.getInstance().getParent(loudTree,position);
            ids.add(personID);
        }

        int sizeIDs = ids.size();
        int[] idPersons = new int[sizeIDs];
        String[] names = new String[sizeIDs];
        for(int i = 0; i < sizeIDs; i++){
            idPersons[i] = ids.get(i);
            names[i] = Manager.getInstance().getPerson(loudTree,idPersons[i]).toString();
        }

        double time = Manager.getInstance().stopChronometer(loudTree); //FIN DEL CRONOMETRO

        listener.onReady(idPersons, names, time);
    }

    public QueryCadenaMandoThread setLoudTree(int loudTree) {
        this.loudTree = loudTree;
        return this;
    }

    public QueryCadenaMandoThread setPosition(int position) {
        this.position = position;
        return this;
    }

    public QueryCadenaMandoThread setListener(OnQueryCadenaMandoListener listener) {
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
