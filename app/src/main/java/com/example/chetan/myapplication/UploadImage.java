package com.example.chetan.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadImage extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private EditText editText;
    private Button upload_image;
    private Button browse_image;
    private final int img_request=1;
    private Bitmap bitmap;
    private String uploadUrl="http://techwinlabs.com/Prestoshare/api/index.php";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        init();
        upload_image.setOnClickListener(this);
        browse_image.setOnClickListener(this);



    }

    private void init(){

        imageView=(ImageView)findViewById(R.id.image_view);
        editText=(EditText)findViewById(R.id.edit_text);
        upload_image=(Button)findViewById(R.id.upload_image);
        browse_image=(Button)findViewById(R.id.browse_image);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case  R.id.browse_image:
                selectImage();
                break;

            case R.id.upload_image:
                imageUpload();
                break;




        }

    }

    private void selectImage(){

        Intent getphoto=new Intent();
        getphoto.setType("image/*");
        getphoto.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(getphoto,img_request);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==img_request && resultCode==RESULT_OK && data!=null ){
            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

    private void imageUpload(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, uploadUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String resp=jsonObject.getString("response");
                    Toast.makeText(UploadImage.this,resp,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(UploadImage.this,error.toString(),Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String ,String> parms=new HashMap<>();
                parms.put("name",editText.getText().toString());
                parms.put("image",imageTostring(bitmap));
                return parms;
            }
        };
        requestQueue.add(stringRequest);

    }
    private String imageTostring(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[]imgbyte=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbyte,Base64.DEFAULT);

    }
}
