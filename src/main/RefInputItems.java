package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.InputItem;


public class RefInputItems {
	
	Map<String, InputItem> hmIdentItem; 
	
	/* constructor
	 * 
	 */
	public RefInputItems() {
		this.hmIdentItem = new HashMap<String, InputItem>();
	}

	/** add a new item
	 * 
	 * @param item
	 */
	public void add(InputItem item)  {
		if (this.hmIdentItem.get(item.getId()) != null) {
			throw new IllegalArgumentException("input items file : duplicate item ident : " + item.getId());
		} else {
			this.hmIdentItem.put(item.getId(), item);
		}
	}
	
	/** get all the input items
	 * 
	 * @return
	 */
	public Collection<InputItem> getAll() {
		return this.hmIdentItem.values();
	}
	
	/** get the number of item records
	 * 
	 * @return
	 */
	public int size()  {
		return this.hmIdentItem.keySet().size();
	}

	/** get an item from its ident
	 * 
	 * @param ident
	 * @return
	 */
	public InputItem get(String ident) {
		return this.hmIdentItem.get(ident);
	}
}
