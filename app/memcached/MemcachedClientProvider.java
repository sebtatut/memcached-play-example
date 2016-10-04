package memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

import javax.inject.*;

import net.spy.memcached.MemcachedClient;
import play.Logger;
import play.inject.ApplicationLifecycle;

@Singleton
public class MemcachedClientProvider implements Provider<MemcachedClient> {

	private final MemcachedClient client;
	
	@Inject
	public MemcachedClientProvider(final ApplicationLifecycle lifecycle) {
		try {
			this.client = new MemcachedClient(new InetSocketAddress("localhost", 11211));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		lifecycle.addStopHook(() -> {
			Logger.debug("Stopping memcached client...");
			client.shutdown();
			return CompletableFuture.completedFuture(null);
		});
	}
	
	@Override
	public MemcachedClient get() {
		return this.client;
	}
	
}
