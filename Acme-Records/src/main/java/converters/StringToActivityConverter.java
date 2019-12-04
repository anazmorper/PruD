package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.ActivityRepository;

import domain.Activity;

@Component
@Transactional
public class StringToActivityConverter implements Converter<String,Activity> {
	
	// Service --------------
			@Autowired
			private ActivityRepository	activityRepository;


			@Override
			public Activity convert(final String string) {
				Activity result;
				int activityId;

				try {
					if (StringUtils.isEmpty(string))
						result = null;
					else {
						activityId = Integer.valueOf(string);
						result = this.activityRepository.findOne(activityId);
					}
				} catch (final Throwable oops) {
					throw new IllegalArgumentException(oops);
				}

				return result;
	}

}
