package com.railweb.shared.domain.items;

import java.util.Objects;

import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.DomainObjectId;
import com.railweb.shared.domain.base.ValueObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class ItemSetPair<T extends DomainObjectId<?>,U extends DomainObjectId<?>>
					implements ValueObject<ItemSetPair<T,U>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2747378926526997139L;
	private T first;
	private U second;
	
	@Override
	public boolean sameValueAs(ItemSetPair<T,U> other) {
		return Objects.equals(this.first.getId(), other.first.getId()) && 
				Objects.equals(this.second.getId(),other.second.getId());
	}
	
}
