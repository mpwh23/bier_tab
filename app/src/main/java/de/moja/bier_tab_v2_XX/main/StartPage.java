package de.moja.bier_tab_v2_XX.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.PopupWindow;

import java.util.HashMap;

import de.moja.bier_tab_v2_XX.R;
import de.moja.bier_tab_v2_XX.web_connect.LoginActivity;
import de.moja.bier_tab_v2_XX.helper.Pop_Btn01;
import de.moja.bier_tab_v2_XX.config.AppConfig;
import de.moja.bier_tab_v2_XX.web_connect.AppController;
import de.moja.bier_tab_v2_XX.helper.SQLiteHandler;
import de.moja.bier_tab_v2_XX.helper.SQLiteHandlerR;
import de.moja.bier_tab_v2_XX.helper.SessionManager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONException;
import org.json.JSONObject;
import static android.graphics.Paint.Align.LEFT;

public class StartPage extends Activity {

	private TextView txtName;
	private TextView txtEmail;
	private Button btnLogout;
	private ImageButton btn_main1,btn_main2,btn_main3,btn_main4;

	private SQLiteHandler db;
	private SQLiteHandlerR dbR;
	private SessionManager session;
	private ProgressDialog pDialogR;

	PopupWindow popUpWindow_1;
	View mainView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_page);

		txtName = (TextView) findViewById(R.id.name);
		txtEmail = (TextView) findViewById(R.id.email);
		btnLogout = (Button) findViewById(R.id.btnLogout);
		btn_main1 = (ImageButton) findViewById(R.id.startbtn01);
		btn_main2 = (ImageButton) findViewById(R.id.startbtn02);
		btn_main3 = (ImageButton) findViewById(R.id.startbtn03);
		btn_main4 = (ImageButton) findViewById(R.id.startbtn04);

		// SqLite database handler
		db = new SQLiteHandler(getApplicationContext());
		dbR = new SQLiteHandlerR(getApplicationContext());

		// session manager
		session = new SessionManager(getApplicationContext());

		// Fetching user details from SQLite
		HashMap<String, String> user = db.getUserDetails();

		String name = user.get("name");
		String email = user.get("email");

		// Displaying the user details on the screen
		txtName.setText(name);
		txtEmail.setText(email);


		//buttons
		btn_main1.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.d("BTN1: ","LOAD_ btn REZEPTE");
				startActivity(new Intent(StartPage.this,Pop_Btn01.class));


				//Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
				//startActivity(i);
			}
		});

		btn_main2.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.d("BTN2: ","neues Protokoll");
			}
		});
		btn_main4.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				createPdf();
			}
		});

		// Logout button click event
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logoutUser();
			}
		});
	}

	/**
	 * Logging out the user. Will set isLoggedIn flag to false in shared
	 * preferences Clears the user data from sqlite users table
	 * */
	private void logoutUser() {
		session.setLogin(false);

		db.deleteUsers();

		// Launching the login activity
		Intent intent = new Intent(StartPage.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
	public void createPdf(){
		// create a new document
		PdfDocument document = new PdfDocument();

		float pageheight = 3508;
		float pagewidth = 2480;
		float cm = (float) (300/2.54);
		// crate a page description
		PdfDocument.PageInfo pageInfo =
				new PdfDocument.PageInfo.Builder(2480, 3508, 1).create();

		// start a page
		PdfDocument.Page page = document.startPage(pageInfo);
		Canvas canvas = page.getCanvas();
			//zeichenwerkzeuge
			Paint paint = new Paint();
				paint.setColor(Color.RED);
			Paint P_linethin = new Paint(Paint.ANTI_ALIAS_FLAG);
				P_linethin.setColor(0xff0000ff);
			Paint P_textmedium = new Paint(Paint.ANTI_ALIAS_FLAG);
				P_textmedium.setStyle(Paint.Style.FILL);
				P_textmedium.setColor(0xff000000);
				P_textmedium.setTextSize(150);
				P_textmedium.setTextAlign(LEFT);

			//draw
			canvas.drawCircle(50, 50, 30, paint);

			canvas.drawLine(0,0,pagewidth,pageheight,P_linethin);
			canvas.drawLine(pagewidth,0,0,pageheight,P_linethin);

			canvas.drawText("Hallo Welt", 2*cm,5*cm,P_textmedium);

		Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.startbtn_01);
		//notBuilder.setLargeIcon(largeIcon);

			canvas.drawBitmap(largeIcon,null,new Rect((int)(10*cm),(int)(10*cm),(int)(15*cm),(int)(15*cm)),null);




		// finish the page
		document.finishPage(page);

		// Create Page 2
		pageInfo = new PdfDocument.PageInfo.Builder(500, 500, 2).create();
		page = document.startPage(pageInfo);
		canvas = page.getCanvas();
		paint = new Paint();
		paint.setColor(Color.BLUE);
		canvas.drawCircle(200, 200, 100, paint);
		document.finishPage(page);

		// write the document content
		String targetPdf = "/sdcard/test4.pdf";
		File filePath = new File(targetPdf);
		try {
			document.writeTo(new FileOutputStream(filePath));
			Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toast.makeText(this, "Something wrong: " + e.toString(),
					Toast.LENGTH_LONG).show();
		}

		// close the document
		document.close();
	}

	private void saveRezept(){

		final String TAG = "REZEPT ";
		String tag_string_req = "req_register";
		final String name = "mein Name neu";
		final String price = "989";
		final String des = "beschreibung wtf 2";

		StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_REZEPT_ADD, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				Log.d(TAG, "Register Response: " + response.toString());

				try {
					Log.d(TAG,"REZEPT");
					JSONObject jObj = new JSONObject(response);
					boolean error = jObj.getBoolean("error");
					if (!error) {
						// Rezept successfully stored in MySQL
						// Now store the rezept in sqlite

						String uid = jObj.getString("uid");

						JSONObject rezept = jObj.getJSONObject("rezept");
						String name = rezept.getString("name");
						String price = rezept.getString("price");
						String created_at = rezept.getString("created_at");

						// Inserting row in users table
						dbR.addRezept(name, price, uid, created_at);

						Toast.makeText(getApplicationContext(), "vielleicht toll", Toast.LENGTH_LONG).show();

						// Launch login activity
						//Intent intent = new Intent(
						//		RegisterActivity.this,
						//		LoginActivity.class);

						//intent.putExtra("registeredPass",inputPassword.getText().toString().trim() );
						//intent.putExtra("registeredEmail",inputEmail.getText().toString().trim());
						//startActivity(intent);
						//finish();
					} else {

						// Error occurred in registration. Get the error
						// message
						String errorMsg = jObj.getString("error_msg");
						Toast.makeText(getApplicationContext(),
								errorMsg, Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					Log.d(TAG,"Entered register fail");
					e.printStackTrace();

				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "fuck Error: " + error.getMessage());
				Toast.makeText(getApplicationContext(),
						error.getMessage(), Toast.LENGTH_LONG).show();
				//hideDialog();
			}
		}) {

			@Override
			protected Map<String, String> getParams() {
				Log.d(TAG,"Entered map");
				// Posting params to rezept url
				Map<String, String> params = new HashMap<String, String>();
				params.put("name", name);
				params.put("price", price);
				params.put("des", des);

				Log.d(TAG,"Entered map, params: " + params);

				return params;

			}
		};

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
		Log.d(TAG,"Entered register ?");

	}
}
