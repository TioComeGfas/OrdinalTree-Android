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
                int idDefault = bundle.getInt("ID_DEFAULT");
                String jefe = bundle.getString("PERSON_JEFE");
                String _default = bundle.getString("PERSON_DEFAULT");

                graph = new Graph();

                Node parent = new Node("Jefe");
                Node child = new Node("Default");

                graph.addEdge(parent,child);

                GraphAdapter adapter = new GraphAdapter(graph, Manager.getInstance().getPersons(tree,idJefe,idDefault));
                graphView.setAdapter(adapter);

                final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                        .setSiblingSeparation(50)
                        .setLevelSeparation(50)
                        .setSubtreeSeparation(100)
                        .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                        .build();
                graphView.setLayout(new BuchheimWalkerAlgorithm(configuration));

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