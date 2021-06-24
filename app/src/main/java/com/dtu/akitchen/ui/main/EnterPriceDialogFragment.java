package com.dtu.akitchen.ui.main;
//Philip Hviid
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dtu.akitchen.R;
import com.dtu.akitchen.ShoppingListItems.BoughtItem;
import com.dtu.akitchen.ShoppingListItems.DAOboughtItem;
import com.dtu.akitchen.ShoppingListItems.DAOshoppingListItems;
import com.dtu.akitchen.authentication.LogInOut;
import com.dtu.akitchen.kitchen.FirebaseCalls;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EnterPriceDialogFragment extends AppCompatDialogFragment {
    private Context context;
    private EditText price;
    private String itemName;
    private String itemKey;
    DatabaseReference reference;
    ValueEventListener listener;
    Button positiveButton;
    Button negativeButton;

    //need context to make toast, and there is a android bug, with making toast from own context
    public void setContext(Context context) {
        this.context = context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String kitchenId = FirebaseCalls.kitchenId;

        reference = FirebaseDatabase.getInstance().getReference()
                .child("kitchens").child(kitchenId).child("shopping_list");

        //To prevent multiple people buying same item
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Log.i("DialogFrag", itemKey);
                if (null==snapshot.child(itemKey).getValue()) {
                    Log.i("DialogFragment", "key is null");
                    EnterPriceDialogFragment.this.dismiss();
                    Toast.makeText(context, itemName + " removed from list by other user",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };

        reference.addValueEventListener(listener);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_enter_price_dialog, null);
        price = view.findViewById(R.id.price_text);

        builder.setView(view).setTitle(itemName)
                .setNeutralButton(R.string.enter_price_dialog_cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.enter_price_dialog_buy_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("BoughtItem", "trying to buy: " + itemName + "" +
                                " with key:" + itemKey);
                        buyItem(itemName, itemKey, Double.parseDouble(price.getText().toString()));
                    }
                }).setNegativeButton(R.string.enter_price_dialog_delete_button, new DialogInterface.OnClickListener() {
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

    @Override
    public void onStart() {
        super.onStart();

        //set color of negative button for visual clarity
        negativeButton=((AlertDialog) this.getDialog()).getButton(DialogInterface.BUTTON_NEGATIVE);
        negativeButton.setBackgroundColor(Color.RED);
        negativeButton.setTextColor(Color.WHITE);

        //save reference to enable and disable depending on user input
        positiveButton =  ((AlertDialog) this.getDialog())
                .getButton(AlertDialog.BUTTON_POSITIVE);

        //make button only be active when the input is >=0
        positiveButton.setEnabled(false);
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            //only allow user to buy items cheaper than 10000 and with at most 2 decimals
            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s)){
                    positiveButton.setEnabled(false);
                    return;
                }
                try {
                    double priceValue = Double.parseDouble((price.getText().toString()));
                    //to preven huge numbers from crashing database
                    Log.i("InputValidation", ""+ (BigDecimal.valueOf(priceValue).scale()));
                    positiveButton.setEnabled(priceValue >= 0 && priceValue <= 10000
                            && BigDecimal.valueOf(priceValue).scale()<3); //sccuffed check for max 2 decimals
                } catch (NumberFormatException e) {
                    positiveButton.setEnabled(false);
                }
            }
        });


    }

    //this is where items are bought, would be good to refactor into the bought item class
    private void buyItem(String itemName, String itemKey, double price) {
        DAOshoppingListItems shoppingDAO = new DAOshoppingListItems();
        DAOboughtItem boughtDAO = new DAOboughtItem();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date date = new Date();

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

    public void setTitle(String itemName) {
        this.itemName = itemName;
    }
    public void setItemKey(String itemKey) {this.itemKey = itemKey; }

    @Override
    public void onPause() {
        super.onPause();
        reference.removeEventListener(listener);
    }
}