package logintest.android.com.androidchatsocket;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import logintest.android.com.adapter.ChatAdapterSingle;
import logintest.android.com.model.ChatMessage;
import logintest.android.com.model.EndTypingPojo;
import logintest.android.com.model.TypingPojo;
import logintest.android.com.socket.SocketConnection;
import logintest.android.com.util.AppSettings;
import logintest.android.com.util.CamClass;
import logintest.android.com.util.Constants;
import logintest.android.com.util.ImageCompressUtils;
import logintest.android.com.util.Utils;

public class ChatRoom extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    RecyclerView recycler_view;
    ImageView bt_send;
    ImageView iv_menu_icon;
    EditText et_message;
    TextView tv_typing;
    TextView tv_online;
    ChatAdapterSingle chatAdapter;
    ArrayList<ChatMessage> chatMessageArrayList = new ArrayList<>();

    private Timer timer = new Timer();
    private boolean isTypingStop = true;
    private final long DELAY = 2000;

    private static final int REQUEST_CODE_GALLERY = 0x2;
    private static final int REQUEST_CODE_TAKE_PICTURE = 0x3;
    String orientation = "";
    Uri imageUriBg;
    CamClass camClass;


    ChatRoom mainActivity;


    private SocketConnection socketConnection = SocketConnection.getInstance();
    boolean isConnected = SocketConnection.connectToSocket();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        socketConnection.onIncomingMessage();
        socketConnection.onUserJoined();
        socketConnection.onUserLeft();
        socketConnection.whoTyping();
        socketConnection.whoEndTyping();
        socketConnection.receiveImage();


        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        bt_send = (ImageView) findViewById(R.id.bt_send);
        iv_menu_icon = (ImageView) findViewById(R.id.iv_menu_icon);
        findViewById(R.id.activity_main).requestFocus();
        et_message = (EditText) findViewById(R.id.et_message);
        tv_typing = (TextView) findViewById(R.id.tv_typing);
        tv_online = (TextView) findViewById(R.id.tv_online);
        bt_send.setOnClickListener(this);
        iv_menu_icon.setOnClickListener(this);
        mainActivity = this;

        et_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.v("Typing", "Typing Yes");
                userTyping(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.v("Typing", "Typing No");
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                                   @Override
                                   public void run() {
                                       Log.v("Typing", "Typing Run");
                                       userStopTyping();
                                   }
                               },
                        DELAY
                );
            }
        });

        chatAdapter = new ChatAdapterSingle(chatMessageArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(chatAdapter);
    }

    private void userTyping(CharSequence s) {
        if (s != null && s.length() > 0 && isTypingStop) {
            isTypingStop = false;
            socketConnection.typing();
        }
    }

    private void userStopTyping() {
        socketConnection.endTyping();
        isTypingStop = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChatMessage chatMessage) {
        addMessage(chatMessage);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TypingPojo typingPojo) {
        tv_typing.setVisibility(View.VISIBLE);
        tv_typing.setText(typingPojo.getUserName() + " typing...");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EndTypingPojo endTypingPojo) {
        tv_typing.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socketConnection.leftUser(AppSettings.getUsername());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_send:
                String message = et_message.getText().toString();
                et_message.setText("");
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setMessage(message);
                chatMessage.setReceived(false);
                chatMessage.setUserName(AppSettings.getUsername());
                addMessage(chatMessage);
                socketConnection.sendMessage(message);
                break;
            case R.id.iv_menu_icon:
                PopupMenu popupMenu = new PopupMenu(ChatRoom.this, v);
                popupMenu.setOnMenuItemClickListener(ChatRoom.this);
                popupMenu.inflate(R.menu.popup_menu);
                popupMenu.show();
                break;
        }
    }

    private void addMessage(ChatMessage chatMessage) {
        chatMessageArrayList.add(chatMessage);
        chatAdapter = new ChatAdapterSingle(chatMessageArrayList);
        chatAdapter.notifyDataSetChanged();
        recycler_view.scrollToPosition(chatAdapter.getItemCount() - 1);
        if (chatMessage.getOnlineCount() != null) {
            tv_online.setText("Online Users : " + chatMessage.getOnlineCount());
            tv_online.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_camera:
                cameraTakePic();
                break;

            case R.id.item_gallery:
                galleryTakePic();
                break;

            default:
                Log.v("Default", "Default");
        }
        return false;
    }

    public void cameraTakePic() {
        camClass = new CamClass(this, REQUEST_CODE_TAKE_PICTURE, "HapHopper");
        imageUriBg = camClass.captureImage();
    }

    public void galleryTakePic() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_CODE_GALLERY) {
                    beginCropFromGallery(data.getData(), data);
                } else if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
                    beginCropFromCamera(imageUriBg, data);
                } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri resultUri = result.getUri();
                    handleCrop(resultCode, resultUri);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void beginCropFromGallery(final Uri source, final Intent result) {

        final String path = getRealPathFromURI(result.getData());
        try {
            ExifInterface exif = new ExifInterface(path);
            orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CropImage.activity(source)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setFixAspectRatio(true)
                .start(this);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = this.getContentResolver().query(contentURI,
                null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void beginCropFromCamera(Uri source, Intent result) {
        String tempPath;
        if (source != null && source.getPath() != null) {
            tempPath = source.getPath();
        } else {
            tempPath = getRealPathFromURI(result.getData());
        }
        try {
            ExifInterface exif = new ExifInterface(tempPath);
            orientation = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CropImage.activity(source)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setFixAspectRatio(true)
                .start(this);
    }

    private void handleCrop(int resultCode, Uri result) {
        if (resultCode == RESULT_OK) {
            String path = getRealPathFromURI(result);
            String comPath;
            Bitmap bitmap;
            //comPath = ReusableLogic.compressImage(path);
            comPath = new ImageCompressUtils(this).compressImage(path);
            File file = new File(path);
            long length = file.length() / 1024;
            if (length > 1000) {
                comPath = new ImageCompressUtils(this).compressImage(path);
            }
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inScaled = false;
            bitmap = BitmapFactory.decodeFile(comPath, bitmapOptions);
            if (bitmap != null) {
                try {
                    if (orientation.equalsIgnoreCase("6")) {
                        bitmap = rotate(bitmap, 0);
                    } else if (orientation.equalsIgnoreCase("8")) {
                        bitmap = rotate(bitmap, 270);
                    } else if (orientation.equalsIgnoreCase("3")) {
                        bitmap = rotate(bitmap, 180);
                    }
                    Log.v("DATA", bitmap + "");

                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setChatImage(bitmap);
                    chatMessage.setReceived(false);
                    chatMessage.setImage(true);
                    chatMessage.setUserName(AppSettings.getUsername());
                    addMessage(chatMessage);
                    socketConnection.sendImage(Utils.getBase64Pic(comPath, bitmap));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Toast.makeText(this, "Error in getting image", Toast.LENGTH_SHORT).show();
        }
    }

    private static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        mtx.postRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

}
