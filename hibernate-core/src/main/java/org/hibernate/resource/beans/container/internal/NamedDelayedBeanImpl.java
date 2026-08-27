package org.hibernate.resource.beans.container.internal;

import org.hibernate.resource.beans.container.spi.BeanContainer;
import org.hibernate.resource.beans.container.spi.BeanLifecycleStrategy;
import org.hibernate.resource.beans.container.spi.ContainedBeanImplementor;
import org.hibernate.resource.beans.spi.BeanInstanceProducer;

/**
 * @author Sean Okafor
 */
public class NamedDelayedBeanImpl <B> implements ContainedBeanImplementor<B> {
	private final String beanName;
	private final Class<B> beanType;
	private final BeanLifecycleStrategy lifecycleStrategy;
	private final BeanInstanceProducer fallbackProducer;
	private final BeanContainer beanContainer;
	private final boolean initializeDelegatedEagerly;
	private ContainedBeanImplementor<B> delegateBean;

	public NamedDelayedBeanImpl(String beanName, Class<B> beanType, BeanLifecycleStrategy lifecycleStrategy,
			BeanInstanceProducer fallbackProducer, BeanContainer beanContainer,
			boolean initializeDelegatedEagerly) {
		this.beanName = beanName;
		this.beanType = beanType;
		this.lifecycleStrategy = lifecycleStrategy;
		this.fallbackProducer = fallbackProducer;
		this.beanContainer = beanContainer;
		this.initializeDelegatedEagerly = initializeDelegatedEagerly;
	}

	@Override
	public Class<B> getBeanClass() {
		return beanType;
	}

	@Override
	public void initialize(){
		if (delegateBean == null) {
			delegateBean = lifecycleStrategy.createBean(beanName, beanType, fallbackProducer, beanContainer);
		}
		if (initializeDelegatedEagerly) {
			delegateBean.initialize();
		}
	}

	@Override
	public B getBeanInstance() {
		if ( delegateBean == null ) {
			initialize();
		}
		return delegateBean.getBeanInstance();
	}

	@Override
	public void release() {
		if ( delegateBean != null ) {
			delegateBean.release();
		}
		if (initializeDelegatedEagerly ) {
			delegateBean = null;
		}
	}
}
