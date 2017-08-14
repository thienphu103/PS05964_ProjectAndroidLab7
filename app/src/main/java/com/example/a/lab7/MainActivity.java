package com.example.a.lab7;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
        txtdialogname = (TextView) dialog.findViewById(R.id.dialogName);
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
                Log.d("text", edtsearch.getText().toString());
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
                ClassBook a = new ClassBook();
                final String indextext = arrayList.get(index).getId() + "";
                ;
                Log.d("index", index + "  " + indextext);
                txtdialogname = (TextView) dialog.findViewById(R.id.dialogName);
//                final String namedialog=indextext.substring(indextext.indexOf("\n")+1,indextext.indexOf("\n"));
                DialogBook();
                btndialogdel.setVisibility(View.VISIBLE);
                dialog.setTitle("Update and Delete");
                txtdialogname.setText(arrayList.get(index).getTilte());
                btndialogdel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String sql = database.deleteBook(indextext);
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Delete");
                        builder.setMessage("Can You Detele " + arrayList.get(index).getTilte() + " Book ?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                database.QueryData(sql);
                                Toast.makeText(getApplicationContext(), "Delete OK", Toast.LENGTH_SHORT).show();
                                database.getDataBook("");
                                arrayAdapter.notifyDataSetChanged();
                            }

                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();
                        database.getDataBook("");
                        arrayList.clear();
                        arrayAdapter.notifyDataSetChanged();
                        showlist("");
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
                        final String sql = database.updateBook(Integer.parseInt(id), tilte, author, price, indextext);
                        try {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Update");
                            builder.setMessage("Can You Update " + arrayList.get(index).getTilte() +" to "+tilte+"  Book ?");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    database.QueryData(sql);
                                    Toast.makeText(getApplicationContext(), "Update OK", Toast.LENGTH_SHORT).show();
                                    database.getDataBook("");
                                    arrayAdapter.notifyDataSetChanged();
                                }

                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();
                        } catch (Exception ex) {
                            AlertErrorDialog();
                        }
//                arrayList.add(new ClassBook(Integer.parseInt(id),tilte,author,price));
                        database.getDataBook("");
                        arrayList.clear();
                        arrayAdapter.notifyDataSetChanged();
                        showlist("");
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
                final String sql = database.addBook(Integer.parseInt(id), tilte, author, price);
                    try {
                        database.QueryData(sql);
                        Toast.makeText(getApplicationContext(), "Add OK", Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        AlertErrorDialog();
                    }

//                arrayList.add(new ClassBook(Integer.parseInt(id),tilte,author,price));
                    database.getDataBook("");
                    arrayList.clear();
                    arrayAdapter.notifyDataSetChanged();
                    showlist("");
                    dialog.dismiss();
                }

//              database.QueryData(database.addBook(new ClassBook(0,tilte,author,price)));

        });
        dialog.show();
    }

public void AlertErrorDialog(){
    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    builder.setTitle("Error");
    builder.setMessage("ID Exist, Please input other Id.Tks");
    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
    });
    builder.show();
}
    public void showlist(String name) {
        if (name.equals("")) {
            arrayList = database.getDataBook("");
        } else {
            arrayList = database.getSearchBook(edtsearch.getText().toString());
        }
        arrayAdapter = new ArrayAdapter<ClassBook>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

    }


}





