package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.WorkProgrammeRepository;
import domain.WorkProgramme;

public class StringToWorkProgrammeConverter implements Converter<String,WorkProgramme> {

	// Service --------------
	@Autowired
	private WorkProgrammeRepository WorkProgrammeRepository;

	@Override
	public WorkProgramme convert(final String string) {
		WorkProgramme result;
		int WorkProgrammeId;

		try {
			if (StringUtils.isEmpty(string))
				result = null;
			else {
				WorkProgrammeId = Integer.valueOf(string);
				result = this.WorkProgrammeRepository.findOne(WorkProgrammeId);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
