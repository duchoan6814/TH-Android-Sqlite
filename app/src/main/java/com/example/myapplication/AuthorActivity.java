package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AuthorActivity extends AppCompatActivity {
    private Button btnExit;
    private Button btnSelect;
    private Button btnSave;
    private Button btnDelete;
    private Button btnUpdate;

    private EditText edtID;
    private EditText edtName;
    private EditText edtAddress;
    private EditText edtEmail;

    private GridView grv_main;

    private DatabaseHelper databaseHelper;

    private List<Author> authorArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);

        btnExit = findViewById(R.id.btn_exit);
        btnSelect = findViewById(R.id.btn_select);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);

        edtID = findViewById(R.id.edt_id);
        edtName = findViewById(R.id.edt_name);
        edtAddress = findViewById(R.id.edt_address);
        edtEmail = findViewById(R.id.edt_email);

        grv_main = findViewById(R.id.grv_main);

        databaseHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(v -> handleBtnSave());
        btnSelect.setOnClickListener(v -> handleBtnSelect());
        btnDelete.setOnClickListener(v -> handleBtnDelete());
        btnUpdate.setOnClickListener(v -> handleBtnUpdate());
    }

    private void handleBtnUpdate() {

        Author author = new Author();
        author.setId(Integer.parseInt(edtID.getText().toString()));
        author.setName(String.valueOf(edtName.getText()));
        author.setAddress(String.valueOf(edtAddress.getText()));
        author.setEmail(String.valueOf(edtEmail.getText()));
        boolean state = databaseHelper.updateAuthorById(author);
        if(state) {
            Toast.makeText(getApplicationContext(), "Sửa thông tin author thành công.", Toast.LENGTH_LONG).show();
            edtID.setText("");
            handleBtnSelect();
        }else {
            Toast.makeText(getApplicationContext(), "Sửa thông tin author không thành công, vui lòng kiểm tra lại id", Toast.LENGTH_LONG).show();
        }
    }

    private void handleBtnDelete() {
        if (edtID.getText().toString() == "")
            Toast.makeText(getApplicationContext(), "Xoa khong thanh cong, vui long nhap id can xoa", Toast.LENGTH_LONG).show();
        else {
            boolean state = databaseHelper.removeAuthorById(edtID.getText().toString());
            if (state) {
                Toast.makeText(getApplicationContext(), "Xoa thanh cong", Toast.LENGTH_LONG).show();
                edtID.setText("");
                handleBtnSelect();
            }else {
                Toast.makeText(getApplicationContext(), "Xoa khong thanh cong, id khong ton tai", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void handleBtnSelect() {

        ArrayList<String> stringArrayList = new ArrayList<>();

        if (edtID.getText().equals(""))
            authorArrayList = databaseHelper.getAllAuthor();
        else
            try {
                authorArrayList = databaseHelper.getIdAuthor(Integer.parseInt(edtID.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
                authorArrayList = databaseHelper.getAllAuthor();
            }

        System.out.println(authorArrayList);

        for (Author i : authorArrayList) {
            stringArrayList.add(i.getId() + "");
            stringArrayList.add(i.getName());
            stringArrayList.add(i.getAddress());
            stringArrayList.add(i.getEmail());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(AuthorActivity.this, android.R.layout.simple_list_item_1, stringArrayList);
        grv_main.setAdapter(adapter);
    }

    private void handleBtnSave() {
        Author author = new Author();
        author.setId(Integer.parseInt(edtID.getText().toString()));
        author.setName(String.valueOf(edtName.getText()));
        author.setAddress(String.valueOf(edtAddress.getText()));
        author.setEmail(String.valueOf(edtEmail.getText()));

        if (databaseHelper.insertAuthor(author) > 0) {
            Toast.makeText(getApplicationContext(), "Da luu thanh cong", Toast.LENGTH_LONG).show();
            edtID.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "luu khong thanh cong", Toast.LENGTH_LONG).show();
        }

    }
}