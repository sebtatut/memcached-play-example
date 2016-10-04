package memcached;

import java.util.concurrent.CompletionStage;

public interface Memcached {
	<T> CompletionStage<T> get(final String key);
	<T> void set(final String key, final T obj, int expiration);
}
