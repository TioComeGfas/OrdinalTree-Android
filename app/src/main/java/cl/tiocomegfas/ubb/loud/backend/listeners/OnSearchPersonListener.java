package cl.tiocomegfas.ubb.loud.backend.listeners;

public interface OnSearchPersonListener {
    void onRunning();
    void onReady(int position);
    void onError(String message);
}
