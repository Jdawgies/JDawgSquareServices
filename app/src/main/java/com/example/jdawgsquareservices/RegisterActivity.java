package com.example.jdawgsquareservices;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.square.Environment;
import com.squareup.square.SquareClient;
import com.squareup.square.api.CustomersApi;
import com.squareup.square.models.CreateCustomerRequest;

public class RegisterActivity extends AppCompatActivity {

    TextView alreadyHaveAccount;
    EditText inputPhone,inputPassword,inputConfirmPassword;
    Button btnRegister;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        alreadyHaveAccount=findViewById(R.id.alreadyHaveAccount);

        inputPhone=findViewById(R.id.inputPhoneNumber);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        btnRegister=findViewById(R.id.btnRegister);
        progressDialog=new ProgressDialog(this);

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });


    }

    private void PerforAuth() {
        String phone=inputPhone.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmPassword=inputConfirmPassword.getText().toString();

        if(password.isEmpty() || password.length()<6) {
            inputPassword.setError("Enter Proper Password");
        } else if (!password.equals(confirmPassword)) {
            inputConfirmPassword.setError("Passwords Don't Match");
        }else {
            progressDialog.setMessage("Please Wait for Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            SquareClient client = new SquareClient.Builder().environment(Environment.SANDBOX)
                    .accessToken("EAAAENKnobWMVkSVGJH-T6WZkYDKDNsJDtYNreKQFW45YV4-WCWKITO9mpeuKeSx").build();

            CustomersApi customersApi = client.getCustomersApi();

            CreateCustomerRequest body = new CreateCustomerRequest.Builder()
                    .phoneNumber(phone)
                    .note(password)
                    .idempotencyKey(UUID.randomUUID().toString())
                    .build();

            customersApi.createCustomerAsync(body)
                    .thenAccept(result -> {
                        System.out.printf("customer created:\n Given name = %s Family name = %s",
                                result.getCustomer().getPhoneNumber(),
                                result.getCustomer().getNote());
                        progressDialog.cancel();
                    })
                    .exceptionally(exception -> {
                        // Your error-handling code here
                        return null;
                    })
                    .join();

        }
    }
}