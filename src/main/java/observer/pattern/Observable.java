package observer.pattern;

import java.util.Vector;

/**
 * An abstract class for all Observable subjects
 */
public abstract class Observable<K> {
	/**
	 * Constructs an Observable object
	 */
	public Observable() {
		this.observers = new Vector<Observer<K>>();
	}

	/**
	 * Attach to this Subject
	 * 
	 * @param o
	 *            the Observer that wishes to attach
	 */
	public void attach(Observer<K> o) {
		this.observers.addElement(o);
	}

	/**
	 * Detach from this Subject
	 * 
	 * @param o
	 *            the Observer that wishes to detach
	 */
	public void detach(Observer<K> o) {
		for (int i = 0; i < observers.size(); i++) {
			if (observers.elementAt(i).equals(o))
				observers.removeElementAt(i);
		}
	}

	/**
	 * Notify all Observers that Subject has changed
	 */
	public void notifyObservers(K data) {
		for (int i = 0; i < observers.size(); i++) {
			Observer<K> observer = observers.elementAt(i);
			observer.update(this, data);
		}
	}

	/**
	 * Pull updated data from this Subject
	 * 
	 * @return the updated data from the Subject
	 */
	public abstract Object getUpdate();

	protected Vector<Observer<K>> observers;
}