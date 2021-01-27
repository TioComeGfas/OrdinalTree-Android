package cl.tiocomegfas.ubb.loud.frontend.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cl.tiocomegfas.library.frontend.top_bar.DialogTop;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnLoadDataListener;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import cl.tiocomegfas.ubb.loud.controller.Manager;
import cl.tiocomegfas.ubb.loud.controller.Pipe;
import cl.tiocomegfas.ubb.loud.frontend.activities.HomeActivity;

public class ExperimentFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_1000_nodes_state)
    TextView tv1000nodes;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_10000_nodes_state)
    TextView tv10000nodes;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tv_100000_nodes_state)
    TextView tv100000nodes;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bt_load_json)
    Button btLoadJson;

    @BindView(R.id.bt_search_subordinados)
    Button btSubordinados;

    private Context context;
    private HomeActivity activity;
    private Unbinder unbinder;

    private final OnLoadDataListener listener = new OnLoadDataListener() {
        @Override
        public void onRunning() {
            //activity.runOnUiThread(() -> DialogTop.show(activity,"Generando información", DialogTop.SUCCESS));
        }

        @Override
        public void onReady(LinkedList<Person> persons) {
            activity.runOnUiThread(() -> {
                Log.e("TAG",persons.size()+"");
                int size = persons.size();
                if(size == 1000){
                    tv1000nodes.setText("Cargado");
                    Manager.getInstance().setPersons(Manager.LOUD_TREE_1,persons);
                    tv1000nodes.setTextColor(getResources().getColor(R.color.md_green_500));
                    Pipe.getInstance().callLoadJson(context,10000,listener);
                    return;
                }else if(size == 10000){
                    tv10000nodes.setText("Cargado");
                    Manager.getInstance().setPersons(Manager.LOUD_TREE_2,persons);
                    tv10000nodes.setTextColor(getResources().getColor(R.color.md_green_500));
                    Pipe.getInstance().callLoadJson(context,100000,listener);
                    return;
                }else if(size == 100000){
                    tv100000nodes.setText("Cargado");
                    Manager.getInstance().setPersons(Manager.LOUD_TREE_3,persons);
                    tv100000nodes.setTextColor(getResources().getColor(R.color.md_green_500));
                }
                DialogTop.show(activity,"Información generada correctamente!!", DialogTop.SUCCESS);
            });
        }

        @Override
        public void onError(String message) {
            activity.runOnUiThread(() -> DialogTop.show(activity,"Ocurrio un problema",message, DialogTop.ERROR));
        }
    };

    public ExperimentFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_experiment, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();
        this.activity = (HomeActivity) getActivity();

        tv1000nodes.setTextColor(getResources().getColor(R.color.md_red_500));
        tv10000nodes.setTextColor(getResources().getColor(R.color.md_red_500));
        tv100000nodes.setTextColor(getResources().getColor(R.color.md_red_500));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_load_json)
    void onClickLoadJson(){
        Pipe.getInstance().callLoadJson(context,1000,listener);
    }

    @OnClick(R.id.bt_search_subordinados)
    void onClickSubordinados(){
        Manager.getInstance().startChronometerTree1();
    }

    @OnClick(R.id.bt_search_jefe)
    void onClickJefe(){
        double value = Manager.getInstance().stopChronometerTree1();

        Toast.makeText(getContext(),value + "", Toast.LENGTH_LONG).show();
    }
}