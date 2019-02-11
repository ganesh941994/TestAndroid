package com.example.mis.externaldb.databaseservcices;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;
import com.example.mis.externaldb.MainActivity;


import java.sql.*;

public class ConnectServer extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... args) {

        try {

            connect();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String file_url) {

    }


    private void connect() {
        Log.e("Android", "SQL Connection start");

        Connection conn;

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            String ip = "52.37.93.94";
            String dbName = "LogicalWings_Test";
            String user = "sa";
            String password = "test#123";

            String connString = "jdbc:mysql://" + ip + "/" + dbName;

            conn = DriverManager.getConnection(connString, user, password);

            Log.e("Connection", "Open");
            Statement statement = conn.createStatement();
            ResultSet set = statement.executeQuery("Select * from Emp");
            ResultSetMetaData resultSetMetaData = set.getMetaData();

            while (set.next()) {

                Log.e("Data:", resultSetMetaData.getColumnName(1));
            }
        } catch (Exception e) {
            Log.e("Error connection", e.toString());
        }

    }
}


