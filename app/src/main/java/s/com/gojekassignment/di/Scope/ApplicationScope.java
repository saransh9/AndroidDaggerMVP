package s.com.gojekassignment.di.Scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;


@Scope
@Retention(RetentionPolicy.CLASS)
public @interface ApplicationScope {
}
