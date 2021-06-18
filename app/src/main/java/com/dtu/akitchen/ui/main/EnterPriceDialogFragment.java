package com.dtu.akitchen.ui.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dtu.akitchen.R;


public class EnterPriceDialogFragment extends AppCompatDialogFragment {
    private EditText price;
    private String itemName;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_enter_price_dialog, null);
        price = view.findViewById(R.id.price_text);

        builder.setView(view).setTitle(itemName)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        buyItem(itemName, price.getText().toString());
                    }
                })
                .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }



    private void buyItem(String itemName, String price) {
    }

    public EditText getPrice() {
        return price;
    }

    public void setTitle(String itemName) {
        this.itemName = itemName;
    }

}