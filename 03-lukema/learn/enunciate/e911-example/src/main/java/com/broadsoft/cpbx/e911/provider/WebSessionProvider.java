package com.broadsoft.cpbx.e911.provider;


import javax.inject.Provider;

import com.broadsoft.cpbx.e911.api.ISession;
import com.broadsoft.cpbx.e911.domain.WebSession;

public class WebSessionProvider implements Provider<ISession> {

	@Override
	public ISession get() {
		return WebSession.getInstance();
	}
}
