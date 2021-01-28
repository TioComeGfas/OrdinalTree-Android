package cl.tiocomegfas.ubb.loud.backend.threads;

import android.content.Context;

import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryCadenaMandoListener;

public class QueryCadenaMandoThread implements Runnable{

    private static final QueryCadenaMandoThread INSTANCE = new QueryCadenaMandoThread();
    private Context context;
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


    }
}
