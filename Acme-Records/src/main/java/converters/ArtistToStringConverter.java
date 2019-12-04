package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Artist;

@Component
@Transactional
public class ArtistToStringConverter implements Converter<Artist, String> {

	@Override
	public String convert(final Artist r) {
		String result;

		if (r == null)
			result = null;
		else
			result = String.valueOf(r.getId());
		return result;
	}
}
