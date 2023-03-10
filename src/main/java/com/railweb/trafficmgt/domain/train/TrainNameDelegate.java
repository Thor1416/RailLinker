package com.railweb.trafficmgt.domain.train;

import java.time.Instant;
import java.util.Locale;
import java.util.Map;

import com.railweb.shared.domain.i18n.TranslatedString;
import com.railweb.shared.domain.util.CachedValue;
import com.railweb.trafficmgt.domain.TextTemplate;
import com.railweb.trafficmgt.domain.events.TrainNameChangedEvent;
import com.railweb.trafficmgt.domain.train.Train.NameType;

public class TrainNameDelegate {
	
	private final Train train;
	private final CachedValue<String> cachedName;
	private final CachedValue<String> cachedCompleteName;
	private Map<String, Object> cachedBinding;
	
	public TrainNameDelegate(Train train) {
		this.train = train;
		cachedName = new CachedValue<>();
		cachedCompleteName = new CachedValue<>();
	}

	public String getName() {
		if(!cachedName.isCached()) {
			this.refreshName();
		}
		return cachedName.getValue();
	}
	public String getCompleteName() {
		if(!cachedCompleteName.isCached()) {
			this.refreshCompleteName();
		}
		return cachedCompleteName.getValue();
	}
	public TranslatedString getName(Train.NameType nametype) {
		return new TranslatedStringTrain(nametype);
	}

	public void refreshCachedNames() {
		refreshName();
		refreshCompleteName();
		
	}
	private void refreshName() {
		String oldName = cachedName.getValue();
		String newName = this.getNameImpl(NameType.NORMAL);
		if(cachedName.setValue(newName)) {
			train.fireEvent(new TrainNameChangedEvent(train.getId(), oldName,newName,NameType.NORMAL, Instant.now()));
		}
		
	}

	private void refreshCompleteName() {
		String oldName = cachedName.getValue();
		String newName = this.getNameImpl(NameType.COMPLETE);
		if(cachedName.setValue(newName)) {
			train.fireEvent(new TrainNameChangedEvent(train.getId(), oldName, newName, NameType.COMPLETE, Instant.now()));
		}
		
	}
	
	private String getNameImpl(NameType nameType) {
		TrainClass tClass = train.getTrainClass();
		return tClass != null ? formatTrainName(nameType): train.getId().getTrainNumber().toString();
	}

	private String formatTrainName(NameType nameType) {
		// TODO Auto-generated method stub
		return null;
	}

	private final class TranslatedStringTrain implements TranslatedString{
		
		private NameType nameType;
		
		public TranslatedStringTrain(NameType nameType) {
			this.nameType=nameType;
		}

		@Override
		public String getDefaultString() {
			return nameType == NameType.COMPLETE ? getCompleteName() : getName();
		}

		@Override
		public String translate(Locale locale) {
			TrainClass trainClass = train.getTrainClass();
			if(trainClass==null) {
				return getDefaultString();
			}else {
				TextTemplate template = trainClass.getNameTemplate(nameType);
				Map<String,Object> binding = TextTemplate.getBinding(train,locale);
				return template.evaluate(binding);
			}
		}
	}



}
