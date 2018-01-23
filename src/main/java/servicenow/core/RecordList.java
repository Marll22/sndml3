package servicenow.core;

import java.util.*;

/**
 * An array of Records.
 */
public class RecordList extends ArrayList<Record> {
	
	private static final long serialVersionUID = 1L;
	
	final protected Table table;
	
	public RecordList(Table table) {
		super();
		this.table = table;
	}

	public RecordList(Table table, int size) {
		super(size);
		this.table = table;
	}
	
	public RecordIterator iterator() {
		return new RecordIterator(this);
	}

	/**
	 * Extract all the values of a reference field from a list of records.
	 * Null keys are not included in the list.
	 * @param fieldname Name of a reference field
	 * @return A list keys
	 */
	public KeyList extractKeys(String fieldname) {
		KeyList result = new KeyList(this.size());
		if (this.size() == 0) return result;
		for (Record rec : this) {
			String value = rec.getValue(fieldname);
			if (value != null) {
				assert Key.isGUID(value);
				result.add(new Key(value));
			}
		}		
		return result;
	}

	/**
	 * Extract the primary keys (sys_ids) from this list.
	 */
	public KeyList extractKeys()  {
		return extractKeys("sys_id");
	}
	
}