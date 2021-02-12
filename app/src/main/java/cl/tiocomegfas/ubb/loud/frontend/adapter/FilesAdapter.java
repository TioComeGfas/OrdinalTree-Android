package cl.tiocomegfas.ubb.loud.frontend.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cl.tiocomegfas.ubb.loud.R;
import cl.tiocomegfas.ubb.loud.frontend.listeners.OnFilesAdapterListener;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.Holder> {

    private final OnFilesAdapterListener listener;
    private final String[] files;

    public FilesAdapter(final String[] files, OnFilesAdapterListener listener){
        this.files = files;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_files, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        try{
            holder.bind(files[position]);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if(files == null) return 0;
        return files.length;
    }

    class Holder extends RecyclerView.ViewHolder{

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.iv_icon_file)
        ImageView ivIcon;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tv_name_file)
        TextView tvNameFile;

        @SuppressLint("NonConstantResourceId")
        @BindDrawable(R.drawable.ic_file_cpp)
        Drawable fileCpp;

        @SuppressLint("NonConstantResourceId")
        @BindDrawable(R.drawable.ic_file_h)
        Drawable fileH;

        @SuppressLint("NonConstantResourceId")
        @BindDrawable(R.drawable.ic_file_dat)
        Drawable fileDat;

        @SuppressLint("NonConstantResourceId")
        @BindDrawable(R.drawable.ic_file_java)
        Drawable fileJava;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void bind(String value){
            //asignacion del nombre
            tvNameFile.setText(value);

            //asignacion del icono
            if(value.contains(".cpp")){
                ivIcon.setImageDrawable(fileCpp);
            }else if(value.contains(".h")){
                ivIcon.setImageDrawable(fileH);
            }else if(value.contains(".java")){
                ivIcon.setImageDrawable(fileJava);
            }else {
                //un icono por defecto en caso
                // de no ser de tipo .h o .cpp
                ivIcon.setImageDrawable(fileDat);
            }
        }

        @OnClick({R.id.iv_icon_file, R.id.tv_name_file})
        void onClick(){
            listener.onClick(getAdapterPosition());
        }
    }
}
