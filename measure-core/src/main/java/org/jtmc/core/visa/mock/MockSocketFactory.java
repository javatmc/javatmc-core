package org.jtmc.core.visa.mock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jtmc.core.visa.factory.ISocketFactory;

/**
 * MockSocketFactory can be used to create MockSockets using VISA resource strings.
 */
public class MockSocketFactory implements ISocketFactory {

  private static final Pattern pattern = Pattern.compile(
            "MOCK::(?<class>[^:]*)(::(?!INSTR)(?<name>[^:]*))?(::INSTR)?");
  
  @Override
  public boolean supports(String resourceString) {
    return pattern.matcher(resourceString).matches();
  }

  @Override
  public MockSocket create(String resourceString) {
    Matcher matcher = pattern.matcher(resourceString);
    if (matcher.matches()) {
      String className = matcher.group("class");
      String instrumentName = matcher.group("name");

      return new MockSocket(className, instrumentName);
    }
    throw new IllegalArgumentException();
  }

}
