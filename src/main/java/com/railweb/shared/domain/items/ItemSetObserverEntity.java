package com.railweb.shared.domain.items;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.railweb.shared.domain.base.AbstractEntity;
import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.base.IdentifiableDomainObject;
import com.railweb.shared.domain.events.DomainEvent;

@Table(name = "itemSet_Observers")
public class ItemSetObserverEntity< T extends IdentifiableDomainObject<ID>,
									ID extends ItemSetObserverId<U,V,ID,E,T> & Serializable,						
									U extends DomainObjectId<?>, 
									V extends DomainObjectId<?>,
									E extends DomainEvent,
									S extends ItemSetObserverEntity<T,ID,U,V,E,S>>
				extends AbstractEntity<ID> 
				implements ItemObserver<T,ID,U,V,E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2971338523153787354L;


	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "itemSetObserver_itemSetMapping", 
		joinColumns = @JoinColumn(name = "itemSetObserver_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "itemSet_id", referencedColumnName = "id"))
	private Set<ItemWithIdSetImpl<U, V, T>> itemSet;

	public ItemSetObserverEntity() {
	} // For use by JPA Only

	public ItemSetObserverEntity(ItemSetObserverId<U,V,ID,E,T> id,
			Set<ItemWithIdSetImpl<U,V,T>> itemset) {
		super(id);
	}
	
	@Override
	public ID getObserverId() {
		return id();
	}

	@Override
	public void notifyObservers(T item, DomainEvent event) {
		// TODO Auto-generated method stub
		
	}

	

}
