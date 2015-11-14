package io.cenet.endpoints;

import io.cenet.datastore.Objectify;
import io.cenet.datastore.entity.IdEntity;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.VoidWork;

@Api(name = "id", version = "v1")
public class IdApi {

  @ApiMethod(httpMethod = "get", path = "{id}")
  public IdEntity getId(@Named("id") final long id) {
    final Key<IdEntity> key = Key.create(IdEntity.class, id);
    return Objectify.load().key(key).now();
  }

  @ApiMethod(httpMethod = "post")
  public Long postId(final String content) {
    final IdEntity entity = new IdEntity();
    entity.searchResult = content;
    final Key<IdEntity> key = Objectify.save().entity(entity).now();
    return key.getId();
  }

  @ApiMethod(httpMethod = "put", path = "{id}")
  public void putId(@Named("id") final long id, final String content) {
    // PUT and DELETE should be idempotent works
    Objectify.transaction(100, new VoidWork() {
      @Override public void vrun() {
        final Key<IdEntity> key = Key.create(IdEntity.class, id);
        final IdEntity idEntity = Objectify.load().key(key).now();
        idEntity.searchResult = content;
        Objectify.save().entity(idEntity).now();
      }
    });
  }

  @ApiMethod(httpMethod = "delete", path = "{id}")
  public void deleteId(@Named("id") final long id) {
    // PUT and DELETE should be idempotent works
    Objectify.transaction(100, new VoidWork() {
      @Override public void vrun() {
        final Key<IdEntity> key = Key.create(IdEntity.class, id);
        Objectify.delete().key(key).now();
      }
    });
  }
}
