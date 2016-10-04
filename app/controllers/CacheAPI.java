package controllers;

import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;

import memcached.MemcachedService;
import play.cache.CacheApi;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class CacheAPI extends Controller {
	
	private final CacheApi cache;
	private final MemcachedService memcached;
	
	@Inject
	public CacheAPI(final CacheApi cache, final MemcachedService memcached) {
		this.cache = cache;
		this.memcached = memcached;
	}
	
	
	public Result set() {
		final JsonNode kv = request().body().asJson();
		final String key = kv.get("key").asText();
		final JsonNode value = kv.get("value");
		cache.set(key, value);
		return ok();
	}
	
	public Result get(final String key) {
		final JsonNode obj = cache.get(key);
		return ok(obj);
	}
	
	public Result setMemcached() {
		final JsonNode kv = request().body().asJson();
		final String key = kv.get("key").asText();
		final String value = Json.stringify(kv.get("value"));
		memcached.set(key, value, 0);
		return ok();
	}
	
	public CompletionStage<Result> getMemcached(final String key) {
		return memcached.get(key)
				.thenApplyAsync(obj -> ok(Json.parse((String) obj)));
	}
}
