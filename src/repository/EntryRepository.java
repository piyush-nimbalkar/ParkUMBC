package repository;

import java.util.List;
import model.Entry;
import storage.DataStorage;
import android.content.Context;
import android.widget.Toast;

public class EntryRepository {
	    private DataStorage dataStorage;
	    private final Context context;

	    public EntryRepository(Context _context) {
	        context = _context;
	        dataStorage = new DataStorage(context);
	    }

	    public boolean createEntry(double latitude, double longitude) {
	        Entry entry = new Entry(latitude, longitude);
            dataStorage.store(entry);
            Toast.makeText(context, "Stored in the database!", 1).show();
            return true;
	    }
	
	    public Entry getEntry() {
	        return dataStorage.getEntryDetails();
	    }	    
}
