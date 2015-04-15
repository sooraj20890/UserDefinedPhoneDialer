package com.android.phonedialer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditorActivity extends Activity {
	
	//Fields used to store values in the class
    public EditText inputEditText;
    public String inputEditTextString,patternRegexString;
    public String phoneNumber=null,formattedPhoneNumber=null;
    //Button variable used to store button details in view
    public Button submitButton;
   
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);
		      submitButton = (Button) findViewById(R.id.button1);
		      /*Using and defining public static interface OnClickListener OnClick method
		      in anonymous class format */
		      submitButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//Calling user defined onClickButton method
						onClickButton(v);
					}
				});
		      
		    
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editor, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//User defined onClickButton method called when button is clicked
	public void onClickButton(View v)
	{
		//Storing content from edit text in EditText type
		inputEditText=(EditText) findViewById(R.id.editText1);
		//Conversion to string
		inputEditTextString= inputEditText.getText().toString();
		//Creating regular expression pattern to match criteria mentioned in project description
		/*NOTE: CARE IS TAKEN SUCH THAT ONLY (XXX) YYY-ZZZZ FORMAT WITH FREE SPACE BEFORE AND AFTER
		PHONE NUMBER ARE APPLICABLE IN THIS PROJECT */
		patternRegexString="\\s\\((\\d{3})\\)\\s(\\d{3})[-](\\d{4})\\s";
	    Pattern pattern=Pattern.compile(patternRegexString);
	    
	    //Matching input string with pattern
		Matcher match=pattern.matcher(inputEditTextString);
		
		//If condition to check whether there is a pattern match or not
		if(match.find())
		{
			//Storing the full pattern in string
			phoneNumber=match.group(0);
			
			if(phoneNumber != null)
			{
				//if string is not null strip it of all no numbers
				formattedPhoneNumber=phoneNumber.replaceAll("[^0-9]+","");
				
				//Creating an intent with action ACTION_DIAL
				Intent phoneIntent=new Intent(Intent.ACTION_DIAL);
				
				//using URI parsing set data of scheme "tel" and phone number to intent
				phoneIntent.setData(Uri.parse("tel:"+formattedPhoneNumber));
				
				//Starting the activity who match with the intent action and scheme 
				startActivity(phoneIntent);
			}
			
			//else condition of phone number null
			else
			{
				//using alert builder to create an alert
				AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
				alertBuilder.setTitle("Wrong Number Format");
			    alertBuilder.setMessage("Please check the number,there should be a number with format (xxx) yyy-zzzz and with a free space before and after number"
			    		+ " to enter dialer window after submit");
			    //Anonymous class to define action on clicking ok
			   alertBuilder.setPositiveButton("Ok",
	                   new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	                   dialog.cancel();
	               }
	           });
			   AlertDialog alertDialog = alertBuilder.create();
			   //Displaying dialog
			   alertDialog.show();
			}
			
		}
		//else condition if pattern is not found
		//content is same as previous else condition
		else
		{
			AlertDialog.Builder alertBuilder=new AlertDialog.Builder(this);
			alertBuilder.setTitle("Wrong Number Format");
			 alertBuilder.setMessage("Please check the number,there should be a number with format (xxx) yyy-zzzz and with a free space before and after number"
			    		+ " to enter dialer window after submit");
		   alertBuilder.setPositiveButton("Ok",
                   new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int id) {
                   dialog.cancel();
               }
           });
		   
		   AlertDialog alertDialog = alertBuilder.create();
		   alertDialog.show();
			
		}
		
	
	}
}
