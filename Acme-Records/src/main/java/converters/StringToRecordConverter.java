package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.RecordRepository;

import domain.Record;

@Component
@Transactional
public class StringToRecordConverter implements Converter<String,Record> {
	
	// Service --------------
			@Autowired
			private RecordRepository	recordRepository;


			@Override
			public Record convert(final String string) {
				Record result;
				int recordId;

				try {
					if (StringUtils.isEmpty(string))
						result = null;
					else {
						recordId = Integer.valueOf(string);
						result = this.recordRepository.findOne(recordId);
					}
				} catch (final Throwable oops) {
					throw new IllegalArgumentException(oops);
				}

				return result;
	}

}
