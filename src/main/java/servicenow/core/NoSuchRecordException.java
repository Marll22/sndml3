package servicenow.core;

@SuppressWarnings("serial")
public class NoSuchRecordException extends ServiceNowException {
	
	public NoSuchRecordException(String message) {
		super(message);
	}
	
}
