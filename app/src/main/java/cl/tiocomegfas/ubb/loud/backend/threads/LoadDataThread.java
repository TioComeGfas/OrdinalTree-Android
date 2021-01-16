package cl.tiocomegfas.ubb.loud.backend.threads;

import android.content.Context;

import java.util.LinkedList;

import cl.tiocomegfas.ubb.loud.backend.exceptions.LoudMasterException;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnLoadDataListener;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import cl.tiocomegfas.ubb.loud.backend.util.DataSet;

public class LoadDataThread implements Runnable{

    private static final LoadDataThread INSTANCE = new LoadDataThread();
    private Context context;
    private OnLoadDataListener listener;
    private int size;
    private Thread thread;

    private LoadDataThread(){ }

    public static LoadDataThread getInstance(){
        return INSTANCE;
    }

    @Override
    public void run() {
        listener.onRunning();
        LinkedList<Person> persons;

        try{
            persons = DataSet.getInstance().load(context,size);
        }catch (LoudMasterException e){
            listener.onError(e.getMessage());
            return;
        }

        listener.onReady(persons);
    }

    public LoadDataThread setContext(Context context) {
        this.context = context;
        return this;
    }

    public LoadDataThread setSize(int size) {
        this.size = size;
        return this;
    }

    public LoadDataThread setListener(OnLoadDataListener listener) {
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