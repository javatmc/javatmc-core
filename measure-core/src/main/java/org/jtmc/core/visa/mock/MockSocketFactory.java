package org.jtmc.core.visa.mock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jtmc.core.visa.factory.ISocketFactory;

/**
 * MockSocketFactory
 */
public class MockSocketFactory implements ISocketFactory {

	private final static Pattern pattern = Pattern.compile("MOCK::(?<class>[^:]*)(::(?!INSTR)(?<name>[^:]*))?(::INSTR)?");
	
	@Override
	public boolean supports(String resourceString) {
		return pattern.matcher(resourceString).matches();
	}

	@Override
	public MockSocket create(String resourceString) {
		Matcher matcher = pattern.matcher(resourceString);
		if(matcher.matches()) {
			String className = matcher.group("class");
			String instrumentName = matcher.group("name");

			return new MockSocket(className, instrumentName);
		}
		throw new IllegalArgumentException();
	}

}