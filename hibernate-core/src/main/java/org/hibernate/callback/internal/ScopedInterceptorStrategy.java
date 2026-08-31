package org.hibernate.callback.internal;

import org.hibernate.Interceptor;
import org.hibernate.callback.spi.InterceptorStrategy;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.resource.beans.container.internal.CdiBeanContainerDelayedAccessImpl;
import org.hibernate.resource.beans.container.internal.CdiBeanContainerExtendedAccessImpl;
import org.hibernate.resource.beans.container.internal.DelayedBeanImpl;
import org.hibernate.resource.beans.container.internal.JpaCompliantLifecycleStrategy;
import org.hibernate.resource.beans.container.spi.FallbackContainedBean;
import org.hibernate.resource.beans.internal.FallbackBeanInstanceProducer;
import org.hibernate.resource.beans.spi.ManagedBean;
import org.hibernate.resource.beans.spi.ManagedBeanRegistry;


/**
 * @author Sean Okafor
 */
public class ScopedInterceptorStrategy {
}
