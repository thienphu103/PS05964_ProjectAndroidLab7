package com.example.a.lab7;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    public ArrayList<ClassBook> arrayList;
    ArrayAdapter arrayAdapter;
    Dialog dialog;
    SQLite database;
    Button btnadd;
    EditText edtsearch;
    TextView txtdialogname;
    Button btndialogup;
    Button btnsearch;
    Button btndialogdel;
    EditText edtdialogID;
    EditText edtdialogTilte;
    EditText edtdialogAuthor;
    EditText edtdialogPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Book");
        //AX
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog);
        database = new SQLite(MainActivity.this, "Book2.sqlite", null, 1);
        arrayList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listviewbook);
        btnadd = (Button) findViewById(R.id.btnAdd);
        edtsearch = (EditText) findViewById(R.id.edtSearch);
        edtdialogID = (EditText) findViewById(R.id.dialogID);
        edtdialogTilte = (EditText) findViewById(R.id.dialogTILTE);
        edtdialogAuthor = (EditText) findViewById(R.id.dialogAUTHOR);
        edtdialogPrice = (EditText) findViewById(R.id.dialogPRICE);
        btndialogup = (Button) findViewById(R.id.dialogbtnUP);
        btndialogdel = (Button) findViewById(R.id.dialogbtnDEL);
        btnsearch = (Button) findViewById(R.id.btnSearch);
        txtdialogname=(TextView) dialog.findViewById(R.id.dialogName);
//        database.getDataBook("");
        arrayList.clear();
        showlist("");
        listView.setAdapter(arrayAdapter);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogBook();

                btndialogup.setText("Add");
                txtdialogname.setText("");
                btndialogdel.setVisibility(View.INVISIBLE);

            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("text",edtsearch.getText().toString());
                arrayList.clear();
                arrayAdapter.notifyDataSetChanged();
//                database.getSearchBook("");
                showlist(edtsearch.getText().toString());
                Toast.makeText(getApplicationContext(), "Search OK", Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int index = i;
                final String indextext = arrayList.get(index).toString();
                final String deltext = indextext.substring(0, indextext.indexOf("-")).trim();
//                Log.d("index",index+"   "+deltext+"   "+indextxt);

                txtdialogname=(TextView) dialog.findViewById(R.id.dialogName);
                final String namedialog=indextext.substring(indextext.indexOf("-")+1,indextext.indexOf("--"));
                DialogBook();
                btndialogdel.setVisibility(View.VISIBLE);
                dialog.setTitle("Update and Delete");

                txtdialogname.setText(namedialog);
                btndialogdel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String sql = database.deleteBook(deltext);
                        database.QueryData(sql);
                        database.getDataBook("");
                        arrayList.clear();
                        arrayAdapter.notifyDataSetChanged();
                        showlist("");
                        Toast.makeText(getApplicationContext(), "Delete OK", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                btndialogup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String id = edtdialogID.getText().toString();
                        String tilte = edtdialogTilte.getText().toString();
                        String author = edtdialogAuthor.getText().toString();
                        String price = edtdialogPrice.getText().toString();
                        String sql = database.updateBook(Integer.parseInt(id), tilte, author, price,deltext);
                        database.QueryData(sql);
//                arrayList.add(new ClassBook(Integer.parseInt(id),tilte,author,price));
                        database.getDataBook("");
                        arrayList.clear();
                        arrayAdapter.notifyDataSetChanged();
                        showlist("");
                        Toast.makeText(getApplicationContext(), "Update OK", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

//              database.QueryData(database.addBook(new ClassBook(0,tilte,author,price)));
                    }
                });

                return false;

            }
        });

    }


    public void DialogBook() {
        dialog.setTitle("Add Book");
        edtdialogID = (EditText) dialog.findViewById(R.id.dialogID);
        edtdialogTilte = (EditText) dialog.findViewById(R.id.dialogTILTE);
        edtdialogAuthor = (EditText) dialog.findViewById(R.id.dialogAUTHOR);
        edtdialogPrice = (EditText) dialog.findViewById(R.id.dialogPRICE);
        btndialogup = (Button) dialog.findViewById(R.id.dialogbtnUP);
        btndialogdel = (Button) dialog.findViewById(R.id.dialogbtnDEL);
        edtdialogID.setText("");
        edtdialogPrice.setText("");
        edtdialogTilte.setText("");
        edtdialogAuthor.setText("");
        btndialogup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtdialogID.getText().toString();
                String tilte = edtdialogTilte.getText().toString();
                String author = edtdialogAuthor.getText().toString();
                String price = edtdialogPrice.getText().toString();
                String sql = database.addBook(Integer.parseInt(id), tilte, author, price);
                database.QueryData(sql);
//                arrayList.add(new ClassBook(Integer.parseInt(id),tilte,author,price));
                database.getDataBook("");
                arrayList.clear();
                arrayAdapter.notifyDataSetChanged();
                showlist("");
                Toast.makeText(getApplicationContext(), "Add OK", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

//              database.QueryData(database.addBook(new ClassBook(0,tilte,author,price)));
            }
        });
        dialog.show();
    }

    public void showlist(String name) {
        if(name.equals("")) {
            arrayList = database.getDataBook("");
        }
        else{
            arrayList = database.getSearchBook(edtsearch.getText().toString());
        }
        arrayAdapter = new ArrayAdapter<ClassBook>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

    }


}





