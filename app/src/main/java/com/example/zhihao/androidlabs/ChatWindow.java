package com.example.zhihao.androidlabs;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ChatWindow extends Activity {
    protected static final String ACTIVITY_NAME = "ChatWindow";

    private ListView listView;
    private EditText editText;
    private Button sendButton;

    private String input;
    ChatDatabaseHelper helper;

    SQLiteDatabase database;
    Cursor cursor;
    ArrayList<String> messages;
    private ArrayList<String> msgs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        listView = findViewById(R.id.chatView);
        sendButton = findViewById(R.id.sendButton);
        editText = findViewById(R.id.chatBoxes);
        messages = new ArrayList<>();

        helper = new ChatDatabaseHelper(this);
        database = helper.getWritableDatabase();

        Toast.makeText(this, "made it", Toast.LENGTH_SHORT).show();
        cursor = database.query(helper.TABLE_NAME, null,null,null,null,null,null);
        Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());

        while(cursor.moveToNext()){
            final String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.COLUMN_MESSAGE));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + message );
            messages.add(message);
        }

        for (int x = 0; x < cursor.getColumnCount(); x++) {
            Log.i(ACTIVITY_NAME, "Cursor’s  column name =" + cursor.getColumnName(x));
        }

        final ChatAdapter messageAdapter = new ChatAdapter(messages, this );
        listView.setAdapter (messageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "In onClick()");
                input = editText.getText().toString();
                messages.add(input);

                ContentValues values = new ContentValues();
                values.put(ChatDatabaseHelper.COLUMN_MESSAGE, input);
                database.insert(helper.TABLE_NAME, "null", values);

                messageAdapter.notifyDataSetChanged();
                editText.setText("");

            }
        });
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
        database.close();
        cursor.close();
    }
    private class ChatAdapter extends BaseAdapter {

        private ArrayList<String> list;
        private Context ctx;

        public ChatAdapter(ArrayList<String> list, Context ctx) {

            this.list = list;
            this.ctx = ctx;

        }



        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;

            if (position % 2 == 0) {

                result = inflater.inflate(R.layout.chat_row_incoming, null);

            } else {

                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = (TextView) result.findViewById(R.id.message);

            message.setText(getItem(position));

            return result;
        }
    }
}
