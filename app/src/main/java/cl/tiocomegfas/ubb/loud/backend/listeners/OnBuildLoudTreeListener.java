package cl.tiocomegfas.ubb.loud.backend.listeners;

public interface OnBuildLoudTreeListener {
    void onRunning();
    void onReady(int loudTree);
    void onError(String message);
}
