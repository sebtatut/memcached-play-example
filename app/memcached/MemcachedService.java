package memcached;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.spy.memcached.MemcachedClient;
import play.Logger;

@Singleton
public class MemcachedService implements Memcached {

	private final MemcachedClient client;
	
	@Inject
	private MemcachedService(final MemcachedClientProvider clientProvider) {
		this.client = clientProvider.get();
	}
	
	
	@Override
	public <T> T get(String key) {
		final Future<Object> future = client.asyncGet(key);
		try {
			return (T) future.get(1, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			Logger.error("BUMMER!", e);
			future.cancel(true);
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> void set(String key, T obj, int expiration) {
		client.set(key, expiration, obj);
	}

}
