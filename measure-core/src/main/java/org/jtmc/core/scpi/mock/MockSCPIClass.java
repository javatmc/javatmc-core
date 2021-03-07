package org.jtmc.core.scpi.mock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a class as a SCPI device mocking candidate.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockSCPIClass {

  /**
   * The name used to reference this mock class, should be Vendor-Model, like Keysight-34460A.
   * 
   * @return Name of this mock class
   */
  String value();
}
