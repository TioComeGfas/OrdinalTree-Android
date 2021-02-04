package cl.tiocomegfas.ubb.loud.backend.listeners;

public interface OnQuerySubordinadosListener extends OnErrorBaseListener, OnRunningBaseListener{
    void onReady(int[] ids, String[] names, double time);
}
