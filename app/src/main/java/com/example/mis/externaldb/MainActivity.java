package com.example.mis.externaldb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mis.externaldb.databaseservcices.ConnectServer;

import java.sql.*;

public class MainActivity extends AppCompatActivity {
    private ConnectServer connectServer;
    private TextView textView;
    private Button sendMail;

    private static final String url = "jdbc:jtds:sqlserver://52.37.93.94:1433/LogicalWings_Test";
    private static final String user = "sa";
    private static final String pass = "test#123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        sendMail = findViewById(R.id.button_send_mail);
        Toast.makeText(MainActivity.this, "OnCreate", Toast.LENGTH_SHORT).show();

        testConnection();

        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(MainActivity.this, SendMailActivity.class);
                startActivity(i);*/
            }
        });
    }

    private void testConnection()
    {
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(url, user, pass);
            String result = "Database Connection";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Emp");
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            while (rs.next()) {
                result += resultSetMetaData.getColumnName(2) + "" + rs.getString("Name") + "\n";
//                Log.e("data", field);
            }
            textView.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("first", e.getMessage());
            textView.setText(e.getMessage());
        }
    }

   /*static class ConnectServer extends AsyncTask<String, String, String> {
       ProgressDialog mProgressDialog;
       Context context;
       private String url;

        public ConnectServer(Context context, String url) {
            this.context = context;
            this.url = url;
        }

       @Override
       protected void onPreExecute() {
           mProgressDialog = ProgressDialog.show(context, "",
                   "Please wait, getting database...");
       }

       @Override
       protected String doInBackground(String... strings) {
           try {
               Class.forName("com.mysql.jdbc.Driver");
               Connection con = DriverManager.getConnection(url, user, pass);
               Statement st = con.createStatement();
               ResultSet rs = st.executeQuery("select * from emp");

               while (rs.next()) {
                   String field= rs.getString("name");
                   Log.e("data", field);
               }
           } catch (SQLException e) {
               e.printStackTrace();
               Log.e("first", e.getMessage());
           } catch (ClassNotFoundException e) {
               Log.e("second", e.getMessage());
               e.printStackTrace();
           }
           return "Complete";
       }

       @Override
       protected void onPostExecute(String s) {
           if (s.equals("Complete")) {
               mProgressDialog.dismiss();
           } else {
               mProgressDialog.dismiss();
           }
       }
   }*/
}
