/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.callback.internal;

import org.hibernate.callback.spi.InterceptorStrategy;
import org.hibernate.Interceptor;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.resource.beans.internal.FallbackBeanInstanceProducer;

/**
 * @author Sean Okafor
 */
public class GlobalInterceptorStrategy implements InterceptorStrategy {
	private final Interceptor interceptor;

	public GlobalInterceptorStrategy(Class<? extends Interceptor> interceptorClass) {
		this.interceptor = FallbackBeanInstanceProducer.INSTANCE.produceBeanInstance(interceptorClass);
	}

	@Override
	public Interceptor getInterceptorForSession(SessionFactoryImplementor factory) {
		return interceptor;
	}

}
