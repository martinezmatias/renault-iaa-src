package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.OutputStack;

public class RefOutputStacks {
	
	private Map<String, OutputStack> hmIdentStack;

	/** constructor
	 * 
	 */
	public RefOutputStacks() {
		this.hmIdentStack = new HashMap<String, OutputStack>();
	}

	/** add a new stack
	 * 
	 * @param stack
	 */
	public void add(OutputStack stack) {
		this.hmIdentStack.put(stack.getId(), stack);
	}
	
	/** get a stack from its id
	 * 
	 * @param stackId
	 * @return
	 */
	public OutputStack get(String stackId) {
		return this.hmIdentStack.get(stackId);
	}
	
	/** get all the stacks
	 * 
	 * @return
	 */
	public Collection<OutputStack> getAll() {
		return this.hmIdentStack.values();
	}
	
	
	/** get the number of stacks
	 * 
	 * @return
	 */
	public int size()  {
		return this.hmIdentStack.size();
	}
}
