package io.cenet.datastore;

import static com.googlecode.objectify.ObjectifyService.ofy;
import io.cenet.datastore.entity.IdEntity;
import io.cenet.datastore.entity.ListEntity;
import io.cenet.datastore.entity.NamedEntity;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.cmd.Deleter;
import com.googlecode.objectify.cmd.Loader;
import com.googlecode.objectify.cmd.Saver;

public class Objectify {

  static {
    ObjectifyService.register(ListEntity.class);
    ObjectifyService.register(NamedEntity.class);
    ObjectifyService.register(IdEntity.class);
  }

  public static Saver save() {
    return ofy().save();
  }

  public static Loader load() {
    return ofy().load();
  }

  public static Deleter delete() {
    return ofy().delete();
  }

  public static void transaction(final int tries, final VoidWork work) {
    ofy().transactNew(tries, work);
  }
}
