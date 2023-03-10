package com.railweb.shared.domain.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletionStage;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.lang.NonNull;

import com.railweb.shared.domain.command.Command;
import com.railweb.shared.domain.command.CommandFailure;
import com.railweb.shared.domain.command.CommandHandler;
import com.railweb.shared.domain.events.DomainEvent;
import com.sun.istack.NotNull;

import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;

@MappedSuperclass
@Slf4j
public abstract class AbstractAggregateRoot<ID extends DomainObjectId<?>> extends AbstractEntity<ID> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1038237265945095709L;
	@Transient
	private final List<DomainEvent> domainEvents = new ArrayList<>();
	@Transient
	private final ApplicationContext applicationContext;
	@Transient
	private AggregateRootBehavior<ID> behavior;

	/**
	 * Default constructor
	 */
	protected AbstractAggregateRoot(ApplicationContext applicationContext, ID entityId) {
		this.id = entityId;
		this.applicationContext = applicationContext;
	}

	/**
	 * Copy constructor. Please note that any registered domain events are
	 * <b>not</b> copied.
	 *
	 * @param source the aggregate root to copy from.
	 */
	protected AbstractAggregateRoot(@NonNull AbstractAggregateRoot<ID> source) {
		super(source);
		this.applicationContext = source.applicationContext;
	}

	public <A extends Command, B extends DomainEvent> CompletionStage<Either<CommandFailure, B>> handle(A command){
		CommandHandler<A,B,ID> commandHandler =  (CommandHandler<A,B,ID>) behavior.handlers.get(command.getClass());
		return commandHandler.execute(command, id);
	}
	
	public <A extends Command, B extends DomainEvent> CommandHandler<A,B,ID> getHandler(Class<? extends CommandHandler> commandHandler){
		return applicationContext.getBean(commandHandler);
	}
	
	protected abstract AggregateRootBehavior<?> initialBehavior();
	
	protected void registerEvent(@NotNull DomainEvent event) {
		Objects.requireNonNull(event, "event most not null");
		domainEvents.add(Objects.requireNonNull(event));
	}
	
	@AfterDomainEventPublication
	protected void clearDomainEvents() {
		this.domainEvents.clear();
	}

	protected List<DomainEvent> domainEvents() {
		return Collections.unmodifiableList(domainEvents);
	}
	
	
	public class AggregateRootBehavior<ID extends DomainObjectId<?>>{
		
		protected final Map<Class<? extends Command>, 
			CommandHandler<? extends Command, ? extends DomainEvent, ID>> handlers;
		
		public AggregateRootBehavior(Map<Class<? extends Command>, CommandHandler<? extends Command, ? extends DomainEvent,ID>> handlers) {
			this.handlers = Collections.unmodifiableMap(handlers);
		}
	}
	
	public class AggregateRootBehaviorBuilder<ID extends DomainObjectId<?>>{
		
		private final Map<Class<? extends Command>,
			CommandHandler<? extends Command, ? extends DomainEvent, ID>> handlers = new HashMap<>();
		
		public <A extends Command, B extends DomainEvent> AggregateRootBehaviorBuilder<?> setCommandHandlers(Class<A> commandClass, CommandHandler<A,B,ID> handler) {
			handlers.put(commandClass, handler);
			return this;
		}
		
		public AggregateRootBehavior<ID> build() {
			return new AggregateRootBehavior<ID>(handlers);
		}
	}
}
