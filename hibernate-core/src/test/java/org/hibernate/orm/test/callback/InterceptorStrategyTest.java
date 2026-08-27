/*
 * SPDX-License-Identifier: Apache-2.0
 * Copyright Red Hat Inc. and Hibernate Authors
 */
package org.hibernate.orm.test.callback;

import org.hibernate.Interceptor;
import org.hibernate.callback.internal.GlobalInterceptorStrategy;
import org.hibernate.callback.internal.NoInterceptorStrategy;
import org.hibernate.callback.internal.ProvidedInterceptorStrategy;
import org.hibernate.callback.spi.InterceptorStrategy;
import org.hibernate.internal.EmptyInterceptor;
import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.hibernate.type.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Tests for {@link InterceptorStrategy} implementations.
 *
 * @author Sean Okafor
 */
@SuppressWarnings("JUnitMalformedDeclaration")
@DomainModel
@SessionFactory
public class InterceptorStrategyTest {

	// -- NoInterceptorStrategy tests --

	@Test
	public void testNoInterceptorStrategyReturnsEmptyInterceptor() {
		final var strategy = NoInterceptorStrategy.INSTANCE;
		final var interceptor = strategy.getInterceptorForSession( null );
		assertSame( EmptyInterceptor.INSTANCE, interceptor );
	}

	@Test
	public void testNoInterceptorStrategySingleton() {
		assertSame( NoInterceptorStrategy.INSTANCE, NoInterceptorStrategy.INSTANCE );
	}

	@Test
	public void testNoInterceptorStrategyReturnsSameInstanceEveryTime() {
		final var strategy = NoInterceptorStrategy.INSTANCE;
		final var first = strategy.getInterceptorForSession( null );
		final var second = strategy.getInterceptorForSession( null );
		assertSame( first, second );
	}

	// -- ProvidedInterceptorStrategy tests --

	@Test
	public void testProvidedInterceptorStrategyReturnsGivenInstance() {
		final var interceptor = new TestInterceptor();
		final var strategy = new ProvidedInterceptorStrategy( interceptor );
		assertSame( interceptor, strategy.getInterceptorForSession( null ) );
	}

	@Test
	public void testProvidedInterceptorStrategyReturnsSameInstanceEveryTime() {
		final var interceptor = new TestInterceptor();
		final var strategy = new ProvidedInterceptorStrategy( interceptor );
		final var first = strategy.getInterceptorForSession( null );
		final var second = strategy.getInterceptorForSession( null );
		assertSame( first, second );
	}

	// -- GlobalInterceptorStrategy tests --

	@Test
	public void testGlobalInterceptorStrategyCreatesInstance(SessionFactoryScope factoryScope) {
		final var factory = factoryScope.getSessionFactory();
		final var strategy = new GlobalInterceptorStrategy( TestInterceptor.class, factory );
		final var interceptor = strategy.getInterceptorForSession( factory );
		assertNotNull( interceptor );
		assertInstanceOf( TestInterceptor.class, interceptor );
	}

	@Test
	public void testGlobalInterceptorStrategyReturnsSameInstanceEveryTime(SessionFactoryScope factoryScope) {
		final var factory = factoryScope.getSessionFactory();
		final var strategy = new GlobalInterceptorStrategy( TestInterceptor.class, factory );
		final var first = strategy.getInterceptorForSession( factory );
		final var second = strategy.getInterceptorForSession( factory );
		assertSame( first, second );
	}

	/**
	 * A simple test interceptor with a no-arg constructor.
	 */
	public static class TestInterceptor implements Interceptor {
		@Override
		public boolean onFlushDirty(
				Object entity,
				Object id,
				Object[] currentState,
				Object[] previousState,
				String[] propertyNames,
				Type[] types) {
			return false;
		}
	}
}
