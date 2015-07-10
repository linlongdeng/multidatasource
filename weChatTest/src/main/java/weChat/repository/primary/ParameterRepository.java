package weChat.repository.primary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import weChat.domain.primary.Parameter;
/**
 * 参数Repository
 * @author deng
 * @date 2015年5月22日
 * @version 1.0.0
 */
public interface ParameterRepository extends JpaRepository<Parameter, Integer>{

	
	public Parameter findFirstByCompanyIDAndParameterName(int companyID,String parameterName);
	/**
	 * 查找参数
	 * @param parameterName
	 * @return
	 */
	public Parameter findFirstByParameterName(String parameterName);
	
	/**查找多个参数
	 * @param companyID
	 * @param parameterNameList
	 * @return
	 */
	public List<Parameter> findByCompanyIDAndParameterNameIn(int companyID, List<String> parameterNameList);
	
	
}
