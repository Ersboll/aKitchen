package com.dtu.akitchen.ui.main;
//Philip Hviid
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dtu.akitchen.R;
import com.dtu.akitchen.ShoppingListItems.BoughtItem;
import com.dtu.akitchen.ShoppingListItems.DAOboughtItem;
import com.dtu.akitchen.ShoppingListItems.DAOshoppingListItems;
import com.dtu.akitchen.authentication.LogInOut;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EnterPriceDialogFragment extends AppCompatDialogFragment {
    private Context context;
    private EditText price;
    private String itemName;
    private String itemKey;

    //need context to make toast, and there is a android bug, with makign toast from own context
    public void setContext(Context context) {
        this.context = context;
    }

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
        BoughtItem boughtItem = new BoughtItem(itemKey,itemName,price,
                LogInOut.getCurrentUser().getUid(), dateFormat.format(date));
        boughtDAO.addItem(boughtItem).addOnSuccessListener( suc -> {
            Log.i("BoughtItems", itemName + "bought");
            //delete the bought item fro mthe shopping list, if it is succesfully bought
            shoppingDAO.deleteItem(itemKey);
            //update balance of all active users in the kitchen
            boughtDAO.updateBalances(LogInOut.getCurrentUser().getUid(), price);
            Toast.makeText(context,
                    itemName + " purchased", Toast.LENGTH_SHORT).show();
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