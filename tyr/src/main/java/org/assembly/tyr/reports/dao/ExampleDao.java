package org.assembly.tyr.reports.dao;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.assembly.tyr.reports.model.Example;

/**
 * 
 * @author emanuel
 *
 */
public class ExampleDao {

	private Map<Long, Example> examples = new HashMap<Long, Example>();
	
	public ExampleDao() {
		examples.put(0l, new Example(0l, "example1", Calendar.getInstance().getTime(), 10l));
		examples.put(1l, new Example(1l, "example2", Calendar.getInstance().getTime(), 112l));
		examples.put(2l, new Example(2l, "example3", Calendar.getInstance().getTime(), 122l));
		examples.put(3l, new Example(3l, "example4", Calendar.getInstance().getTime(), 10l));
		examples.put(4l, new Example(4l, "example5", Calendar.getInstance().getTime(), 133l));
		examples.put(5l, new Example(5l, "example6", Calendar.getInstance().getTime(), 10l));
		examples.put(6l, new Example(6l, "example7", Calendar.getInstance().getTime(), 144l));
		examples.put(7l, new Example(7l, "example8", Calendar.getInstance().getTime(), 1777l));
		examples.put(8l, new Example(8l, "example9", Calendar.getInstance().getTime(), 710l));
		examples.put(9l, new Example(9l, "example10", Calendar.getInstance().getTime(), 210l));
		examples.put(10l, new Example(10l, "example11", Calendar.getInstance().getTime(), 110l));
		examples.put(11l, new Example(11l, "example12", Calendar.getInstance().getTime(), 810l));
	}
	
	public Collection<Example> getAll() {
		return examples.values();
	}
	
	public Example get(Long id) {
		return examples.get(id);
	}
	
}
