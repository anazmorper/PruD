package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Setter;

@Component
@Transactional
public class SetterToStringConverter implements Converter<Setter,String> {

	@Override
	public String convert(final Setter a) {
		String result;

		if (a == null)
			result = null;
		else
			result = String.valueOf(a.getId());
		return result;
	}
}

