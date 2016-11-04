package models;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QUserBranch is a Querydsl query type for UserBranch
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QUserBranch extends EntityPathBase<UserBranch> {

    private static final long serialVersionUID = -360283279L;

    public static final QUserBranch userBranch = new QUserBranch("userBranch");

    public final StringPath branch = createString("branch");

    public final NumberPath<Integer> noOfUser = createNumber("noOfUser", Integer.class);

    public QUserBranch(String variable) {
        super(UserBranch.class, forVariable(variable));
    }

    public QUserBranch(Path<? extends UserBranch> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserBranch(PathMetadata<?> metadata) {
        super(UserBranch.class, metadata);
    }

}

