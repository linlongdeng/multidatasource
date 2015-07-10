package weChat.core.jpa;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.internal.util.StringHelper;
import org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy;

@SuppressWarnings("serial")
public class CustomNameNameingStrategy extends SpringNamingStrategy {

	@Override
	public String propertyToColumnName(String propertyName) {
		//去除下划线
		return  StringHelper.unqualify(propertyName);
	}

	
	

}
