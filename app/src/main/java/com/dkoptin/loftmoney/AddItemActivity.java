package com.dkoptin.loftmoney;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;


public class AddItemActivity extends AppCompatActivity {

    private TextInputEditText editName;
    private TextInputEditText editPrice;
    private Button addButton;
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //auto generated
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            //auto generated
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editName.getText().length() > 0 && editPrice.getText().length() > 0) {
                addButton.setEnabled(true);
                addButton.setTextColor(getResources().getColor(R.color.colorTemp));
                Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_addbutton_enabled);
                addButton.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
            } else {
                addButton.setEnabled(false);
                addButton.setTextColor(getResources().getColor(R.color.colorAccent));
                Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_addbutton_disabled);
                addButton.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        editName = findViewById(R.id.edittext_hint_name);
        editPrice = findViewById(R.id.edittext_hint_price);
        addButton = findViewById(R.id.add_button);

        editName.addTextChangedListener(watcher);
        editPrice.addTextChangedListener(watcher);
    }

    public void onItemAdd(View v) {
        Intent intent = new Intent();
        intent.putExtra("name", editName.getText().toString());
        intent.putExtra("price", editPrice.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

}
