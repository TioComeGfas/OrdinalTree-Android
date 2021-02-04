package cl.tiocomegfas.ubb.loud.backend.listeners;

public interface OnQueryJefeListener extends OnErrorBaseListener, OnRunningBaseListener{
    void onReady( int parentID, int childID, String parent, String child, double time);

}
