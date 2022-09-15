/**
 * 
 */
package main;

import java.util.Set;
import java.util.TreeSet;


/**
 * @author a193857
 *
 */


/*
 * Liste des anomalies
 */
public class RefAnos {

	private Set<String> hsMsgAno; // pour ne pas répéter N fois la meme ano

	public RefAnos() {
		hsMsgAno = new TreeSet<String>();
	}

	/*------------------------------------------------------------------------------------------------------
	 * 	methodes d'instance
	 * ------------------------------------------------------------------------------------------------------
	 */

	/**
	 * ajout d'une ano
	 * @param ano
	 */
	public void add(String ano) {
		hsMsgAno.add(ano);
	}

	/**
	 * @return the lstAnos
	 */
	public String[] getAll() {
		return hsMsgAno.toArray(new String[hsMsgAno.size()]);
	}
	
	
}