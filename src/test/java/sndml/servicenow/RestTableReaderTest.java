package sndml.servicenow;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;

import sndml.servicenow.Record;
import sndml.servicenow.RecordListAccumulator;
import sndml.servicenow.Session;
import sndml.servicenow.Table;
import sndml.servicenow.TableReader;

public class RestTableReaderTest {

	final Logger logger = TestManager.getLogger(this.getClass());
	final Session session;
	
	public RestTableReaderTest() {
		session = TestManager.getDefaultProfile().getSession();
	}

	@Test
	public void testSetQuery() throws Exception {
		String someDeptName = TestManager.getProperty("some_department_name");
		boolean found = false;
		Table dept = session.table("cmn_department");
		RecordListAccumulator accumulator = new RecordListAccumulator(dept);
		TableReader reader = dept.rest().getDefaultReader();
		reader.setWriter(accumulator);
		reader.initialize();
		reader.call();
		for (Record rec : accumulator.getRecords()) {
			String deptName = rec.getValue("name");
			String deptHead = rec.getValue("dept_head");
			logger.debug(deptName + "|" + deptHead);;
			if (someDeptName.equals(deptName)) found = true;			
		}
		assertTrue("Expected department found", found);
	}

	@Test
	public void testSetDisplayValuesTrue() throws Exception {
		String someDeptName = TestManager.getProperty("some_department_name");
		String someDeptHead = TestManager.getProperty("some_department_head");
		boolean found = false;
		Table dept = session.table("cmn_department");
		RecordListAccumulator accumulator = new RecordListAccumulator(dept);
		TableReader reader = dept.rest().getDefaultReader();
		reader.setWriter(accumulator);
		reader.setDisplayValue(true);
		reader.initialize();
		reader.call();
		for (Record rec : accumulator.getRecords()) {
			String deptName = rec.getValue("name");
			String deptHead = rec.getDisplayValue("dept_head");
			logger.debug(deptName + "|" + deptHead);;
			if (someDeptName.equals(deptName)) {
				if (someDeptHead.equals(deptHead)) found = true; 
			}			
		}
		assertTrue("Department head name found", found);
	}

	@Test
	public void testSetDisplayValuesFalse() throws Exception {
		Table dept = session.table("cmn_department");
		RecordListAccumulator accumulator = new RecordListAccumulator(dept);
		TableReader reader = dept.rest().getDefaultReader();
		reader.setWriter(accumulator);
		reader.setDisplayValue(false);
		reader.initialize();
		reader.call();
		for (Record rec : accumulator.getRecords()) {
			String deptName = rec.getValue("name");
			String deptHead = rec.getDisplayValue("dept_head");
			logger.debug(deptName + "|" + deptHead);
			if (deptHead != null) fail("Department head should be null");
		}
	}
		

}
