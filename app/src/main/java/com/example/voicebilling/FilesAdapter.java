package com.example.voicebilling;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {

    ArrayList<String[]> file = new ArrayList<>();
    Context context ;
    public FilesAdapter(ArrayList<String[]> arrayList,Context context){
        this.file = arrayList;
        this.context = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.file_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.file_name.setText(file.get(position)[0]);
            Log.d("getFile",file.get(position)[1]);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    viewpdf(file.get(position)[1]);
                }
            });
    }


    @Override
    public int getItemCount() {
        return file.size();
    }

    private void viewpdf(String file) {
        // add the link of pdf
//        File filqe = new File(Environment.getExternalStorageDirectory(), "VoiceBilling/VoiceBilling.pdf");
//        Log.d("pdfFIle", "" + file);

        // Get the URI Path of file.
//        Uri uriPdfPath = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", filqe);
//        Log.d("pdfPath", "" + uriPdfPath);

        String value=file;
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(value), "application/pdf");
        // start activity
//        context.startActivity(intent);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView file_name;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            file_name = itemView.findViewById(R.id.filename);
            button = itemView.findViewById(R.id.showbtn);
        }
    }
}
