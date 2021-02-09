package cl.tiocomegfas.ubb.loud.backend.threads;

import java.text.DecimalFormat;

import cl.tiocomegfas.bitarray.LoudsTree;
import cl.tiocomegfas.library.backend.parser.Parser;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnTableViewGenerateDataListener;
import cl.tiocomegfas.ubb.loud.controller.Manager;

public class TableViewGenerateDataThread implements Runnable {

    private static final TableViewGenerateDataThread INSTANCE = new TableViewGenerateDataThread();

    private Thread thread;
    private OnTableViewGenerateDataListener listener;

    @Override
    public void run() {
        listener.onRunning();

        DecimalFormat df = new DecimalFormat("0.00000000");

        //consultas padres
        Manager.getInstance().startChronometer(Manager.LOUD_TREE_1);
        Manager.getInstance().getParent(Manager.LOUD_TREE_1,800);
        double timeParentTree1 = Parser.toDouble(df.format(Manager.getInstance().stopChronometer(Manager.LOUD_TREE_1)));

        Manager.getInstance().startChronometer(Manager.LOUD_TREE_2);
        Manager.getInstance().getParent(Manager.LOUD_TREE_2,9800);
        double timeParentTree2 = Parser.toDouble(df.format(Manager.getInstance().stopChronometer(Manager.LOUD_TREE_2)));

        Manager.getInstance().startChronometer(Manager.LOUD_TREE_3);
        Manager.getInstance().getParent(Manager.LOUD_TREE_3,98000);
        double timeParentTree3 = Parser.toDouble(df.format(Manager.getInstance().stopChronometer(Manager.LOUD_TREE_3)));

        listener.onTimeParent(timeParentTree1,timeParentTree2,timeParentTree3);


        //consultas primer hijo
        Manager.getInstance().startChronometer(Manager.LOUD_TREE_1);
        Manager.getInstance().getFirstChild(Manager.LOUD_TREE_1,2);
        double timeFirstChildTree1 = Parser.toDouble(df.format(Manager.getInstance().stopChronometer(Manager.LOUD_TREE_1)));

        Manager.getInstance().startChronometer(Manager.LOUD_TREE_2);
        Manager.getInstance().getFirstChild(Manager.LOUD_TREE_2,2);
        double timeFirstChildTree2 = Parser.toDouble(df.format(Manager.getInstance().stopChronometer(Manager.LOUD_TREE_2)));

        Manager.getInstance().startChronometer(Manager.LOUD_TREE_3);
        Manager.getInstance().getFirstChild(Manager.LOUD_TREE_3,2);
        double timeFirstChildTree3 = Parser.toDouble(df.format(Manager.getInstance().stopChronometer(Manager.LOUD_TREE_3)));

        listener.onTimeFirstChild(timeFirstChildTree1,timeFirstChildTree2,timeFirstChildTree3);


        //consultas siguiente hermano
        Manager.getInstance().startChronometer(Manager.LOUD_TREE_1);
        Manager.getInstance().getSibling(Manager.LOUD_TREE_1,800);
        double timeNextSiblingTree1 = Parser.toDouble(df.format(Manager.getInstance().stopChronometer(Manager.LOUD_TREE_1)));

        Manager.getInstance().startChronometer(Manager.LOUD_TREE_2);
        Manager.getInstance().getSibling(Manager.LOUD_TREE_2,9800);
        double timeNextSiblingTree2 = Parser.toDouble(df.format(Manager.getInstance().stopChronometer(Manager.LOUD_TREE_2)));

        Manager.getInstance().startChronometer(Manager.LOUD_TREE_3);
        Manager.getInstance().getSibling(Manager.LOUD_TREE_3,98000);
        double timeNextSiblingTree3 = Parser.toDouble(df.format(Manager.getInstance().stopChronometer(Manager.LOUD_TREE_3)));

        listener.onTimeNextSibling(timeNextSiblingTree1,timeNextSiblingTree2,timeNextSiblingTree3);

        //consultas data
        Manager.getInstance().startChronometer(Manager.LOUD_TREE_1);
        Manager.getInstance().getPerson(Manager.LOUD_TREE_1,800);
        double timeDataTree1 = Parser.toDouble(df.format(Manager.getInstance().stopChronometer(Manager.LOUD_TREE_1)));

        Manager.getInstance().startChronometer(Manager.LOUD_TREE_2);
        Manager.getInstance().getPerson(Manager.LOUD_TREE_2,9800);
        double timeDataTree2 = Parser.toDouble(df.format(Manager.getInstance().stopChronometer(Manager.LOUD_TREE_2)));

        Manager.getInstance().startChronometer(Manager.LOUD_TREE_3);
        Manager.getInstance().getPerson(Manager.LOUD_TREE_3,98000);
        double timeDataTree3 = Parser.toDouble(df.format(Manager.getInstance().stopChronometer(Manager.LOUD_TREE_3)));

        listener.onTimeData(timeDataTree1,timeDataTree2,timeDataTree3);

        listener.onReady();
    }

    public static TableViewGenerateDataThread getInstance() {
        return INSTANCE;
    }

    public TableViewGenerateDataThread setListener(OnTableViewGenerateDataListener listener) {
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
