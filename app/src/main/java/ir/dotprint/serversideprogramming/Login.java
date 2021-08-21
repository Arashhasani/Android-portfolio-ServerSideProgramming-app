package ir.dotprint.serversideprogramming;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    Button btnregister,btnlogin;
    EditText edtusername,edtpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin = (Button)findViewById(R.id.btnlogin);
        btnregister = (Button)findViewById(R.id.btnregisterlogin);
        edtusername=(EditText)findViewById(R.id.edtuserlogin);
        edtpassword=(EditText)findViewById(R.id.edtpasswordlogin);


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = String.valueOf(edtusername.getText());
                final String password = String.valueOf(edtpassword.getText());

                if (TextUtils.isEmpty(username)|| TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"FIll All" ,Toast.LENGTH_SHORT).show();
                }else {
                    final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setTitle("please wait");
                    progressDialog.show();
                    String url = "http://dotprint.ir/resume/serverside/finduser.php";

                    Response.Listener<String> stringListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equals("ok")){
                                Intent intent = new Intent(Login.this , MainActivity.class);
                                intent.putExtra("username" , username);
                                startActivity(intent);
                                finish();

                            }else {
                                Toast.makeText(getApplicationContext(),"Username Or Password Is Wrong !!" , Toast.LENGTH_SHORT).show();

                            }
                            progressDialog.dismiss();
                        }
                    };


                    Response.ErrorListener errorListener = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),"Error" , Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    };

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,url,stringListener,errorListener){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String , String> hashMap = new HashMap<String, String>();
                            hashMap.put("username" , username);
                            hashMap.put("password" , password);
                            return hashMap;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(stringRequest);

                }
            }
        });
    }

}
