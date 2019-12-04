package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Record;

@Component
@Transactional
public class RecordToStringConverter implements Converter<Record, String> {

	@Override
	public String convert(final Record r) {
		String result;

		if (r == null)
			result = null;
		else
			result = String.valueOf(r.getId());
		return result;
	}
}
