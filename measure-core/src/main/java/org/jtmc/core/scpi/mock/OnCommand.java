package org.jtmc.core.scpi.mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * OnCommand is used to annotate methods to be invoked when 
 * the given command is received.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnCommand {

  /**
   * Regular expression, the annotated method will be invoked when 
   * this expression matches the incoming command.
   * 
   * @return Command's path
   */
  String value();
}
