package org.hibernate.callback.spi;


import org.hibernate.Interceptor;
import org.hibernate.engine.spi.SessionFactoryImplementor;

/**
 * @author Sean Okafor
 */
public interface InterceptorStrategy {
	Interceptor getInterceptorForSession(SessionFactoryImplementor factory);
}
