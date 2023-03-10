package com.railweb.shared.infra.eventlog;

import com.railweb.shared.domain.base.ValueObject;

import lombok.Getter;


@Getter
public class DomainEventLogId implements ValueObject<DomainEventLogId> {

	private static final long serialVersionUID = 8815027578759766236L;
	private final long low;
    private final long high;

    public DomainEventLogId(long low, long high) {
        if (low > high) {
            throw new IllegalArgumentException("low cannot be higher than high");
        }
        this.low = low;
        this.high = high;
    }

	@Override
	public boolean sameValueAs(DomainEventLogId other) {
		return (this.low == other.low) && (this.high ==other.high);
	}

	public boolean isFirst() {
		return low == 1;
	}

}
