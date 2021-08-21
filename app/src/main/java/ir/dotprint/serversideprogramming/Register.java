package ir.dotprint.serversideprogramming;

import android.app.ProgressDialog;
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

public class Register extends AppCompatActivity {

    Button btnregisterregister;
    EditText edtusernameregister,edtpasswordregister,edtrepeatpasswordregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnregisterregister=(Button) findViewById(R.id.btnregisterregister);
        edtpasswordregister=(EditText)findViewById(R.id.edtpasswordregister);
        edtusernameregister=(EditText)findViewById(R.id.edtusernameregister);
        edtrepeatpasswordregister=(EditText)findViewById(R.id.edtrepeatpasswordregister);




        btnregisterregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(edtusernameregister.getText()) || TextUtils.isEmpty(edtpasswordregister.getText())||TextUtils.isEmpty(edtrepeatpasswordregister.getText())){

                    Toast.makeText(getApplicationContext(),"Fill All" ,Toast.LENGTH_SHORT).show();

                }else {


                    final String username = String.valueOf(edtusernameregister.getText());
                    final String password = String.valueOf(edtpasswordregister.getText());
                    final String repeatpassword = String.valueOf(edtrepeatpasswordregister.getText());
                    if (repeatpassword.equals(password)){
                        final ProgressDialog progressDialog = new ProgressDialog(Register.this);
                        progressDialog.setTitle("please wait");
                        progressDialog.show();
                        String url = "http://dotprint.ir/resume/serverside/register.php";
                        Response.Listener<String> stringListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                                finish();
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


                    }else {
                        Toast.makeText(getApplicationContext(),"Your Password Is Not Same As Your Reapeat Password" ,Toast.LENGTH_SHORT).show();

                    }




                }


            }
        });

    }
}
