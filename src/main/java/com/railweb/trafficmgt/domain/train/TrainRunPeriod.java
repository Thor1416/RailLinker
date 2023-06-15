package com.railweb.trafficmgt.domain.train;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.railweb.shared.domain.base.ValueObject;

import lombok.Data;

@Embeddable
@Data
public class TrainRunPeriod implements ValueObject<TrainRunPeriod> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7697309587697326130L;
	@Column(name="start_date")
	private LocalDate startDate;
	@Column(name="end_date")
	private LocalDate endDate;
	
	
	@Override
	public boolean sameValueAs(TrainRunPeriod other) {
		return this.hashCode() == other.hashCode(); 
	}

	public int hashCode() {
		return Objects.hash(startDate, endDate);
	}
}
