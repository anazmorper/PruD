package converters;

import java.net.URLEncoder;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Attachment;

@Component
@Transactional
public class AttachmentStringConverter implements Converter<Attachment, String> {

	@Override
	public String convert(Attachment attachment) {
		String result;
		StringBuilder builder;

		if (attachment == null)
			result = null;
		else
			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(attachment.getUrl(), "UTF-8"));
				result = builder.toString();
			} catch (final Throwable oops) {
				throw new IllegalArgumentException(oops);

			}
		return result;
	}
}