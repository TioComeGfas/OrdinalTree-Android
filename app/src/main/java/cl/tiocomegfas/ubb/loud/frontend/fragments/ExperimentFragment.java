package cl.tiocomegfas.ubb.loud.frontend.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.filter.Filter;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import cl.tiocomegfas.library.backend.parser.Parser;
import cl.tiocomegfas.library.frontend.center_bar.LoadingDialog;
import cl.tiocomegfas.library.frontend.top_bar.DialogTop;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnBuildLoudTreeListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnLoadDataListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryCadenaMandoListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryColegasListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQueryJefeListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnQuerySubordinadosListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnSearchPersonListener;
import cl.tiocomegfas.ubb.loud.backend.listeners.OnTableViewGenerateDataListener;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import cl.tiocomegfas.ubb.loud.backend.tableutil.TableViewAdapter;
import cl.tiocomegfas.ubb.loud.backend.tableutil.TableViewListener;
import cl.tiocomegfas.ubb.loud.backend.tableutil.TableViewModel;
import cl.tiocomegfas.ubb.loud.backend.tableutil.model.Cell;
import cl.tiocomegfas.ubb.loud.controller.Manager;
import cl.tiocomegfas.ubb.loud.controller.Pipe;
import cl.tiocomegfas.ubb.loud.frontend.activities.HomeActivity;
import cl.tiocomegfas.ubb.loud.frontend.bottomsheet.RequestOperationBottomFragment;
import de.blox.graphview.Node;

public class ExperimentFragment extends Fragment {

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

    @BindView(R.id.rb_tree_1)
    RadioButton rb1;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.table)
    TableView tableView;

    private Context context;
    private HomeActivity activity;
    private Unbinder unbinder;
    private AlertDialog dialog;
    private int treeSelect;
    private boolean isLoadJson;
    private final double[][] data = new double[TableViewModel.ROW_SIZE][TableViewModel.COLUMN_SIZE]; //crear datos ficticios


    private final OnQueryJefeListener listenerQueryJefe = new OnQueryJefeListener() {
        @Override
        public void onReady(int parentID, int childID, String parent, String child, double time) {
            activity.runOnUiThread(() -> {
                Bundle bundle = new Bundle();
                bundle.putString("MODE","JEFE");
                bundle.putInt("ID_JEFE", parentID);
                bundle.putInt("ID_CHILD", childID);
                bundle.putString("NAME_JEFE", parent);
                bundle.putString("NAME_CHILD", child);
                bundle.putDouble("TIME",time);
                showBottomSheetFragment(bundle);
            });
        }

        @Override
        public void onError(String message) {
            //dialog.cancel();
        }

        @Override
        public void onRunning() {
            activity.runOnUiThread(() -> {
            });
        }
    };
    private final OnQuerySubordinadosListener listenerQuerySubordinados = new OnQuerySubordinadosListener() {
        @Override
        public void onReady(int[] ids, String[] names, double time) {
            activity.runOnUiThread(() ->{
                Bundle bundle = new Bundle();
                bundle.putString("MODE", "SUBORDINADOS");
                bundle.putIntArray("IDs", ids);
                bundle.putStringArray("NAMEs", names);
                bundle.putDouble("TIME",time);
                showBottomSheetFragment(bundle);
            });
        }

        @Override
        public void onError(String message) {
            activity.runOnUiThread(() ->{

            });
        }

        @Override
        public void onRunning() {
            activity.runOnUiThread(() ->{

            });
        }
    };
    private final OnQueryCadenaMandoListener listenerQueryCadenaMando = new OnQueryCadenaMandoListener() {
        @Override
        public void onReady(int[] ids, String[] persons, double time) {
            activity.runOnUiThread(() ->{
                Bundle bundle = new Bundle();
                bundle.putString("MODE", "CADENA_MANDO");
                bundle.putIntArray("IDs", ids);
                bundle.putStringArray("NAMEs", persons);
                bundle.putDouble("TIME",time);
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
    private final OnQueryColegasListener listenerQueryColegas = new OnQueryColegasListener() {
        @Override
        public void onReady(int[] colegasID, String[] colegas, double time) {
            activity.runOnUiThread(() -> {
                Bundle bundle = new Bundle();
                bundle.putString("MODE", "COLEGAS");
                bundle.putIntArray("IDs", colegasID);
                bundle.putStringArray("NAMEs", colegas);
                bundle.putDouble("TIME",time);
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
    private final OnTableViewGenerateDataListener listenerGenerateDataListener = new OnTableViewGenerateDataListener() {
        @Override
        public void onTimeFirstChild(double timeTree1, double timeTree2, double timeTree3) {
            activity.runOnUiThread(() -> {
                data[0][0] = timeTree1;
                data[0][1] = timeTree2;
                data[0][2] = timeTree3;
            });
        }

        @Override
        public void onTimeNextSibling(double timeTree1, double timeTree2, double timeTree3) {
            activity.runOnUiThread(() -> {
                data[1][0] = timeTree1;
                data[1][1] = timeTree2;
                data[1][2] = timeTree3;
            });
        }

        @Override
        public void onTimeParent(double timeTree1, double timeTree2, double timeTree3) {
            activity.runOnUiThread(() -> {
                data[2][0] = timeTree1;
                data[2][1] = timeTree2;
                data[2][2] = timeTree3;
            });
        }

        @Override
        public void onTimeData(double timeTree1, double timeTree2, double timeTree3) {
            activity.runOnUiThread(() -> {
                data[3][0] = timeTree1;
                data[3][1] = timeTree2;
                data[3][2] = timeTree3;
            });
        }

        @Override
        public void onReady() {
            activity.runOnUiThread(() -> {
                dialog.cancel();
                initTable();
            });
        }

        @Override
        public void onRunning() {
            activity.runOnUiThread(() -> {
                //informa al usuario que se estan realizando pruebas de rendimiento

                dialog = LoadingDialog.show(context,"Prueba de rendimiento...");
                dialog.show();
            });
        }
    };
    private final OnSearchPersonListener listenerSearchPerson = new OnSearchPersonListener() {
        @Override
        public void onRunning() {

        }

        @Override
        public void onReady(int position, String name, double time) {
            activity.runOnUiThread(() -> {
                Bundle bundle = new Bundle();
                bundle.putString("MODE","SEARCH");
                bundle.putInt("ID", position);
                bundle.putString("NAME", name);
                bundle.putDouble("TIME",time);
                showBottomSheetFragment(bundle);
            });
        }

        @Override
        public void onError(String message) {

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

        this.treeSelect = -1;
        rb1.setChecked(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_start_test)
    void onClickLoadJson(){
        Pipe.getInstance().callTableViewGenerateData(listenerGenerateDataListener);
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
        Manager.getInstance().getSubordinados(treeSelect,indexNodo, listenerQuerySubordinados);
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
        Manager.getInstance().getJefe(treeSelect,indexNodo,listenerQueryJefe);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.bt_search_bnodo)
    void onClickBuscarNodo(){
        if(TextUtils.isEmpty(etBuscarNodo.getText().toString())){
            DialogTop.show(activity,"Recuerde ingresar el número del nodo",DialogTop.PRECAUTION);
            return;
        }
        String nameStr = etBuscarNodo.getText().toString();

        if(!nameStr.contains(" ")){
            DialogTop.show(activity,"Antes de continuar, ingrese el apellido",DialogTop.PRECAUTION);
            return;
        }

        if(nameStr.split(" ").length != 2) {
            DialogTop.show(activity,"Debe ingresar el nombre y el apellido",DialogTop.PRECAUTION);
            return;
        }

        Manager.getInstance().searchNodo(treeSelect,nameStr,listenerSearchPerson);
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
        Manager.getInstance().getCadenaDeMando(treeSelect,indexNodo, listenerQueryCadenaMando);
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
        Manager.getInstance().getColegas(treeSelect,indexNodo, listenerQueryColegas);
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

    private void initTable(){
        // Create TableView View model class  to group view models of TableView
        TableViewModel tableViewModel = new TableViewModel();

        // Create TableView Adapter
        TableViewAdapter tableViewAdapter = new TableViewAdapter(tableViewModel);

        tableView.setAdapter(tableViewAdapter);
        tableView.setTableViewListener(new TableViewListener(tableView));

        tableViewAdapter.setAllItems(tableViewModel.getColumnHeaderList(), tableViewModel.getRowHeaderList(), tableViewModel.getCellList(data));
    }

    private boolean checkValues(int indexNodo){

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