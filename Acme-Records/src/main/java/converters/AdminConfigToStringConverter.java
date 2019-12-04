
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.AdminConfig;

@Component
@Transactional
public class AdminConfigToStringConverter implements Converter<AdminConfig, String> {

	@Override
	public String convert(final AdminConfig adminConfig) {
		String result;

		if (adminConfig == null)
			result = null;
		else
			result = String.valueOf(adminConfig.getId());
		return result;
	}
}
