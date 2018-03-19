package com.example.punam.myweathermap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.JarException;

public class MainActivity extends AppCompatActivity
{
    Button button;
    EditText city;
    TextView result;
    String baseURl="http://api.openweathermap.org/data/2.5/weather?q=";
    String API="&appid=d30ad3013afbd76f6c951a1011199936";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button =findViewById(R.id.button);
        city=findViewById(R.id.getcity);
        result=findViewById(R.id.weather);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.i("TAP","TAPPED");
                String myURL= baseURl + city.getText()+API;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myURL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response)
                            {
                                Log.i("TAPPED","Inside onResponse");
                                try
                                {
                                    String info = response.getString("weather");
                                    Log.i("INFO","Inside try");
                                    JSONArray ar = new JSONArray(info);
                                    for(int i=0;i<ar.length();i++)
                                    {
                                        JSONObject parObj = ar.getJSONObject(i);
                                        String myWeather = parObj.getString("main");
                                        result.setText("Status: "+myWeather+"\n");
                                    }
                                    String main = response.getString("main");
                                    JSONObject obj = new JSONObject(main);
                                    String mytemp = obj.getString("temp");
                                    double mtemp = Double.parseDouble(mytemp);
                                    mtemp = mtemp-273.15;
                                    mtemp = Math.round(mtemp);
                                    result.setText(result.getText()+"Temparature: "+mtemp+" degree celcius\n");
                                    //String pressure = obj.getString("pressure");
                                    //mypressure.setText(pressure);
                                    String humadity = obj.getString("humidity");
                                    result.setText(result.getText()+"Humidity: "+humadity);
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {

                            }
                        }
                );
                MySingleton.getmInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);
            }
        });
    }
}
