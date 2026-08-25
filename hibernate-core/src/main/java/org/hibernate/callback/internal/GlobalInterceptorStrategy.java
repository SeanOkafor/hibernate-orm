/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.callback.internal;

import org.hibernate.Interceptor;
import org.hibernate.callback.spi.InterceptorStrategy;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.resource.beans.container.internal.CdiBeanContainerDelayedAccessImpl;
import org.hibernate.resource.beans.container.internal.CdiBeanContainerExtendedAccessImpl;
import org.hibernate.resource.beans.container.spi.FallbackContainedBean;
import org.hibernate.resource.beans.internal.FallbackBeanInstanceProducer;
import org.hibernate.resource.beans.spi.ManagedBean;
import org.hibernate.resource.beans.spi.ManagedBeanRegistry;


/**
 * @author Sean Okafor
 */
public class GlobalInterceptorStrategy implements InterceptorStrategy {
	private final ManagedBean<? extends Interceptor> interceptorBean;

	//Factory needed to access the service registry
	public GlobalInterceptorStrategy(Class<? extends Interceptor> interceptorClass,
									 SessionFactoryImplementor sessionFactory) {
		final var mbr = sessionFactory.getServiceRegistry().getService( ManagedBeanRegistry.class );
		final var bc = mbr.getBeanContainer();

		if(bc == null) {
			interceptorBean = new FallbackContainedBean<>(interceptorClass,
					FallbackBeanInstanceProducer.INSTANCE  );
		}
		else if (bc instanceof CdiBeanContainerExtendedAccessImpl ||
				 bc instanceof CdiBeanContainerDelayedAccessImpl ) {
			interceptorBean = mbr.getBean(interceptorClass);
		}
		else {
			interceptorBean = mbr.getBean( interceptorClass );
		}
	}

	@Override
	public Interceptor getInterceptorForSession(SessionFactoryImplementor factory) {
		return interceptorBean.getBeanInstance();
	}

}
