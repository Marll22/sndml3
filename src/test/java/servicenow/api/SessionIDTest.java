package servicenow.api;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SessionIDTest {

	@Test
	public void testSession() throws Exception {
		TestingManager.loadDefaultProfile();
		Session session = TestingManager.getSession();
		Table location = session.table("cmn_location");
		location.getRecord("name", TestingManager.getProperty("location1"));;
		String session1 = session.getSessionID();
		System.out.println("JSESSIONID=" + session1);
		location.getRecord("name", TestingManager.getProperty("location2"));
		String session2 = session.getSessionID();
		System.out.println("JSESSIONID=" + session2);
		location.getRecord("name", TestingManager.getProperty("location3"));
		String session3 = session.getSessionID();
		System.out.println("JSESSIONID=" + session3);
		assertEquals(session1, session2);
		assertEquals(session2, session3);
	}

}
