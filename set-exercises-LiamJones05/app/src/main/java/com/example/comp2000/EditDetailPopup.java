package com.example.comp2000;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditDetailPopup extends AppCompatActivity {

    public EditText NewNameInput;
    public EditText NewEmailInput;
    public EditText NewTelephoneInput;
    public EditText NewAddressInput;
    private Button UpdateButton;
    private Button ReturnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_detail_popup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        NewNameInput = findViewById(R.id.Nameinput);
        NewEmailInput = findViewById(R.id.EmailInput);
        NewAddressInput = findViewById(R.id.Addressinput);
        NewTelephoneInput = findViewById(R.id.TelephoneInput);
        ReturnButton = findViewById(R.id.ReturnButton);

        UpdateButton = findViewById(R.id.UpdateButton);
        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NewName = NewNameInput.getText().toString();
                String NewEmail = NewEmailInput.getText().toString();
                String NewAddress = NewAddressInput.getText().toString();
                String NewTelephone = NewTelephoneInput.getText().toString();

                Toast.makeText(EditDetailPopup.this, "Details Updated", Toast.LENGTH_SHORT).show();
                // Create an intent to send updated data back
                Intent returnIntent = new Intent();
                if (NewName != null && !NewName.isEmpty()) {
                    returnIntent.putExtra("NEW_NAME", NewName);
                }
                if (NewEmail != null && !NewEmail.isEmpty()) {
                    returnIntent.putExtra("NEW_EMAIL", NewEmail);
                }
                if (NewAddress != null && !NewAddress.isEmpty()) {
                    returnIntent.putExtra("NEW_ADDRESS", NewAddress);
                }
                if (NewTelephone != null && !NewTelephone.isEmpty()) {
                    returnIntent.putExtra("NEW_TELEPHONE", NewTelephone);
                }
                setResult(RESULT_OK, returnIntent);
                finish(); // Close the popup and return to the previous activity
            }
        });

        // Handle return button click
        ReturnButton.setOnClickListener(v -> finish());
    }
}
