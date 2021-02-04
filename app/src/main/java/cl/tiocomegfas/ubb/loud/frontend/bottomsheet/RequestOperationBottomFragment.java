package cl.tiocomegfas.ubb.loud.frontend.bottomsheet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DecimalFormat;

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

    private HomeActivity activity;
    private Context context;
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
        this.context = getContext();
        this.activity = (HomeActivity) getActivity();

        if(getArguments() == null){
            return;
        }

        Bundle bundle = getArguments();
        String mode = bundle.getString("MODE");

        switch (mode){
            case "JEFE":{
                int tree = bundle.getInt("TREE");
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

                GraphAdapter adapter = new GraphAdapter(graph, new int[] {idJefe, idChild}, names);
                graphView.setAdapter(adapter);

                final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                        .setSiblingSeparation(50)
                        .setLevelSeparation(50)
                        .setSubtreeSeparation(100)
                        .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                        .build();
                graphView.setLayout(new BuchheimWalkerAlgorithm(configuration));

                DecimalFormat df = new DecimalFormat("0.000000");
                tvTime.setText(df.format(time) + " segundos");

                break;
            } default:
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