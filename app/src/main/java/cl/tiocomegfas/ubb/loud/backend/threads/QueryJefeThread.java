package cl.tiocomegfas.ubb.loud.backend.threads;

import android.content.Context;
import android.text.TextUtils;

import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryJefeListener;
import cl.tiocomegfas.ubb.loud.controller.Manager;

public class QueryJefeThread implements Runnable{

    private static final QueryJefeThread INSTANCE = new QueryJefeThread();
    private Context context;
    private Thread thread;
    private int loudTree;
    private int position;
    private OnQueryJefeListener listener;

    private QueryJefeThread(){ }

    public static QueryJefeThread getInstance(){
        return INSTANCE;
    }

    @Override
    public void run() {
        listener.onRunning();
        String personStr = Manager.getInstance().getParent(loudTree, position);

        if(TextUtils.isEmpty(personStr)){
            listener.onError("No fue posible obtener el jefe");
            return;
        }

        listener.onReady(personStr,position);
    }

    public QueryJefeThread setLoudTree(int loudTree) {
        this.loudTree = loudTree;
        return this;
    }

    public QueryJefeThread setPosition(int position) {
        this.position = position;
        return this;
    }

    public QueryJefeThread setContext(Context context) {
        this.context = context;
        return this;
    }

    public QueryJefeThread setListener(OnQueryJefeListener listener) {
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
