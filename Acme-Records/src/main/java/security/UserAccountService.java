
package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class UserAccountService {

	// Managed repository 
	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Services

	// Constructor
	public UserAccountService() {
		super();
	}

	// CRUD 

	public UserAccount create() {

		UserAccount result;

		result = new UserAccount();

		return result;

	}
	public UserAccount findByUserName(final String username) {
		Assert.notNull(username);
		final UserAccount result = this.userAccountRepository.findByUsername(username);
		return result;
	}

	public UserAccount findOne(final int userAccountId) {
		return this.userAccountRepository.findOne(userAccountId);
	}

	public UserAccount save(final UserAccount userAccount) {
		return this.userAccountRepository.save(userAccount);
	}
	public void delete(final UserAccount userAccount) {
		this.userAccountRepository.delete(userAccount);
	}

	// Other methods

}
