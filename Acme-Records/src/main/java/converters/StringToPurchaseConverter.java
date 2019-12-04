package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.PurchaseRepository;
import domain.Purchase;

public class StringToPurchaseConverter implements Converter<String,Purchase> {

	// Service --------------
	@Autowired
	private PurchaseRepository purchaseRepository;

	@Override
	public Purchase convert(final String string) {
		Purchase result;
		int purchaseId;

		try {
			if (StringUtils.isEmpty(string))
				result = null;
			else {
				purchaseId = Integer.valueOf(string);
				result = this.purchaseRepository.findOne(purchaseId);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
