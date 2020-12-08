package observer.pattern;

import observer.CourseRecord;

/**
 * An interface for all Observers
 */
public interface Observer<K> {
	/**
	 * Informs this observer that an observed subject has changed
	 * 
	 * @param o
	 *            the observed subject that has changed
	 */
	public void update(Observable<K> o, K data);
}