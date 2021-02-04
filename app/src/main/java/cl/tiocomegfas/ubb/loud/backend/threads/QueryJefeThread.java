package cl.tiocomegfas.ubb.loud.backend.threads;

import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryJefeListener;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import cl.tiocomegfas.ubb.loud.controller.Manager;

public class QueryJefeThread implements Runnable{

    private static final QueryJefeThread INSTANCE = new QueryJefeThread();
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

        Manager.getInstance().startChronometer(loudTree);

        int parentID = (int) Manager.getInstance().getParent(loudTree, position);

        Person parent = Manager.getInstance().getPerson(loudTree,parentID);
        Person child = Manager.getInstance().getPerson(loudTree,position);

        double time = Manager.getInstance().stopChronometer(loudTree);

        listener.onReady(parentID,position,parent.toString(), child.toString(), time);
    }

    public QueryJefeThread setLoudTree(int loudTree) {
        this.loudTree = loudTree;
        return this;
    }

    public QueryJefeThread setPosition(int position) {
        this.position = position;
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
