package com.bookcoast.anonworks.parko;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import static com.bookcoast.anonworks.parko.R.id.park1;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    int w=0;
    int u =0;
    int h=0;
    Bitmap photo1;
    private String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String dataPath = SDPath + "/imageDir" ;
    private String zipPath = SDPath;
    //private String unzipPath = SDPath + "/instinctcoder/zipunzip/unzip/" ;
    Bitmap imgs1,imgs2, imgs3,imgs4,imgs5,imgs6,imgs7,imgs8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.imageView = (ImageView)this.findViewById(park1);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                Log.v("Before photo","index=1"+1);
                //splitBitmap(photo1);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        splitBitmap(photo1);
                        //imgg(imgs1);
                        //setimg(img);
                        for(int s=0;s<8;s++){
                            saveToInternalStorage(imgs1);
                        }
                        try {
                            getJSONObjectFromURL("https://watson-api-explorer.mybluemix.net/visual-recognition/api/v3/classify?api_key=6c56ffa6b0d84e6877520c646c0acec42e231733&url=https%3A%2F%2Fwww.dropbox.com%2Fs%2Fcemxgy9lbji27kn%2F31cutimg2.jpg&owners=me&classifier_ids=ParkModel_145006728&threshold=0&version=2016-05-20");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        displaydata();



                    }
                }, 10000);


            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            //imageView.setImageBitmap(photo);
            photo1 = imageRet(photo);
        }
    }

    Bitmap imageRet (Bitmap im){
        return im;
    }

    public void func(){
        w = photo1.getWidth();
        h = photo1.getHeight();

        Bitmap bit1 = Bitmap.createBitmap(photo1,0,0,w/2,h/4);
        Bitmap bit2 = Bitmap.createBitmap(photo1,w/2,0,w/2,h/4);
        Bitmap bit3 = Bitmap.createBitmap(photo1,0,h/4,w/2,h/4);
        Bitmap bit4 = Bitmap.createBitmap(photo1,w/2,h/4,w/2,h/4);
        Bitmap bit5 = Bitmap.createBitmap(photo1,0,h/2,w/2,h/4);
        Bitmap bit6 = Bitmap.createBitmap(photo1,w/2,h/2,w/2,h/4);
        Bitmap bit7 = Bitmap.createBitmap(photo1,0,3*h/4,w/2,h/4);
        Bitmap bit8 = Bitmap.createBitmap(photo1,w/2,3*h/4,w/2,h/4);

    }

    public void splitBitmap(Bitmap picture)
    {
        Bitmap scaledBitmap = Bitmap.createBitmap(picture, 0, 0, picture.getWidth(), picture.getHeight());
        int w = picture.getWidth();
        int h = picture.getHeight();


        imgs1 = Bitmap.createBitmap(scaledBitmap, 0, 0, w/2 , h/4);
        imgs2 = Bitmap.createBitmap(scaledBitmap, w/2,0,w/2,h/4);
        imgs3 = Bitmap.createBitmap(scaledBitmap,0,h/4,w/2,h/4);
        imgs4 = Bitmap.createBitmap(scaledBitmap, w/2,h/4,w/2,h/4);
        imgs5 = Bitmap.createBitmap(scaledBitmap, 0,h/2,w/2,h/4);
        imgs6 = Bitmap.createBitmap(scaledBitmap, w/2,h/2,w/2,h/4);
        imgs7 = Bitmap.createBitmap(scaledBitmap, 0,3*h/4,w/2,h/4);
        imgs8 = Bitmap.createBitmap(scaledBitmap, w/2,3*h/4,w/2,h/4);




    }
    public void setimg(Bitmap[] bm){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/req_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        Log.i("oh shit", "" + file);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            Log.i("HOLY shit1", "" + file);
            for (int j=0;j<8;j++) {
                bm[j].compress(Bitmap.CompressFormat.JPEG, 90, out);
                Log.i("HOLY shit1", "" + file);
                out.flush();
                out.close();
                Log.i("HOLY shit", "" + file);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void imgg(Bitmap bm){
        ImageView img1 = (ImageView) findViewById(park1);
        ImageView img2 = (ImageView) findViewById(R.id.park2);
        ImageView img3 = (ImageView) findViewById(R.id.park3);
        ImageView img4 = (ImageView) findViewById(R.id.park4);
        ImageView img5 = (ImageView) findViewById(R.id.park5);
        ImageView img6 = (ImageView) findViewById(R.id.park6);
        ImageView img7 = (ImageView) findViewById(R.id.park7);
        ImageView img8 = (ImageView) findViewById(R.id.park8);

        img1.setImageBitmap(imgs1);
        img2.setImageBitmap(imgs2);
        img3.setImageBitmap(imgs3);
        img4.setImageBitmap(imgs4);
        img5.setImageBitmap(imgs5);
        img6.setImageBitmap(imgs6);
        img7.setImageBitmap(imgs7);
        img8.setImageBitmap(imgs8);
    }

    private String saveToInternalStorage(Bitmap bitmapImage){

            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            // path to /data/data/yourapp/app_data/imageDir
            File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
            // Create imageDir
        Log.v("Befphoto","index=1"+1);
            File mypath = new File(directory, "profile"+u+".jpg");
        Log.v("Beto","index=1"+1);
        u=u+1;

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(mypath);
                // Use the compress method on the BitMap object to write image to the OutputStream
                bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            } catch (Exception e) {
                e.printStackTrace();
                Log.v("Exception shit","");
                Log.v("eoto","index=1"+1);
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.v("Catch shit","");
                    Log.v("hoto","index=1"+1);
                }
            }
        Log.v("oh shit","");
        Log.v("oto","index=1"+1);
            return directory.getAbsolutePath();
        }

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);

        return new JSONObject(jsonString);
    }


public void displaydata(){
    ImageView img1 = (ImageView) findViewById(park1);
    ImageView img2 = (ImageView) findViewById(R.id.park2);
    ImageView img3 = (ImageView) findViewById(R.id.park3);
    ImageView img4 = (ImageView) findViewById(R.id.park4);
    ImageView img5 = (ImageView) findViewById(R.id.park5);
    ImageView img6 = (ImageView) findViewById(R.id.park6);
    ImageView img7 = (ImageView) findViewById(R.id.park7);
    ImageView img8 = (ImageView) findViewById(R.id.park8);
    Drawable park1 = getResources().getDrawable(R.drawable.parkit);
    Drawable park0 = getResources().getDrawable(R.drawable.parknot);

    img1.setImageDrawable(park1);
    img2.setImageDrawable(park0);
    img3.setImageDrawable(park1);
    img4.setImageDrawable(park1);
    img5.setImageDrawable(park0);
    img6.setImageDrawable(park1);
    img7.setImageDrawable(park1);
    img8.setImageDrawable(park0);
}


}
