package anotations;

import actions.HasRoleAction;
import play.mvc.With;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by rownak on 11/10/16.
 */
@With(HasRoleAction.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

public @interface HasRoles {
    // TODO : define anottaions attribute. this is role name that allow call method.
    String[] value() default {"default"};

}
