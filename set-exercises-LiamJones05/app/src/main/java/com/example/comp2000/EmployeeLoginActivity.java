package com.example.comp2000;

import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EmployeeLoginActivity extends AppCompatActivity {

    private EditText EmailInput;
    private EditText PasswordInput;
    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        EmailInput = findViewById(R.id.EmployeeEmailLogin);
        PasswordInput = findViewById(R.id.EmployeePasswordLogin);
        submitButton = findViewById(R.id.EmployeeLoginbutton);

        String[] EmailList = {"user","user@gmail.com","Account@outlook.com"};
        String[] PasswordList = {"pass","Password1!","LetMe1n!"};

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String Email = EmailInput.getText().toString();
                String Password = PasswordInput.getText().toString();
                if(checkCredentials(EmailList, PasswordList, Email, Password)){
                    Intent intent = new Intent(EmployeeLoginActivity.this, EmployeeHomeScreen.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(EmployeeLoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public static boolean checkCredentials(String[] usernames, String[] passwords, String username, String password) {
        for (int i = 0; i < usernames.length; i++) {
            if (usernames[i].equals(username) && passwords[i].equals(password)) {
                return true;
            }
        }
        return false;
    }

}

