package weChat.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;

import weChat.domain.primary.Interfacecheck;

public interface InterfacecheckRepository extends
		JpaRepository<Interfacecheck, Integer> {

	public Interfacecheck findFirstByAppIDAndAppKey(int appid, String appkey);
	
	public Interfacecheck findFirstByAccessToken(String accessToken);
}
