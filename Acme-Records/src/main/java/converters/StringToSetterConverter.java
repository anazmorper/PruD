package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.SetterRepository;
import domain.Setter;

@Component
@Transactional
public class StringToSetterConverter implements Converter<String, Setter> {
	
	// Service --------------
		@Autowired
		private SetterRepository	setterRepository;


		@Override
		public Setter convert(final String string) {
			Setter result;
			int setterId;

			try {
				if (StringUtils.isEmpty(string))
					result = null;
				else {
					setterId = Integer.valueOf(string);
					result = this.setterRepository.findOne(setterId);
				}
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}

			return result;
}
}

