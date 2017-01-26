package com.youtube.sorcjc.incidencias.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.youtube.sorcjc.incidencias.Global;
import com.youtube.sorcjc.incidencias.R;
import com.youtube.sorcjc.incidencias.io.IncidentApiAdapter;
import com.youtube.sorcjc.incidencias.io.response.LoginResponse;
import com.youtube.sorcjc.incidencias.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Callback<LoginResponse> {

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        writeLastAuthenticatedUser();
    }

    private void writeLastAuthenticatedUser() {
        etUsername.setText(Global.getFromSharedPreferences(this, "username"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                Call<LoginResponse> call = IncidentApiAdapter.getApiService().postLogin(username, password);
                call.enqueue(this);

                Global.saveInSharedPreferences(this, "username", username);
                break;
        }
    }


    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        if (response.isSuccessful()) {
            LoginResponse loginResponse = response.body();
            User user = loginResponse.getUser();

            if (loginResponse.isError()) {
                // Error message
                Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            } else {
                // Save preferences
                Global.saveInSharedPreferences(this, "id", user.getId());
                Global.saveInSharedPreferences(this, "name", user.getName());

                // Start panel activity
                Intent intentPanel = new Intent(this, PanelActivity.class);
                startActivity(intentPanel);

                // Welcome message
                Toast.makeText(this, "Bienvenido, "+user.getName(), Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        Toast.makeText(this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }
}
