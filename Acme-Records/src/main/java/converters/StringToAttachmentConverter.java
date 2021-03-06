package converters;

import java.net.URLDecoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Attachment;

@Component
@Transactional
public class StringToAttachmentConverter implements Converter<String, Attachment> {

	@Override
	public Attachment convert(String text) {
		Attachment result;
		String parts[];

		if (text == null)
			result = null;
		else
			try {
				parts = text.split("\\|");
				result = new Attachment();
				result.setUrl(URLDecoder.decode(parts[0], "UTF-8"));
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);
			}
		return result;
	}
}