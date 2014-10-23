package com.example.test2222;


import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
 
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         
        DatabaseHandler db = new DatabaseHandler(this);
         
        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        String log = "";
   
        log += "Insert: "+ "Inserting .. \n"; 
        db.addContact(new Contact("Ravi", "9100000000"));        
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));
         
        // Reading all contacts
        log +="Reading: "+ "Reading all contacts.. \n";
        List<Contact> contacts = db.getAllContacts();
         
        for (Contact cn : contacts) {
            log += "Id: "+cn.getID()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber() + "\n";
        }
        
        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText(log);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
