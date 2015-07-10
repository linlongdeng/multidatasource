package weChat.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;

import weChat.domain.primary.Companywechatpub;

public interface CompanywechatpubRepository extends
		JpaRepository<Companywechatpub, Integer> {

	
	public Companywechatpub findFirstByCompanyID(int companyID);
}
