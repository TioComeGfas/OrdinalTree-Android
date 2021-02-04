package cl.tiocomegfas.ubb.loud.backend.listeners;

public interface OnQueryColegasListener extends OnErrorBaseListener, OnRunningBaseListener{
    void onReady(int[] colegasID, String[] colegas, double time);
}
