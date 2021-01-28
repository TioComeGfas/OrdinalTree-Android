package cl.tiocomegfas.ubb.loud.backend.threads;

import android.content.Context;

import cl.tiocomegfas.ubb.loud.backend.listeners.OnBuildLoudTreeListener;
import cl.tiocomegfas.ubb.loud.controller.Manager;

public class BuildLoudTreeThread implements Runnable{

    private static final BuildLoudTreeThread INSTANCE = new BuildLoudTreeThread();
    private Context context;
    private Thread thread;
    private int loudTree;
    private OnBuildLoudTreeListener listener;

    private BuildLoudTreeThread(){ }

    public static BuildLoudTreeThread getInstance(){
        return INSTANCE;
    }

    @Override
    public void run() {
        listener.onRunning();

        if(loudTree == Manager.LOUD_TREE_1) Manager.getInstance().buildLoudTree1(1000);
        else if(loudTree == Manager.LOUD_TREE_2) Manager.getInstance().buildLoudTree2(10000);
        else if(loudTree == Manager.LOUD_TREE_3) Manager.getInstance().buildLoudTree1(100000);
        else {
            listener.onError("loudTree > 0 & loudTree < 4");
            return;
        }

        listener.onReady(loudTree);
    }

    public BuildLoudTreeThread setListener(OnBuildLoudTreeListener listener) {
        this.listener = listener;
        return this;
    }

    public BuildLoudTreeThread setLoudTree(int loudTree) {
        this.loudTree = loudTree;
        return this;
    }

    public BuildLoudTreeThread setContext(Context context) {
        this.context = context;
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
