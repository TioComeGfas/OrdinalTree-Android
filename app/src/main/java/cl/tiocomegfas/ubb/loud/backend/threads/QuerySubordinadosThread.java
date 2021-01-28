package cl.tiocomegfas.ubb.loud.backend.threads;

import android.content.Context;

public class QuerySubordinadosThread implements Runnable {

    private static final QuerySubordinadosThread INSTANCE = new QuerySubordinadosThread();
    private Context context;
    private Thread thread;
    private int loudTree;
    private int position;
    private QuerySubordinadosThread listener;

    private QuerySubordinadosThread(){ }

    public static QuerySubordinadosThread getInstance(){
        return INSTANCE;
    }

    @Override
    public void run() {

    }
}
