package cl.tiocomegfas.ubb.loud.frontend.adapter;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.frontend.listeners.OnTreeAdapterListener;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.Holder>{

    private final String[] trees;
    private final OnTreeAdapterListener listener;

    public TreeAdapter(String[] trees, OnTreeAdapterListener listener){
        this.trees = trees;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TreeAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TreeAdapter.Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_trees, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TreeAdapter.Holder holder, int position) {
        try{
            holder.bind(trees[position]);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(trees == null) return 0;
        return trees.length;
    }

    class Holder extends RecyclerView.ViewHolder{

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.iv_icon_file)
        ImageView ivIcon;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_name_file)
        TextView tvNameFile;

        @SuppressLint("NonConstantResourceId")
        @BindDrawable(R.drawable.ic_park)
        Drawable iconTree;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(String tree) {
            //asignacion del nombre
            tvNameFile.setText(tree);
            ivIcon.setImageDrawable(iconTree);
        }

        @OnClick({R.id.iv_icon_file, R.id.tv_name_file})
        void onClick(){
            listener.onClick(getAdapterPosition());
        }
    }

}
