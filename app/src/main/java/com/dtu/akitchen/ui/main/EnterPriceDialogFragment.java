package com.dtu.akitchen.ui.main;
//Philip Hviid
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dtu.akitchen.R;
import com.dtu.akitchen.ShoppingListItems.BoughtItem;
import com.dtu.akitchen.ShoppingListItems.DAOboughtItem;
import com.dtu.akitchen.ShoppingListItems.DAOshoppingListItems;
import com.dtu.akitchen.ShoppingListItems.ShoppingListFragment;
import com.dtu.akitchen.authentication.LogInOut;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EnterPriceDialogFragment extends AppCompatDialogFragment {
    private EditText price;
    private String itemName;
    private String itemKey;


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_enter_price_dialog, null);
        price = view.findViewById(R.id.price_text);

        builder.setView(view).setTitle(itemName)
                .setNeutralButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("BoughtItem", "trying to buy: " + itemName + "" +
                                " with key:" + itemKey);
                        buyItem(itemName, itemKey, Double.parseDouble(price.getText().toString()));
                    }
                }).setNegativeButton("Delete item", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delteItem(itemKey);
            }
        });

        return builder.create();
    }

    private void delteItem(String key) {
        DAOshoppingListItems shoppingDAO = new DAOshoppingListItems();
        shoppingDAO.deleteItem(key);
    }


    private void buyItem(String itemName, String itemKey, double price) {
        DAOshoppingListItems shoppingDAO = new DAOshoppingListItems();
        DAOboughtItem boughtDAO = new DAOboughtItem();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();

        //TODO add real date
        BoughtItem boughtItem = new BoughtItem(itemName,price,
                LogInOut.getCurrentUser().getUid(), dateFormat.format(date));
        boughtDAO.addItem(boughtItem).addOnSuccessListener( suc -> {
            Log.i("BoughtItems", itemName + "bought");
            shoppingDAO.deleteItem(itemKey);
            //TODO implement properly
            boughtDAO.upDateBalances(LogInOut.getCurrentUser().getUid(), price);

        }).addOnFailureListener(err -> {
            Log.i("BoughtItems", err.getMessage());
        });

    }

    public EditText getPrice() {
        return price;
    }

    public void setTitle(String itemName) {
        this.itemName = itemName;
    }
    public void setItemKey(String itemKey) {this.itemKey = itemKey; }

}