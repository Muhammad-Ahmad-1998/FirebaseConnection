package com.example.firebaseconnection;

import static com.google.firebase.database.core.operation.OperationSource.Source.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.compiler.processing.util.Source;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.Source;

public class MainActivity<User> extends AppCompatActivity {

    private Button sendDatabtn;
    private EditText NameEdt, EmailEdt, PassEdt;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NameEdt = findViewById(R.id.idEdtName);
        EmailEdt = findViewById(R.id.idEdtEmail);
        PassEdt = findViewById(R.id.idEdtPass);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("User");

        user = new user();

        sendDatabtn = findViewById(R.id.idBtnSendData);
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting text from our edittext fields.
                String name = NameEdt.getText().toString();
                String email = EmailEdt.getText().toString();
                String pass = PassEdt.getText().toString();

                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(name) && TextUtils.isEmpty(pass)) {
                    Toast.makeText(MainActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(email, name, pass);
                }
            }
        });
    }
    private void addDatatoFirebase(String name, String email, String pass) {
        user.setName(name);
        user.setEmail(email);
        user.setPass(pass);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(user);
                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
