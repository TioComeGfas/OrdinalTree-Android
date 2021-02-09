package cl.tiocomegfas.ubb.loud.frontend.bottomsheet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.model.Person;
import cl.tiocomegfas.ubb.loud.controller.Manager;
import cl.tiocomegfas.ubb.loud.frontend.activities.HomeActivity;
import cl.tiocomegfas.ubb.loud.frontend.adapter.GraphAdapter;
import de.blox.graphview.Graph;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;

public class RequestOperationBottomFragment extends BottomSheetDialogFragment {

    @BindView(R.id.tv_title_request)
    TextView tvTtileRequest;

    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.graph_view)
    GraphView graphView;

    private Unbinder unbinder;
    private Graph graph;

    public RequestOperationBottomFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_operation_bottom, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() == null){
            return;
        }

        Bundle bundle = getArguments();
        String mode = bundle.getString("MODE");
        Log.e("TAG", mode);

        switch (mode){
            case "JEFE":{
                int idJefe = bundle.getInt("ID_JEFE");
                int idChild = bundle.getInt("ID_CHILD");
                String nameJefe = bundle.getString("NAME_JEFE");
                String nameChild = bundle.getString("NAME_CHILD");
                double time = bundle.getDouble("TIME");

                String[] names = new String[2];
                names[0] = nameJefe;
                names[1] = nameChild;

                graph = new Graph();

                Node parent = new Node("Jefe");
                Node child = new Node("Child");

                graph.addEdge(parent,child);

                GraphAdapter adapter = new GraphAdapter(graph, new int[] {idJefe, idChild}, names, null);
                graphView.setAdapter(adapter);

                final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                        .setSiblingSeparation(50)
                        .setLevelSeparation(50)
                        .setSubtreeSeparation(100)
                        .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                        .build();
                graphView.setLayout(new BuchheimWalkerAlgorithm(configuration));

                DecimalFormat df = new DecimalFormat("0.000000");
                String tiempo = "Tiempo de operación: " + df.format(time) + " segundos";
                tvTime.setText(tiempo);
                break;
            }
            case "SUBORDINADOS":{
                int[] ids = bundle.getIntArray("IDs");
                String[] names = bundle.getStringArray("NAMEs");
                double time = bundle.getDouble("TIME");

                graph = new Graph();

                Node parent = new Node("parent");
                for(int i = 1; i < ids.length; i++){
                    graph.addEdge(parent,new Node("child " + i));
                }

                GraphAdapter adapter = new GraphAdapter(graph, ids, names, null);
                graphView.setAdapter(adapter);

                final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                        .setSiblingSeparation(50)
                        .setLevelSeparation(50)
                        .setSubtreeSeparation(100)
                        .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                        .build();
                graphView.setLayout(new BuchheimWalkerAlgorithm(configuration));

                DecimalFormat df = new DecimalFormat("0.000000");
                String tiempo = "Tiempo de operación: " + df.format(time) + " segundos";
                tvTime.setText(tiempo);

                break;
            }
            case "CADENA_MANDO":{
                int[] ids = bundle.getIntArray("IDs");
                String[] names = bundle.getStringArray("NAMEs");
                double time = bundle.getDouble("TIME");

                graph = new Graph();

                Node parent = new Node("parent 0");
                for(int i = 1; i < ids.length; i++){
                    Node child = new Node("child " + i);
                    graph.addEdge(parent,child);

                    parent = child;
                }

                GraphAdapter adapter = new GraphAdapter(graph, ids, names, null);
                graphView.setAdapter(adapter);

                final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                        .setSiblingSeparation(50)
                        .setLevelSeparation(50)
                        .setSubtreeSeparation(100)
                        .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                        .build();
                graphView.setLayout(new BuchheimWalkerAlgorithm(configuration));

                DecimalFormat df = new DecimalFormat("0.000000");
                String tiempo = "Tiempo de operación: " + df.format(time) + " segundos";
                tvTime.setText(tiempo);

                break;
            }
            case "COLEGAS":{
                int[] ids = bundle.getIntArray("IDs");
                String[] names = bundle.getStringArray("NAMEs");
                double time = bundle.getDouble("TIME");

                graph = new Graph();

                Node parent = new Node("root");

                for(int i = 1; i < ids.length; i++){
                    Node child = new Node("child " + i);
                    graph.addEdge(parent,child);
                }

                GraphAdapter adapter = new GraphAdapter(graph, ids, names, null);
                graphView.setAdapter(adapter);

                final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                        .setSiblingSeparation(50)
                        .setLevelSeparation(50)
                        .setSubtreeSeparation(100)
                        .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                        .build();
                graphView.setLayout(new BuchheimWalkerAlgorithm(configuration));

                DecimalFormat df = new DecimalFormat("0.000000");
                String tiempo = "Tiempo de operación: " + df.format(time) + " segundos";
                tvTime.setText(tiempo);

                break;
            }
            case "SEARCH":{
                int id = bundle.getInt("ID");
                String name = bundle.getString("NAME");
                double time = bundle.getDouble("TIME");

                graph = new Graph();

                Node parent = new Node("root");

                graph.addNode(parent);

                GraphAdapter adapter = new GraphAdapter(graph, new int[]{id},new String[]{name}, null);
                graphView.setAdapter(adapter);

                final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                        .setSiblingSeparation(50)
                        .setLevelSeparation(50)
                        .setSubtreeSeparation(100)
                        .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                        .build();
                graphView.setLayout(new BuchheimWalkerAlgorithm(configuration));

                DecimalFormat df = new DecimalFormat("0.000000");
                String tiempo = "Tiempo de operación: " + df.format(time) + " segundos";
                tvTime.setText(tiempo);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        getArguments().clear();
    }

}