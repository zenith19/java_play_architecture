package models;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QSession is a Querydsl query type for Session
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QSession extends EntityPathBase<Session> {

    private static final long serialVersionUID = -2087286030L;

    public static final QSession session = new QSession("session");

    public final StringPath authToken = createString("authToken");

    public final StringPath email = createString("email");

    public QSession(String variable) {
        super(Session.class, forVariable(variable));
    }

    public QSession(Path<? extends Session> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSession(PathMetadata<?> metadata) {
        super(Session.class, metadata);
    }

}

