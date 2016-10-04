package memcached;

public interface Memcached {
	<T> T get(final String key);
	<T> void set(final String key, final T obj, int expiration);
}
