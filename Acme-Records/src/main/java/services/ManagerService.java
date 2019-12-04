
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Artist;
import domain.Manager;
import domain.Record;
import domain.WorkProgramme;

@Service
@Transactional
public class ManagerService {

	// Managed Repository -----------------------------------------
	@Autowired
	private ManagerRepository	managerRepository;

	@Autowired
	private UserAccountService	userAccountService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods -----------------------------------------
	public ManagerService() {
		super();
	}

	public Manager save(final Manager b) {
		Assert.notNull(b);
		Manager result;
		this.checkPrincipal();

		result = (Manager) this.actorService.save(b);
		return result;

	}

	public Manager saveC(final Manager b) {
		Assert.notNull(b);
		Manager result;

		result = (Manager) this.actorService.save(b);
		return result;

	}

	public Manager save2(final Manager b) {
		Assert.notNull(b);
		Manager result;

		result = this.managerRepository.save(b);
		return result;

	}

	public Manager create() {

		Manager result;
		UserAccount userAccount;
		Authority authority;
		Collection<Authority> authorities;

		userAccount = this.userAccountService.create();
		userAccount.setActive(true);
		userAccount.setUsername("");
		userAccount.setPassword("");
		authority = new Authority();
		authority.setAuthority(Authority.MANAGER);
		authorities = new ArrayList<Authority>();
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		result = new Manager();
		result.setUserAccount(userAccount);

		return result;

	}

	public Manager findOne(final int bh) {
		Manager res;

		res = this.managerRepository.findOne(bh);
		Assert.notNull(res);

		return res;
	}

	public Collection<Manager> findAll() {
		Collection<Manager> r;

		Assert.notNull(this.managerRepository);
		r = this.managerRepository.findAll();
		Assert.notNull(r);

		return r;
	}

	public void delete(final Manager manager) {
		assert manager != null;
		assert manager.getId() != 0;

		Assert.isTrue(this.managerRepository.exists(manager.getId()));
		Manager principal;
		principal = this.findOneByPrincipal();
		Assert.isTrue(manager.equals(principal));

		this.managerRepository.delete(manager);
	}
	//----------------------------------------------------

	public Manager findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Manager a;
		a = this.managerRepository.findByUserAccountId(userAccount.getId());
		return a;
	}

	public Manager findOneByPrincipal() {
		Manager result;
		result = this.managerRepository.findByUserAccountId(LoginService.getPrincipal().getId());
		Assert.notNull(result);
		return result;
	}

	public Collection<Artist> getArtistsByManager(final Integer id) {
		Collection<Artist> res = new ArrayList<Artist>();
		Assert.notNull(this.managerRepository);
		res = this.managerRepository.findArtistsByManager(id);
		return res;
	}
	
	public Collection<Artist> getArtistsByManagerSinRecord(final Integer id) {
		Collection<Artist> res = new ArrayList<Artist>();
		Assert.notNull(this.managerRepository);
		res = this.managerRepository.findArtistsByManagerSinRecord(id);
		return res;
	}
	
	public void checkPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		final Collection<Authority> authorities = userAccount.getAuthorities();
		Assert.notNull(authorities);

		final Authority auth = new Authority();
		auth.setAuthority(Authority.MANAGER);

		Assert.isTrue(authorities.contains(auth));
	}

	//Reconstructor
	public Manager reconstruct(final Manager a, final BindingResult binding) {
		Manager result;

		if (a.getId() == 0)
			result = a;
		else {
			result = this.managerRepository.findOne(a.getId());
			result.setName(a.getName());
			result.setEmail(a.getEmail());
			this.validator.validate(result, binding);
		}
		return result;
	}

	public Manager findTopManager() {
		Manager result = null;
		Integer recordSellingTop = 0;

		for (final Manager m : this.findAll()) {
			Integer recordSelling = 0;
			for (final WorkProgramme wk : m.getWorkProgrammes())
				if (null != wk.getRecords() && !wk.getRecords().isEmpty())
					for (final Record r : wk.getRecords())
						if (null != r.getPurchases() && !r.getPurchases().isEmpty())
							recordSelling = recordSelling + r.getPurchases().size();
			if (recordSelling > recordSellingTop) {
				result = m;
				recordSellingTop = recordSelling;
			}
		}

		return result;
	}

}
