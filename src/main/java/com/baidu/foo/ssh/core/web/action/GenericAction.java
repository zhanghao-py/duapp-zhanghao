package com.baidu.foo.ssh.core.web.action;

import com.baidu.foo.ssh.core.web.action.dto.AbstractGenericModel;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class GenericAction extends ActionSupport implements ModelDriven<AbstractGenericModel> {

	private static final long serialVersionUID = 8771346935688971680L;
	protected AbstractGenericModel model;

	@Override
	public AbstractGenericModel getModel() {
		return model;
	}

}
