package io.cenet.endpoints;

import io.cenet.datastore.Objectify;
import io.cenet.datastore.entity.NamedEntity;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;

@Api(name = "named", version = "v1")
public class NamedApi {

  @ApiMethod(httpMethod = "get", path = "{name}")
  public NamedEntity get(@Named("name") final String name) {
    final Key<NamedEntity> key = Key.create(NamedEntity.class, name);
    return Objectify.load().key(key).now();
  }

  @ApiMethod(httpMethod = "put", path = "{name}")
  public void put(@Named("name") final String name) {
    // PUT and DELETE should be idempotent works
    Objectify.transaction(100, new VoidWork() {
      @Override public void vrun() {
        final NamedEntity namedEntity = new NamedEntity(name);
        Objectify.save().entity(namedEntity).now();
      }
    });
  }

  @ApiMethod(httpMethod = "delete", path = "{name}")
  public void delete(@Named("name") final String name) {
    // PUT and DELETE should be idempotent works
    Objectify.transaction(100, new VoidWork() {
      @Override public void vrun() {
        final Key<NamedEntity> key = Key.create(NamedEntity.class, name);
        Objectify.delete().key(key).now();
      }
    });
  }
}
