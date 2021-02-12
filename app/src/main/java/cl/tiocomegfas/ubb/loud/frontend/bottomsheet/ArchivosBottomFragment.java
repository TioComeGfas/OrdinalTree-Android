package cl.tiocomegfas.ubb.loud.frontend.bottomsheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.backend.constants.Codes;
import cl.tiocomegfas.ubb.loud.frontend.activities.HomeActivity;
import cl.tiocomegfas.ubb.loud.frontend.adapter.FilesAdapter;
import cl.tiocomegfas.ubb.loud.frontend.listeners.OnFilesAdapterListener;

public class ArchivosBottomFragment extends BottomSheetDialogFragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rv_archivos)
    RecyclerView rvArchivos;

    private HomeActivity activity;
    private Context context;
    private Unbinder unbinder;
    private final String[] files = new String[]{ "BitArray.java", "RankSelect.java", "LoudsTree.java", "Chronometer.cpp", "Chronometer.h"};

    private final OnFilesAdapterListener listener = position -> {
        switch (position){
            case 0:{
                activity.setCode(Codes.codeBitArray, "Java");
                break;
            } case 1:{
                activity.setCode(Codes.codeRankSelect,"Java");
                break;
            } case 2:{
                activity.setCode(Codes.codeLoudsTree,"Java");
                break;
            } case 3:{
                activity.setCode(Codes.codeChronometerCpp, "C++");
                break;
            } case 4:{
                activity.setCode(Codes.codeChronometerH, "C++");
                break;
            } default:{
                break;
            }
        }
    };

    public ArchivosBottomFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_archivos_bottom, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();
        this.activity = (HomeActivity) getActivity();

        //new GridLayoutManager(this, numberOfColumns)

        GridLayoutManager layoutManager = new GridLayoutManager(context,3);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rvArchivos.setLayoutManager(layoutManager);

        FilesAdapter filesAdapter = new FilesAdapter(files, listener);
        rvArchivos.setAdapter(filesAdapter);

        rvArchivos.invalidate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}