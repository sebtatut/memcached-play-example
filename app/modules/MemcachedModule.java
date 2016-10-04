package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import memcached.Memcached;
import memcached.MemcachedClientProvider;
import memcached.MemcachedService;
import net.spy.memcached.MemcachedClient;

public class MemcachedModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(MemcachedClient.class).toProvider(MemcachedClientProvider.class)
								   .in(Singleton.class);
		bind(Memcached.class).to(MemcachedService.class)
							 .in(Singleton.class);
	}

}
