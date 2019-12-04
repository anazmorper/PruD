package converters;

import org.springframework.core.convert.converter.Converter;

import security.Authority;


public class AuthorityToStringConverter implements Converter<Authority, String>{
	
	@Override
	public String convert(Authority a){
		String result;
		
		if (a  == null){
			result = null;
		}else{
			result = String.valueOf(a.getAuthority());
		}
		return result;
	}

}
