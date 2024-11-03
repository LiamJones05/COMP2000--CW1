package com.example.comp2000;

import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.content.Intent;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onAdminLoginClick(View view){
        Toast.makeText(this,"Login As Admin", Toast.LENGTH_SHORT).show();
    }

    public void onEmployeeLoginClick(View view){
        Intent intent = new Intent(this, EmployeeLoginActivity.class);
        startActivity(intent);
    }
}