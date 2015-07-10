package weChat.parameter.manager;

import weChat.core.metatype.Dto;

/**
 * 带数据的返回参数
 * 
 * @author deng
 * @date 2015年5月21日
 * @version 1.0.0
 */
public class MRespDataParam extends MRespParam {

	private Dto data;

	public MRespDataParam(int ret, String msg) {
		super(ret, msg);

	}

	public MRespDataParam(Dto data) {
		super();
		this.data = data;
	}

	public MRespDataParam(int ret, String msg, Dto data) {
		super(ret, msg);
		this.data = data;
	}

	public Dto getData() {
		return data;
	}

	public void setData(Dto data) {
		this.data = data;
	}

}
