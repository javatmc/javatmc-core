package org.jtmc.core.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * EnumParameters is a convenience class for building prototype objects
 */
public abstract class EnumParameters<T extends Enum<T>> {

	private Map<T, Object> params = new HashMap<>();

	/**
	 * Returns the parameters that have an assigned value
	 * @return Set of parameters
	 */
	public Set<T> getParameters() {
		return new HashSet<>(params.keySet());
	}

	/**
	 * Returns a given parameter as float
	 * @param param Parameter
	 * @return Float value
	 * @throws ClassCastException if the parameter isn't float
	 */
	public float getFloat(T param) {
		return (float) params.get(param);
	}

	/**
	 * Returns a given parameter as int
	 * @param param Parameter
	 * @return Integer value
	 * @throws ClassCastException if the parameter isn't int
	 */
	public int getInt(T param) {
		return (int) params.get(param);
	}

	/**
	 * Returns a given parameter as boolean
	 * @param param Parameter
	 * @return Boolean value
	 * @throws ClassCastException if the parameter isn't boolean
	 */
	public boolean getBoolean(T param) {
		return (boolean) params.get(param);
	}

	/**
	 * Assigns the given object to the parameter
	 * @param param Parameter
	 * @param value Assigned value
	 * @return Previously assigned value
	 */
	protected Object put(T param, Object value) {
		return params.put(param, value);
	}

	/**
	 * Removes the assigned value from given parameter
	 * @param param Parameter
	 * @return Assigned value
	 */
	protected Object remove(T param) {
		return params.remove(param);
	}

	/**
	 * Returns whether the parameter has any value assigned to it
	 * @param param Parameter
	 * @return {@code true} if the parameter has an object assigned to it
	 */
	public boolean has(T param) {
		return params.containsKey(param);
	}

	/**
	 * Optionally returns the value associated to the given parameter
	 * @param <U> Type of the parameter
	 * @param param Parameter
	 * @return Optional of value, {@code null} if the parameter has no value or isn't of type {@code U}  
	 */
	@SuppressWarnings("unchecked")
	public <U> Optional<U> get(T param) {
		try {
			Object o = params.get(param);
			if(o == null) {
				return Optional.empty();
			}
			return Optional.of((U) o);
		} catch(ClassCastException e) {
			return Optional.empty();
		}
	}
}