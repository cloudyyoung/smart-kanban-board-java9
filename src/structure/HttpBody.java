package structure;

import com.google.gson.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * This is a custom HashMap class for <b>fluently and elegantly</b> writing and reading values in
 * {@code JSON}.
 *
 * <p>To avoid bothers of TypeCasting warnings from HashMap and ArrayList class, this class is built
 * for adapting all types of variables in one object. Object could be both Map and List.
 *
 * @author Cloudy Young
 * @since 2.0
 * @version 2.1
 */
public final class HttpBody extends HashMap<Object, Object> {

	/** SerialVersionUID of the class. */
	private static final long serialVersionUID = 795022394347180417L;

	/**
	 * A {@code boolean} to indicate if this instance should be performing a
	 * {@code List}.
	 */
	private boolean list;

	/** Default constructor of {@code HttpBody}. */
	public HttpBody() {
		super();
	}

	/**
	 * Constructor of {@code HttpBody}, provide {@code Map} object.
	 *
	 * @param toCopy the {@code Map} to copy
	 */
	public HttpBody(Map<?, ?> toCopy) {
		super();
		this.parseMap(toCopy);
	}

	/**
	 * Constructor of {@code HttpBody}, provide {@code List} object.
	 *
	 * @param toCopy the {@code List} to copy
	 */
	public HttpBody(Collection<?> toCopy) {
		super();
		this.parseList(toCopy);
		this.list = true;
	}

	/**
	 * Constructor of {@code HttpBody}, provide a {@code boolean} to indicate if
	 * this instance should perform a {@code List}.
	 *
	 * @param isList a {@code boolean} to indicate if this instance should perform a
	 *               {@code List}
	 */
	public HttpBody(boolean isList) {
		super();
		this.list = isList;
	}

	/**
	 * A method to parse the given {@code Object} into {@code HttpBody}. This method
	 * is recursive. It guarantee that all the sub-element of the instance, such as
	 * child {@code List}, would always and also be {@code HttpBody} instance.
	 *
	 * @param obj the {@code Object} to parse
	 * @return the parsed object
	 */
	private Object parse(Object obj) {
		if (obj instanceof HttpBody) {
			return obj;
		} else if (obj instanceof Map) {
			HttpBody body = new HttpBody();
			for (Map.Entry<?, ?> each : ((Map<?, ?>) obj).entrySet()) {
				body.put(each.getKey(), this.parse(each.getValue()));
			}
			return body;
		} else if (obj instanceof List) {
			HttpBody body = new HttpBody(true);
			for (Object each : (List<?>) obj) {
				body.put(this.parse(each));
			}
			return body;
		} else {
			return obj;
		}
	}

	/**
	 * Put the provided {@code Map} object into this instance.
	 *
	 * @param map the {@code Map} to put
	 */
	private void parseMap(Map<?, ?> map) {
		for (Map.Entry<?, ?> each : map.entrySet()) {
			this.put(each.getKey(), this.parse(each.getValue()));
		}
	}

	/**
	 * Put the provided {@code Iterable} object into this instance. This method is
	 * to make the instance perform a {@code List}. When performing like a
	 * {@code List}, <b>the {@code Map} key would be index</b>.
	 *
	 * @param list the {@code Iterable} object to put, therefore it acceptes
	 *             {@code List}, {@code
	 *     Collections}, etc
	 */
	private void parseList(Iterable<?> list) {
		for (Object each : list) {
			this.put(this.size(), this.parse(each));
		}
	}

	/**
	 * Reindex the index of the instance, when performing like a {@code List}. This
	 * will be used when the index has changed in the instance, such as an element
	 * is removed.
	 */
	private void listReindex() {
		if (this.list) {
			Collection<Object> collect = this.values();
			this.clear();
			this.parseList(collect);
		}
	}

	/**
	 * Associates the specified value with the specified key in the instance. If the
	 * map previously contained a mapping for the key, the old value is replaced.
	 *
	 * <p>
	 * Put new key/value pair into the instance defaultly.
	 *
	 * <p>
	 * Put new value at the key index into the instance when perform a {@code List}.
	 *
	 * @param key   the Object of key
	 * @param value the Object of value
	 * @return the instance itself
	 * @see #get(Object)
	 * @see #put(Object, Object)
	 */
	@Override
	public HttpBody put(Object key, final Object value) {
		Object newKey = key;
		if (this.list) {
			newKey = Integer.parseInt(key.toString());
		}
		super.put(newKey, this.parse(value));
		return this;
	}

	/**
	 * Associates the specified value with the specified key in the instance. If the
	 * map previously contained a mapping for the key, the old value is replaced.
	 *
	 * <p>
	 * Put new value at the key index into the instance when perform a {@code List}.
	 *
	 * <p>
	 * When the instance is not performing a {@code List}, this invocation <b>is
	 * invalid and will be ignored</b>.
	 *
	 * @param value the value to be put
	 * @return the instance itself
	 * @see #put(Object, Object)
	 * @see #get(Object)
	 */
	public HttpBody put(Object value) {
		if (this.list) {
			this.put(this.size(), this.parse(value));
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param key {@inheritDoc}
	 * @return {@inheritDoc}
	 * @see #put(Object)
	 */
	@Override
	public Object get(Object key) {
		return super.get(key);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param key {@inheritDoc}
	 * @return the value associated with {@code key} will be removed, and return the
	 *         instance itself No element will be removed if there was no mapping
	 *         for {@code key}
	 * @see #remove(Object, Object)
	 */
	@Override
	public HttpBody remove(Object key) {
		if (this.list) {
			Integer index = this.parseInt(key);
			if (index != null) {
				super.remove(index);
			}
			this.listReindex();
		} else {
			super.remove(key);
		}
		return this;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @param key   {@inheritDoc}
	 * @param value {@inheritDoc}
	 * @return {@inheritDoc}
	 * @see #remove(Object)
	 */
	@Override
	public boolean remove(Object key, final Object value) {
		if (this.list) {
			Object index = this.parseInt(key);
			if (index == null) {
				return false;
			} else {
				boolean isSucceeded = super.remove(index, value);
				this.listReindex();
				return isSucceeded;
			}
		} else {
			return super.remove(key, value);
		}
	}

	/**
	 * Returns {@code true} if the instance maps one or more keys to the specified
	 * value. More formally, returns {@code true} if and only if the instance
	 * contains at least one mapping to a value {@code v} such that
	 * {@code Objects.equals(value, v)}. This operation will probably require time
	 * linear in the map size for most implementations of the {@code Map} interface.
	 * <b>This is a syntatic sugar.</b>
	 *
	 * @param value value whose presence in this instance is to be tested
	 * @return {@code true} if the instance maps one or more keys to the specified
	 *         value {@code false} if the instance maps no key to the specified
	 *         value or an exception is thrown
	 */
	public boolean hasValue(Object value) {
		try {
			return super.containsValue(value);
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * Returns {@code true} if the instance contains a mapping for the specified
	 * key. More formally, returns {@code true} if and only if the instance contains
	 * a mapping for a key {@code k} such that {@code Objects.equals(key, k)}.
	 * (There can be at most one such mapping.) <b>This is a syntatic sugar.</b>
	 *
	 * @param key key whose presence in this instance is to be tested
	 * @return {@code true} if the instance contains a mapping for the specified key
	 *         {@code false} if the instance maps no key to the specified value or
	 *         an exception is thrown
	 */
	public boolean hasKey(Object key) {
		try {
			return super.containsKey(key);
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * Returns the value of specified key in {@code Integer} type.
	 *
	 * @param key the key in {@code Object} type
	 * @return {@code Integer} value of the specified key {@code null} if no value
	 *         maps to the specified key, or the mapped value cannot be represented
	 *         as {@code Integer} type
	 */
	public Integer getInt(Object key) {
		return this.parseInt(this.get(key));
	}

	/**
	 * Returns the value of specified key in {@code Double} type.
	 *
	 * @param key the key in {@code Object} type
	 * @return {@code Double} value of the specified key {@code null} if no value
	 *         maps to the specified key, or the mapped value cannot be represented
	 *         as {@code Double} type
	 */
	public Double getDouble(Object key) {
		return this.parseDouble(this.get(key));
	}

	/**
	 * Returns the value of specified key in {@code Boolean} type.
	 *
	 * @param key the key in {@code Object} type
	 * @return {@code Boolean} value of the specified key {@code null} if no value
	 *         maps to the specified key, or the mapped value cannot be represented
	 *         as {@code Boolean} type
	 */
	public Boolean getBoolean(Object key) {
		return this.parseBoolean(this.get(key));
	}

	/**
	 * Returns the value of specified key in {@code String} type.
	 *
	 * @param key the key in {@code Object} type
	 * @return {@code String} value of the specified key {@code null} if no value
	 *         maps to the specified key, or the mapped value cannot be represented
	 *         as {@code String} type
	 */
	public String getString(Object key) {
		return this.parseString(this.get(key));
	}

	public Long getLong(Object key) {
		return this.parseLong(this.get(key));
	}

	/**
	 * Returns the value of specified key in {@code HttpBody}(performing
	 * {@code Map}) type.
	 *
	 * @param key the key in {@code Object} type
	 * @return {@code HttpBody}(performing {@code Map}) value of the specified key
	 *         {@code null} if no value maps to the specified key, or the mapped
	 *         value cannot be represented as {@code
	 *     HttpBody}(performing {@code Map}) type
	 */
	public HttpBody getMap(Object key) {
		return (HttpBody) this.get(key);
	}

	/**
	 * Returns the value of specified key in {@code HttpBody}(performing
	 * {@code List}) type.
	 *
	 * @param key the key in {@code Object} type
	 * @return {@code HttpBody}(performing {@code List}) value of the specified key
	 *         {@code null} if no value maps to the specified key, or the mapped
	 *         value cannot be represented as {@code
	 *     HttpBody}(performing {@code List}) type
	 */
	public HttpBody getList(Object key) {
		if (this.getMap(key) == null || !this.getMap(key).isList())
			return null;
		return this.getMap(key);
	}

	/**
	 * Returns the value of specified key in {@code HttpBody} type.
	 *
	 * @param key the key in {@code Object} type
	 * @return {@code HttpBody} value of the specified key {@code null} if no value
	 *         maps to the specified key, or the mapped value cannot be represented
	 *         as {@code HttpBody} type
	 */
	public HttpBody getHttpBody(Object key) {
		if (this.get(key) instanceof HttpBody) {
			return (HttpBody) this.get(key);
		} else {
			return null;
		}
	}

	/**
	 * Return is the instance is performing {@code List}.
	 *
	 * @return {@code true} if the instance is performing {@code List},
	 *         {@code false} otherwise
	 */
	public boolean isList() {
		return this.list;
	}

	/**
	 * Returns {@code Integer} type of the given {@code Object}.
	 *
	 * @param obj the {@code Object} to be parsed
	 * @return the parsed {@code Integer} object
	 */
	private Integer parseInt(Object obj) {
		try {
			String str = obj.toString() + "";
			if (str.indexOf(".") > -1) {
				str = str.substring(0, str.indexOf("."));
			}
			return Integer.parseInt(str);
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * Returns {@code Double} type of the given {@code Object}.
	 *
	 * @param obj the {@code Object} to be parsed
	 * @return the parsed {@code Double} object
	 */
	private Double parseDouble(Object obj) {
		try {
			return Double.parseDouble(obj.toString() + "");
		} catch (Throwable e) {
			return null;
		}
	}

	private Long parseLong(Object obj) {
		try {
			String str = obj.toString();
			if (str.indexOf(".") > -1 && str.indexOf("E") > -1) { // Scientific notation: 1.223E2
				BigDecimal value = new BigDecimal(str);
				return value.longValue();
			} else if (str.indexOf(".") > -1) { // Decimals: 100022.2
				str = str.substring(0, str.indexOf("."));
				return Long.parseLong(str);
			} else {
				return Long.parseLong(str);
			}
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * Returns {@code Boolean} type of the given {@code Object}.
	 *
	 * @param obj the {@code Object} to be parsed
	 * @return the parsed {@code Boolean} object
	 */
	private Boolean parseBoolean(Object obj) {
		return Boolean.parseBoolean(obj.toString() + "");
	}

	/**
	 * Returns {@code String} type of the given {@code Object}.
	 *
	 * @param obj the {@code Object} to be parsed
	 * @return the parsed {@code String} object
	 */
	private String parseString(Object obj) {
		try {
			return obj.toString();
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * Returns a {@code JSON} representation of the instance in {@code String} type.
	 *
	 * @return a {@code JSON} in {@code String}
	 */
	@Override
	public String toString() {
		Object parse = this;
		if (this.list) {
			parse = this.values();
		}
		return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(parse);
	}
}
