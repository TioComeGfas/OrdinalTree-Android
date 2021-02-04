package cl.tiocomegfas.ubb.loud.backend.threads;

import android.util.Log;

import java.util.Arrays;

import cl.tiocomegfas.ubb.loud.backend.listeners.OnQuerySubordinadosListener;
import cl.tiocomegfas.ubb.loud.controller.Manager;

public class QuerySubordinadosThread implements Runnable {

    private static final QuerySubordinadosThread INSTANCE = new QuerySubordinadosThread();
    private Thread thread;
    private int loudTree;
    private int position;
    private OnQuerySubordinadosListener listener;

    private QuerySubordinadosThread(){ }

    public static QuerySubordinadosThread getInstance(){
        return INSTANCE;
    }

    @Override
    public void run() {
        listener.onRunning();

        Manager.getInstance().startChronometer(loudTree);
        int fChild = (int) Manager.getInstance().getFirstChild(loudTree, position);
        int lChild = (int) Manager.getInstance().getSibling(loudTree, fChild);

        int size = lChild - fChild + 1;
        int[] ids = new int[size];
        String[] names = new String[size];

        ids[0] = position;
        names[0] = Manager.getInstance().getPerson(loudTree,position).toString();

        int i = 1;
        while(fChild != lChild){
            ids[i] = fChild;
            names[i] = Manager.getInstance().getPerson(loudTree,fChild).toString();
            fChild++;
            i++;
        }

        double time = Manager.getInstance().stopChronometer(loudTree);

        listener.onReady(ids,names,time);
    }

    public QuerySubordinadosThread setLoudTree(int loudTree) {
        this.loudTree = loudTree;
        return this;
    }

    public QuerySubordinadosThread setPosition(int position) {
        this.position = position;
        return this;
    }

    public QuerySubordinadosThread setListener(OnQuerySubordinadosListener listener) {
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
