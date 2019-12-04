package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.ArtistRepository;

import domain.Artist;

@Component
@Transactional
public class StringToArtistConverter implements Converter<String,Artist> {
	
	// Service --------------
			@Autowired
			private ArtistRepository	artistRepository;


			@Override
			public Artist convert(final String string) {
				Artist result;
				int artistId;

				try {
					if (StringUtils.isEmpty(string))
						result = null;
					else {
						artistId = Integer.valueOf(string);
						result = this.artistRepository.findOne(artistId);
					}
				} catch (final Throwable oops) {
					throw new IllegalArgumentException(oops);
				}

				return result;
	}

}
