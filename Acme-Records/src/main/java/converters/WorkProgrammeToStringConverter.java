package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.WorkProgramme;

@Component
@Transactional
public class WorkProgrammeToStringConverter implements Converter<WorkProgramme, String> {
	
	@Override
	public String convert(final WorkProgramme r) {
		String result;

		if (r == null)
			result = null;
		else
			result = String.valueOf(r.getId());
		return result;
	}

}
