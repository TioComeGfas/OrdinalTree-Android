package cl.tiocomegfas.ubb.loud.backend.listeners;

public interface OnTableViewGenerateDataListener extends OnRunningBaseListener{
    void onTimeFirstChild(double timeTree1, double timeTree2, double timeTree3);
    void onTimeNextSibling(double timeTree1, double timeTree2, double timeTree3);
    void onTimeParent(double timeTree1, double timeTree2, double timeTree3);
    void onTimeData(double timeTree1, double timeTree2, double timeTree3);
    void onReady();
}
