package com.example.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.employee.database.AppDatabase;
import com.example.employee.database.entitas.User;

public class AddActivity extends AppCompatActivity {
    private EditText editName, editEmail;
    private Button btnSave;
    private AppDatabase database;
    private int uid = 0;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        editName = findViewById(R.id.name);
        editEmail = findViewById(R.id.email);
        btnSave = findViewById(R.id.btn_save);

        database = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        uid = intent.getIntExtra("uid", 0);
        if(uid > 0){
            isEdit = true;
            User user = database.userDao().get(intent.getIntExtra("uid", 0));
            editName.setText(user.fullname);
            editEmail.setText(user.email);
        } else {
            isEdit = false;
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEdit == true) {
                    database.userDao().update(uid, editName.getText().toString(), editEmail.getText().toString());
                } else {
                    database.userDao().insertAll(editName.getText().toString(), editEmail.getText().toString());
                }
                finish();
            }
        });
    }
}