package com.example.instasaver;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.StringUtils;

public class PhotoFragment extends Fragment {
    String URL="NULL";
    EditText getPhotoLink;
    ImageView mparticularphoto;
    Button getphoto,downloadphoto;
    String PhotoUrl="1";
    private Uri uri2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.photo_fragment,null);
        getphoto=view.findViewById(R.id.getphoto);
        downloadphoto=view.findViewById(R.id.downloadPhoto);
        getPhotoLink=view.findViewById(R.id.getphotolink);
        mparticularphoto=view.findViewById(R.id.particularphoto);


        getphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL=getPhotoLink.getText().toString().trim();
                if(getPhotoLink.equals("NULL")){
                    Toast.makeText(getContext(), "Enter URL first!!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String result2= StringUtils.substringBefore(URL,"/?");
                    URL=result2+"/?__a=1";
                    processdata();
                }
            }
        });

        downloadphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!PhotoUrl.equals("1")){
                    DownloadManager.Request request=new DownloadManager.Request(uri2);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE|DownloadManager.Request.NETWORK_WIFI);
                    request.setTitle("Downloaded");
                    request.setDescription("......");
                    request.allowScanningByMediaScanner();
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DCIM,""+System.currentTimeMillis()+".jpg");
                    DownloadManager manager=(DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                    manager.enqueue(request);
                    Toast.makeText(getContext(), "Image downloaded!!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Enter URL first!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }


    private void processdata() {
        StringRequest request=new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                MainURL mainURL = gson.fromJson(response, MainURL.class);
                PhotoUrl = mainURL.getGraphql().getShortcode_media().getDisplay_url();
                uri2 = Uri.parse(PhotoUrl);
                Glide.with(getContext()).load(uri2).into(mparticularphoto);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Enter correct URL", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(request);
    }
}
