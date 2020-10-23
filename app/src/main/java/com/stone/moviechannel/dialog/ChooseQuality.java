package com.stone.moviechannel.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import com.htetznaing.lowcostvideo.Model.XModel;
import com.stone.moviechannel.databinding.ChooseQualityLayoutBinding;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;

public class ChooseQuality {
    private Context context;
    private ChooseQualityLayoutBinding binding;
    private AlertDialog dialog;

    public ChooseQuality(Context context, ArrayList<XModel> vidURL) {
        this.context = context;
        binding = ChooseQualityLayoutBinding.inflate(LayoutInflater.from(context));
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(binding.getRoot());
        dialog = builder.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, vidURL));


        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

    }

}
