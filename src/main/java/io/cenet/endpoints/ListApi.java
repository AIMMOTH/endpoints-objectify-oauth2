package io.cenet.endpoints;

import io.cenet.datastore.Objectify;
import io.cenet.datastore.entity.ListEntity;
import io.cenet.endpoints.oauth2.Auth0Authentication;

import java.util.Arrays;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.AuthLevel;
import com.google.api.server.spi.config.Named;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.cmd.LoadType;

@Api(
    name = "list",
    version = "v1",
    authenticators = {Auth0Authentication.class}
    )
public class ListApi {

  @ApiMethod(httpMethod = "post", path = "list", authLevel = AuthLevel.REQUIRED)
  public void post(@Named("content") final String csv) {
    final ListEntity list = new ListEntity();
    list.admins = Arrays.asList(csv.split(","));
    Objectify.save().entity(list).now();
  }

  @ApiMethod(httpMethod = "put", path = "list", authLevel = AuthLevel.REQUIRED)
  public void put(@Named("content") final String csv) {
    // PUT and DELETE should be performed idempotent.
    Objectify.transaction(100, new VoidWork() {
      @Override public void vrun() {
        final LoadType<ListEntity> type = Objectify.load().type(ListEntity.class);
        final ListEntity list = type.limit(1).list().get(0);
        list.admins = Arrays.asList(csv.split(","));
        Objectify.save().entity(list).now();
      }
    });
  }

  @ApiMethod(httpMethod = "delete", path = "list", authLevel = AuthLevel.REQUIRED)
  public void delete() {
    // PUT and DELETE should be performed idempotent.
    Objectify.transaction(100, new VoidWork() {
      @Override public void vrun() {
        final LoadType<ListEntity> type = Objectify.load().type(ListEntity.class);
        final ListEntity list = type.limit(1).list().get(0);
        Objectify.delete().entity(list).now();
      }
    });
  }

  @ApiMethod(httpMethod = "get", path = "list", authLevel = AuthLevel.REQUIRED)
  public ListEntity get() {
    final LoadType<ListEntity> type = Objectify.load().type(ListEntity.class);
    return type.limit(1).list().get(0);
  }

}
