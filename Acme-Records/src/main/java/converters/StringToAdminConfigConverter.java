
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.AdminConfigRepository;
import domain.AdminConfig;

@Component
@Transactional
public class StringToAdminConfigConverter implements Converter<String, AdminConfig> {

	@Autowired
	private AdminConfigRepository	adminConfigRepository;


	@Override
	public AdminConfig convert(final String text) {
		AdminConfig result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.adminConfigRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
