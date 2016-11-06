package logintest.android.com.androidchatsocket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import logintest.android.com.socket.SocketConnection;
import logintest.android.com.util.AppSettings;
import logintest.android.com.util.Constants;

public class UserName extends AppCompatActivity implements View.OnClickListener {

    Button bt_adduser;
    EditText et_username;

    private SocketConnection socketConnection = SocketConnection.getInstance();
    boolean isConnected = SocketConnection.connectToSocket();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        socketConnection.setup();

        bt_adduser = (Button) findViewById(R.id.bt_adduser);
        et_username = (EditText) findViewById(R.id.et_username);
        bt_adduser.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String userName = et_username.getText().toString();
        if (userName != null && userName.length() > 0) {
            AppSettings.setUserName(userName);
            socketConnection.addUser(userName);
            startActivity(new Intent(this, MainScreen.class));
        } else
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketConnection.disconnectSocket();
    }
}
