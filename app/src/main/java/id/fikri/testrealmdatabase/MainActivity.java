package id.fikri.testrealmdatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import id.fikri.testrealmdatabase.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private RealmHelper helper;
    private ArrayList<ArticleModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = new ArrayList<>();
        helper = new RealmHelper(MainActivity.this);


        recyclerView = (RecyclerView) findViewById(R.id.rvArticle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutInflater.inflate(R.layout.custom_input_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Edit article");
                alertDialogBuilder.setView(promptView);

                final EditText editId = (EditText) promptView.findViewById(R.id.edit_id);
                final EditText editTitle = (EditText) promptView.findViewById(R.id.edit_title);
                final EditText editDesc = (EditText) promptView.findViewById(R.id.edit_desc);

                editId.setText(String.valueOf(data.get(position).getId()));
                editTitle.setText(data.get(position).getTitle());
                editDesc.setText(data.get(position).getDescription());

                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int artId =  Integer.parseInt(editId.getText().toString());
                                String artTitle =  editTitle.getText().toString();
                                String artDesc =  editDesc.getText().toString();

                                helper.updateArticle(artId, artTitle, artDesc);

                                setRecyclerView();
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();

                setRecyclerView();
            }

            @Override
            public void onLongClick(View view, int position) {
                final int idArticel = data.get(position).getId();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Delete article");
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                helper.deleteData(idArticel);
                                setRecyclerView();
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }
        }));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutInflater.inflate(R.layout.custom_input_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle("Add article");
                alertDialogBuilder.setView(promptView);

                final EditText editId = (EditText) promptView.findViewById(R.id.edit_id);
                final EditText editTitle = (EditText) promptView.findViewById(R.id.edit_title);
                final EditText editDesc = (EditText) promptView.findViewById(R.id.edit_desc);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                int artId = Integer.parseInt(editId.getText().toString());
                                String artTitle =  editTitle.getText().toString();
                                String artDesc =  editDesc.getText().toString();

                                helper.addArticle(artId, artTitle, artDesc);

                                setRecyclerView();
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();

                setRecyclerView();
            }
        });

        setRecyclerView();
    }

    /**
     * set recyclerview with try get data from realm
     */
    public void setRecyclerView() {
        try {
            data = helper.findAllArticle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AdapterArticle adapter = new AdapterArticle(data);
        recyclerView.setAdapter(adapter);
    }
}
