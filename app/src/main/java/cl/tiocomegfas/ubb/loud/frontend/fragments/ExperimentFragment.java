package cl.tiocomegfas.ubb.loud.frontend.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import cl.tiocomegfas.library.backend.parser.Parser;
import cl.tiocomegfas.library.frontend.top_bar.DialogTop;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnBuildLoudTreeListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnLoadDataListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryJefeListener;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import cl.tiocomegfas.ubb.loud.controller.Manager;
import cl.tiocomegfas.ubb.loud.controller.Pipe;
import cl.tiocomegfas.ubb.loud.frontend.activities.HomeActivity;
import cl.tiocomegfas.ubb.loud.frontend.bottomsheet.RequestOperationBottomFragment;
import de.blox.graphview.Node;

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
    @BindView(R.id.et_subordinado)
    EditText etSubordinado;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_jefe)
    EditText etJefe;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_bnodo)
    EditText etBuscarNodo;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_cmando)
    EditText etCadenaMando;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.et_colegas)
    EditText etColegas;

    private Context context;
    private HomeActivity activity;
    private Unbinder unbinder;
    private int treeSelect;
    private boolean isLoadJson;

    private final OnBuildLoudTreeListener listenerBuildLoudTree = new OnBuildLoudTreeListener() {
        @Override
        public void onRunning() {

        }

        @Override
        public void onReady(int loudTree) {
            activity.runOnUiThread(() -> {
                if(loudTree == Manager.LOUD_TREE_1) tv1000nodes.setText(getResources().getText(R.string.text_generado));
                else if(loudTree == Manager.LOUD_TREE_2) tv10000nodes.setText(getResources().getText(R.string.text_generado));
                else tv100000nodes.setText(getResources().getText(R.string.text_generado));
            });
        }

        @Override
        public void onError(String message) {
            activity.runOnUiThread(() -> DialogTop.show(activity,"Ocurrio un problema",message, DialogTop.ERROR));
        }
    };
    private final OnLoadDataListener listenerLoadData = new OnLoadDataListener() {
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
                    //Almacenamiento de las personas generadas
                    Manager.getInstance().setPersons(Manager.LOUD_TREE_1,persons);
                    tv1000nodes.setTextColor(getResources().getColor(R.color.md_green_500));
                    tv1000nodes.setText(getResources().getText(R.string.text_cargado));
                    Pipe.getInstance().callLoadJson(context,10000,listenerLoadData);

                    //Creacion del arbol con las personas cargadas
                    Pipe.getInstance().callBuildLoud(context,Manager.LOUD_TREE_1,listenerBuildLoudTree);
                    return;
                }else if(size == 10000){
                    //Almacenamiento de las personas generadas
                    Manager.getInstance().setPersons(Manager.LOUD_TREE_2,persons);
                    tv10000nodes.setTextColor(getResources().getColor(R.color.md_green_500));
                    tv10000nodes.setText(getResources().getText(R.string.text_cargado));
                    Pipe.getInstance().callLoadJson(context,100000,listenerLoadData);


                    //Creacion del arbol con las personas cargadas
                    Pipe.getInstance().callBuildLoud(context,Manager.LOUD_TREE_2,listenerBuildLoudTree);
                    return;
                }else if(size == 100000){
                    //Almacenamiento de las personas generadas
                    Manager.getInstance().setPersons(Manager.LOUD_TREE_3,persons);
                    tv100000nodes.setTextColor(getResources().getColor(R.color.md_green_500));
                    tv100000nodes.setText(getResources().getText(R.string.text_cargado));

                    //Creacion del arbol con las personas cargadas
                    Pipe.getInstance().callBuildLoud(context,Manager.LOUD_TREE_3,listenerBuildLoudTree);
                }
                DialogTop.show(activity,"Información generada correctamente!!", DialogTop.SUCCESS);
                isLoadJson = true;
            });
        }

        @Override
        public void onError(String message) {
            activity.runOnUiThread(() -> DialogTop.show(activity,"Ocurrio un problema",message, DialogTop.ERROR));
        }
    };
    private final OnQueryJefeListener listenerQueryJefe = new OnQueryJefeListener() {
        @Override
        public void onReady(String request, int ID) {
            activity.runOnUiThread(() -> {
                String[] values = request.split("_");

                //este es el jefe
                int id = Parser.toInteger(values[0]);
                String person = values[1];

                //este es el nodo que el jefe busco
                String personDefault = Manager.getInstance().getPersonString(treeSelect,indexNodo);

                Bundle bundle = new Bundle();
                bundle.putString("MODE","JEFE");
                bundle.putInt("ID_JEFE", id);
                bundle.putInt("ID_DEFAULT", indexNodo);
                bundle.putString("PERSON_JEFE",person);
                bundle.putString("PERSON_DEFAULT", personDefault);
                bundle.putInt("TREE",treeSelect);

                showBottomSheetFragment(bundle);
            });
        }

        @Override
        public void onError(String message) {

        }

        @Override
        public void onRunning() {

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

        this.treeSelect = -1;
        this.isLoadJson = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isLoadJson){
            tv1000nodes.setText(getResources().getText(R.string.text_generado));
            tv10000nodes.setText(getResources().getText(R.string.text_generado));
            tv100000nodes.setText(getResources().getText(R.string.text_generado));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_load_json)
    void onClickLoadJson(){
        Pipe.getInstance().callLoadJson(context,1000,listenerLoadData);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_search_subordinados)
    void onClickSubordinados(){
        if(TextUtils.isEmpty(etSubordinado.getText().toString())){
            DialogTop.show(activity,"Recuerde ingresar el número del nodo",DialogTop.PRECAUTION);
            return;
        }
        int indexNodo = Parser.toInteger(etSubordinado.getText().toString());

        if(!checkValues(indexNodo)) return;
        String[] request = Manager.getInstance().getSubordinados(treeSelect,indexNodo);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_search_jefe)
    void onClickJefe(){
        if(TextUtils.isEmpty(etJefe.getText().toString())){
            DialogTop.show(activity,"Recuerde ingresar el número del nodo",DialogTop.PRECAUTION);
            return;
        }
        int indexNodo = Parser.toInteger(etJefe.getText().toString());

        if(!checkValues(indexNodo)) return;
        Manager.getInstance().getJefe(context,treeSelect,indexNodo,listenerQueryJefe);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_search_bnodo)
    void onClickBuscarNodo(){
        if(TextUtils.isEmpty(etBuscarNodo.getText().toString())){
            DialogTop.show(activity,"Recuerde ingresar el número del nodo",DialogTop.PRECAUTION);
            return;
        }
        int indexNodo = Parser.toInteger(etBuscarNodo.getText().toString());

        if(!checkValues(indexNodo)) return;
        //String request = Manager.getInstance().searchNodo(treeSelect,indexNodo);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_search_cmando)
    void onClickCadenaComando(){
        if(TextUtils.isEmpty(etCadenaMando.getText().toString())){
            DialogTop.show(activity,"Recuerde ingresar el número del nodo",DialogTop.PRECAUTION);
            return;
        }
        int indexNodo = Parser.toInteger(etCadenaMando.getText().toString());

        if(!checkValues(indexNodo)) return;
        String[] request = Manager.getInstance().getCadenaDeMando(treeSelect,indexNodo);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_search_coelgas)
    void onClickColegas(){
        if(TextUtils.isEmpty(etColegas.getText().toString())){
            DialogTop.show(activity,"Recuerde ingresar el número del nodo",DialogTop.PRECAUTION);
            return;
        }
        int indexNodo = Parser.toInteger(etColegas.getText().toString());

        if(!checkValues(indexNodo)) return;
        String[] request = Manager.getInstance().getColegas(treeSelect,indexNodo);
    }

    @SuppressLint("NonConstantResourceId")
    @OnCheckedChanged(R.id.rb_tree_1)
    void onCheckedTree1(boolean checked){
        if(checked) treeSelect = Manager.LOUD_TREE_1;
    }

    @SuppressLint("NonConstantResourceId")
    @OnCheckedChanged(R.id.rb_tree_2)
    void onCheckedTree2(boolean checked){
        if(checked) treeSelect = Manager.LOUD_TREE_2;
    }

    @SuppressLint("NonConstantResourceId")
    @OnCheckedChanged(R.id.rb_tree_3)
    void onCheckedTree3(boolean checked){
        if(checked) treeSelect = Manager.LOUD_TREE_3;
    }

    private boolean checkValues(int indexNodo){
        if(!isLoadJson) {
            DialogTop.show(activity,"Antes de continuar, genere los arboles",DialogTop.PRECAUTION);
            return false;
        }

        if(treeSelect == -1){
            DialogTop.show(activity,"Antes de continuar, seleccione el arbol",DialogTop.PRECAUTION);
            return false;
        }

        if(indexNodo < 0){
            DialogTop.show(activity,"El número del nodo debe ser mayor o igual a 0",DialogTop.PRECAUTION);
            return false;
        }

        if(treeSelect == Manager.LOUD_TREE_1){
            if(indexNodo > 1000){
                DialogTop.show(activity,"El número del nodo debe ser menor o igual a 1.000",DialogTop.PRECAUTION);
                return false;
            }

        }else if(treeSelect == Manager.LOUD_TREE_2){
            if(indexNodo > 10000){
                DialogTop.show(activity,"El número del nodo debe ser menor o igual a 10.000",DialogTop.PRECAUTION);
                return false;
            }
        }else{
            if(indexNodo > 100000){
                DialogTop.show(activity,"El número del nodo debe ser menor o igual a 100.000",DialogTop.PRECAUTION);
                return false;
            }
        }

        return true;
    }

    private void showBottomSheetFragment(Bundle bundle){
        RequestOperationBottomFragment fragment = new RequestOperationBottomFragment();
        fragment.setArguments(bundle);
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
        fragment.show(activity.getSupportFragmentManager(),"RequestOperationBottomFragment");
    }
}