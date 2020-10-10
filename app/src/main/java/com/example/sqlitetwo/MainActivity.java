package com.example.sqlitetwo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    EditText eName, eSurname, eMarks, eID;
    Button btnAdd, btnView, btnUpdate, btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb = new DatabaseHelper(this);
        eName = (EditText)findViewById(R.id.edit_name);
        eSurname = (EditText)findViewById(R.id.edit_surname);
        eMarks= (EditText)findViewById(R.id.edit_marks);
        eID = (EditText)findViewById(R.id.edit_id);
        btnAdd = (Button)findViewById(R.id.btn_add);
        btnView = (Button)findViewById(R.id.btn_view);
        btnUpdate = (Button)findViewById(R.id.btn_update);
        btnDelete = (Button)findViewById(R.id.btn_delete);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             String name = eName.getText().toString().trim();
             String surname = eSurname.getText().toString().trim();
             String marks = eMarks.getText().toString().trim();
             btnAdd.setEnabled(!name.isEmpty() && !surname.isEmpty() && !marks.isEmpty());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        eName.addTextChangedListener(textWatcher);
        eSurname.addTextChangedListener(textWatcher);
        eMarks.addTextChangedListener(textWatcher);
        addData();
        viewAll();
        updateData();
        deleteData();



    }
    public void addData(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    boolean isInserted = mydb.insertData(eName.getText().toString(),eSurname.getText().toString(),eMarks.getText().toString());
                    if(isInserted == true)
                        Toast.makeText(getApplicationContext(),"Data inserted",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(),"Data not inserted",Toast.LENGTH_SHORT).show();
                    eName.setText("");
                    eSurname.setText("");
                    eMarks.setText("");

                }



        });
    }

    public void viewAll(){
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getAllData();
                if (res.getCount() == 0) {
                    showMessage("Error", "No Data found");
                    return;
                }
                else {
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("id : " + res.getString(0)+"\n");
                        buffer.append("name : " + res.getString(1)+"\n");
                        buffer.append("surname : " + res.getString(2)+"\n");
                        buffer.append("marks : " + res.getString(3)+"\n\n");
                    }
                    showMessage("Data : ", buffer.toString());
                }


            }
        });
    }

    public void showMessage( String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void updateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = mydb.updateData(eID.getText().toString(),eName.getText().toString(),eSurname.getText().toString(),eMarks.getText().toString());
                if(isUpdated == true){
                    Toast.makeText(getApplicationContext(), "DATA UPDATED", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "DATA UPDATED", Toast.LENGTH_SHORT).show();


            }
        });
    }

   public void deleteData()
{btnDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Integer deletedRows = mydb.deleteData(eID.getText().toString());
        if (deletedRows > 0) {
            Toast.makeText(getApplicationContext(), "DATA DELETED", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(getApplicationContext(), "DATA NOT DELETED", Toast.LENGTH_SHORT).show();
        eID.setText("");
    }
});

}

}



