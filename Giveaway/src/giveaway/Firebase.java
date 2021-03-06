package giveaway;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Firebase {

	public static void connectDatabase() {
		// Fetch the service account key JSON file contents
		FileInputStream serviceAccount = new FileInputStream("path/to/serviceAccountCredentials.json");

		// Initialize the app with a service account, granting admin privileges
		FirebaseOptions options = new FirebaseOptions.Builder()
		    .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
		    .setDatabaseUrl("https://databaseName.firebaseio.com")
		    .build();
		FirebaseApp.initializeApp(options);

		// As an admin, the app has access to read and write all data, regardless of Security Rules
		DatabaseReference ref = FirebaseDatabase
		    .getInstance()
		    .getReference("restricted_access/secret_document");
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
		    @Override
		    public void onDataChange(DataSnapshot dataSnapshot) {
		        Object document = dataSnapshot.getValue();
		        System.out.println(document);
		    }
		});
	}
    
}
