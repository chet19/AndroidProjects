package com.example.chetan.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadImage2 extends AppCompatActivity implements View.OnClickListener {

    private Button uploadImagebtn,browseImagebtn;
   private ImageView image_to_upload;

    private EditText nameEditText;
    private static final int request_code=1;

    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image2);
        init();
        uploadImagebtn.setOnClickListener(this);
        browseImagebtn.setOnClickListener(this);

    }

    private void init(){
        uploadImagebtn=(Button)findViewById(R.id.upload_button);
        browseImagebtn=(Button)findViewById(R.id.upload_button);
        nameEditText=(EditText)findViewById(R.id.edit_text_field);
        image_to_upload=(ImageView)findViewById(R.id.image_to_load);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.browse_button:
                browseImage();
                break;
        }


    }
private void browseImage(){

    Intent intent=new Intent();
    intent.setType("Image");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(intent,request_code);

}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==requestCode && resultCode==RESULT_OK && data!=null){

            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);

                image_to_upload.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }



    }

    private void uplaodImage(){


    }

    private String imageToString(Bitmap bitmap){


        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte []bytearr=byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(bytearr,Base64.DEFAULT);


    }

}
