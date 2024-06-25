package slayin.model.utility;

import java.util.Objects;

/**
 * A standard generic Pair<E1,E2>, with getters, hashCode, equals, and toString well implemented. 
 */
public class Pair<E1,E2> {
	
	private final E1 e1;
	private final E2 e2;
	
	/**
     * Constructs a new Pair with the specified objects.
     *
     * @param x the first object
     * @param y the second object
     */
	public Pair(E1 x, E2 y) {
		super();
		this.e1 = x;
		this.e2 = y;
	}


	/**
     * Returns the first object in the pair.
     *
     * @return the first object
     */
	public E1 get1() {
		return e1;
	}


	/**
     * Returns the second object in the pair.
     *
     * @return the second object
     */
	public E2 get2() {
		return e2;
	}


	/**
     * Computes a hash code for this pair.
     *
     * @return a hash code value for this object
     */
	@Override
	public int hashCode() {
		return Objects.hash(e1, e2);
	}


	/**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise
     */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Pair<?,?> other = (Pair<?,?>) obj;
		return Objects.equals(e1, other.e1) && Objects.equals(e2, other.e2);
	}


	/**
     * Returns a string representation of this pair.
     *
     * @return a string representation of this pair
     */
	@Override
	public String toString() {
		return "Pair [e1=" + e1 + ", e2=" + e2 + "]";
	}
	
	

}
