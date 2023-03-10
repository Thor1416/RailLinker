package com.railweb.trafficmgt.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import com.railweb.shared.anno.ValueObject;

import lombok.Data;

@ValueObject
@Data
@Embeddable
public class TrainNumber {

	@Column(name="train_Number")
	@NotNull(message="Train nuber cannot be null")
	private Long trainNumber;

	public TrainNumber(@NotNull(message = "Train nuber cannot be null") Long trainNumber) {
		this.trainNumber = trainNumber;
	}
}
