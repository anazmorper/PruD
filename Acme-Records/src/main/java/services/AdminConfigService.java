
package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdminConfigRepository;
import security.Authority;
import domain.AdminConfig;
import domain.Administrator;

@Service
@Transactional
public class AdminConfigService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private AdminConfigRepository	adminConfigRepository;

	@Autowired
	private AdministratorService	administratorService;
	// Supporting services ----------------------------------------------------

	// Constructors -----------------------------------------------------------
	public AdminConfigService() {

	}

	// Simple CRUD methods ----------------------------------------------------


	public Collection<AdminConfig> findAll() {
		Collection<AdminConfig> result;

		result = this.adminConfigRepository.findAll();

		Assert.notNull(result);

		return result;
	}

	public AdminConfig findOne() {

		AdminConfig result;

		result = this.findAll().iterator().next();
		Assert.notNull(result);

		return result;
	}
	
	public AdminConfig findOne(final int id) {
		AdminConfig result;

		result = this.adminConfigRepository.findOne(id);
		Assert.notNull(result);

		return result;
	}
	
	public AdminConfig save(final AdminConfig adminConfig) {
		
		final Administrator admin = this.administratorService.findByPrincipal();
		AdminConfig configuration = null ;
		Assert.notNull(admin);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(admin.getUserAccount().getAuthorities().contains(authorityAdmin));

		if (adminConfig.getDefaultCountryCode().length() > 4){
			
		}else{
			if (adminConfig.getDefaultCountryCode().charAt(0) == '+'){
				 configuration = this.adminConfigRepository.save(adminConfig);
			}
		}
		Assert.notNull(configuration);
	return configuration;
	
	}

	public void delete(final AdminConfig adminConfig) {
		Collection<AdminConfig> adminConfigs;

		adminConfigs = this.findAll();

		Assert.isTrue(adminConfigs.size() > 1);

		this.adminConfigRepository.delete(adminConfig);

	}
	
	public AdminConfig findConf() {

		AdminConfig config;
		config = this.adminConfigRepository.findAll().get(0);
		Assert.notNull(config);
		return config;
	}
	// Complex business rules -----------------------------

	public String getDefaultCountryCode() {
		String result;

		result = this.adminConfigRepository.getDefaultCountryCode();

		return result;
	}
	
	public String getSystemBanner() {
		String result;

		result = this.adminConfigRepository.getSystemBanner();

		return result;
	}
	
	public List<String> palabrasVaciasEspañol(){
		List<String> result = new ArrayList<String>();

		result.add("algún");
		result.add("algo");
		result.add("al");
		result.add("ajenos");
		result.add("ajenas");
		result.add("ajeno");
		result.add("ajena");
		result.add("ahí");
		result.add("aunque");
		result.add("aun");
		result.add("atrás");
		result.add("así");
		result.add("arriba");
		result.add("aquí");
		result.add("aquellos");
		result.add("aquello");
		result.add("aquellas");
		result.add("aquel");
		result.add("aquella");
		result.add("antes");
		result.add("ante");
		result.add("ambos");
		result.add("allí");
		result.add("allá");
		result.add("algunas");
		result.add("algunos");
		result.add("alguno");
		result.add("alguna");
		result.add("algún");
		result.add("contra");
		result.add("contigo");
		result.add("consigues");
		result.add("consiguen");
		result.add("consigue");
		result.add("consigo");
		result.add("conseguir");
		result.add("conseguimos");
		result.add("conmigo");
		result.add("con");
		result.add("como");
		result.add("ciertas");
		result.add("ciertos");
		result.add("cierta");
		result.add("cierto");
		result.add("casi");
		result.add("cada");
		result.add("cabe");
		result.add("bien");
		result.add("bastante");
		result.add("bajo");
		result.add("cual");
		result.add("cuales");
		result.add("cualquier");
		result.add("cualquiera");
		result.add("cualquieras");
		result.add("cuan");
		result.add("cuando");
		result.add("cuanto");
		result.add("cuanta");
		result.add("cuantas");
		result.add("cuantos");
		result.add("de");
		result.add("dejar");
		result.add("del");
		result.add("demás");
		result.add("demasiada");
		result.add("demasiadas");
		result.add("demasiados");
		result.add("demasiado");
		result.add("dentro");
		result.add("desde");
		result.add("dos");
		result.add("el");
		result.add("él");
		result.add("ella");
		result.add("ellos");
		result.add("ellas");
		result.add("ello");
		result.add("empleáis");
		result.add("emplean");
		result.add("emplear");
		result.add("empleas");
		result.add("empleo");
		result.add("en");
		result.add("encima");
		result.add("entonces");
		result.add("entre");
		result.add("era");
		result.add("eras");
		result.add("eramos");
		result.add("eran");
		result.add("eres");
		result.add("es");
		result.add("esa");
		result.add("esas");
		result.add("ese");
		result.add("eso");
		result.add("esos");
		result.add("eses");
		result.add("esta");
		result.add("estas");
		result.add("estaba");
		result.add("hacéis");
		result.add("haces");
		result.add("hace");
		result.add("ha");
		result.add("gueno");
		result.add("fuimos");
		result.add("fui");
		result.add("fueron");
		result.add("fue");
		result.add("fin");
		result.add("etc");
		result.add("estoy");
		result.add("estos");
		result.add("estes");
		result.add("esto");
		result.add("este");
		result.add("estar");
		result.add("están");
		result.add("estamos");
		result.add("estáis");
		result.add("estado");
		result.add("jamás");
		result.add("ir");
		result.add("intento");
		result.add("intentar");
		result.add("intentan");
		result.add("intentamos");
		result.add("intentáis");
		result.add("intentas");
		result.add("intenta");
		result.add("incluso");
		result.add("hasta");
		result.add("hago");
		result.add("hacia");
		result.add("hacer");
		result.add("hacen");
		result.add("hacemos");
		result.add("modo");
		result.add("míos");
		result.add("mío");
		result.add("mientras");
		result.add("mías");
		result.add("mía");
		result.add("mis");
		result.add("mi");
		result.add("menos");
		result.add("me");
		result.add("más");
		result.add("largo");
		result.add("los");
		result.add("lo");
		result.add("las");
		result.add("la");
		result.add("juntos");
		result.add("junto");
		result.add("nuestros");
		result.add("nuestro");
		result.add("nuestra");
		result.add("nuestras");
		result.add("nosotros");
		result.add("nosotras");
		result.add("nos");
		result.add("no");
		result.add("ningunos");
		result.add("ningunas");
		result.add("ninguno");
		result.add("ninguna");
		result.add("ningún");
		result.add("ni");
		result.add("nada");
		result.add("muy");
		result.add("muchos");
		result.add("mucho");
		result.add("muchísimos");
		result.add("muchísimo");
		result.add("muchísimas");
		result.add("muchísima");
		result.add("muchas");
		result.add("mucha");
		result.add("mismos");
		result.add("mismo");
		result.add("mismas");
		result.add("misma");
		result.add("pocos");
		result.add("poco");
		result.add("pocas");
		result.add("poca");
		result.add("pero");
		result.add("parecer");
		result.add("para");
		result.add("otros");
		result.add("otro");
		result.add("otra");
		result.add("otras");
		result.add("os");
		result.add("nunca");
		result.add("puedo");
		result.add("pueden");
		result.add("puede");
		result.add("primero");
		result.add("porque");
		result.add("por qué");
		result.add("por");
		result.add("podrían");
		result.add("podríamos");
		result.add("podríais");
		result.add("podrías");
		result.add("podría");
		result.add("poder");
		result.add("podemos");
		result.add("podéis");
		result.add("pues");
		result.add("que");
		result.add("qué");
		result.add("querer");
		result.add("quién");
		result.add("quienes");
		result.add("quienesquiera");
		result.add("quienquiera");
		result.add("quizá");
		result.add("quizás");
		result.add("sabe");
		result.add("sabes");
		result.add("saben");
		result.add("sabéis");
		result.add("sabemos");
		result.add("saber");
		result.add("solamente");
		result.add("sois");
		result.add("sobre");
		result.add("so");
		result.add("sino");
		result.add("sin");
		result.add("siendo");
		result.add("siempre");
		result.add("sí");
		result.add("si");
		result.add("ser");
		result.add("según");
		result.add("se");
		result.add("solo");
		result.add("sólo");
		result.add("somos");
		result.add("soy");
		result.add("sr");
		result.add("sra");
		result.add("sres");
		result.add("sta");
		result.add("su");
		result.add("sus");
		result.add("suya");
		result.add("suyas");
		result.add("suyo");
		result.add("suyos");
		result.add("tal");
		result.add("tales");
		result.add("también");
		result.add("tampoco");
		result.add("tan");
		result.add("tanta");
		result.add("tantas");
		result.add("tanto");
		result.add("tantos");
		result.add("te");
		result.add("tenéis");
		result.add("tenemos");
		result.add("tener");
		result.add("tengo");
		result.add("ti");
		result.add("tiempo");
		result.add("tiene");
		result.add("tienen");
		result.add("toda");
		result.add("todas");
		result.add("todo");
		result.add("todos");
		result.add("tomar");
		result.add("trabaja");
		result.add("trabajo");
		result.add("trabajáis");
		result.add("trabajamos");
		result.add("trabajan");
		result.add("trabajar");
		result.add("trabajas");
		result.add("tras");
		result.add("tú");
		result.add("tu");
		result.add("tus");
		result.add("tuya");
		result.add("tuyas");
		result.add("tuyo");
		result.add("tuyos");
		result.add("último");
		result.add("ultimo");
		result.add("un");
		result.add("una");
		result.add("uno");
		result.add("unas");
		result.add("unos");
		result.add("usa");
		result.add("usas");
		result.add("usáis");
		result.add("usamos");
		result.add("usan");
		result.add("usar");
		result.add("uso");
		result.add("usted");
		result.add("ustedes");
		result.add("va");
		result.add("van");
		result.add("vais");
		result.add("valor");
		result.add("vamos");
		result.add("varias");
		result.add("varios");
		result.add("vaya");
		result.add("verdadera");
		result.add("vosotras");
		result.add("vosotros");
		result.add("voy");
		result.add("vuestra");
		result.add("vuestras");
		result.add("vuestro");
		result.add("vuestros");
		result.add("y");
		result.add("ya");
		result.add("yo");
		
		return result;
	}
	
	public List<String> palabraVaciasIngles(){
		String[] palabras = {"a","able","about","across","after","all","almost","also","am","among","an","and","any","are","as","at","be","because","been","but","by","can","cannot","could","dear","did","do","does","either","else","ever","every","for","from","get","got","had","has","have","he","her","hers","him","his","how","however","i","if","in","into","is","it","its","just","least","let","like","likely","may","me","might","most","must","my","neither","no","nor","not","of","off","often","on","only","or","other","our","own","rather","said","say","says","she","should","since","so","some","than","that","the","their","them","then","there","these","they","this","tis","to","too","twas","us","wants","was","we","were","what","when","where","which","while","who","whom","why","will","with","would","yet","you","your"};
		List<String> result = new ArrayList<String>(Arrays.asList(palabras));
		return result;
	}

	public String getWelcomeMessageEnglish() {
		String result;

		result = this.adminConfigRepository.getWelcomeMessageEnglish();

		return result;
	}

	public String getWelcomeMessageSpanish() {
		String result;

		result = this.adminConfigRepository.getWelcomeMessageSpanish();

		return result;
	}

	public Collection<String> creditCardNames() {
		Collection<String> res;

		res = this.adminConfigRepository.getCreditCardNames();
		Assert.notNull(res);

		return res;
	}
}
