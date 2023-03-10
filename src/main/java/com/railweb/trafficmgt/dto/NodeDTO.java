package com.railweb.trafficmgt.dto;

import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.measure.Quantity;
import javax.measure.quantity.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.railweb.shared.web.BaseDTO;
import com.railweb.trafficmgt.domain.network.Node;
import com.railweb.trafficmgt.domain.network.NodeTrack;

import lombok.Data;

@Data
public class NodeDTO implements BaseDTO {

	@JsonProperty(required=true)
	@NotEmpty
	@NotBlank
	private String abbr;
	
	private Set<UUID> netId;
	
	private String name;
	
	private List<Pair<UUID,String>> tracks;
	
	private ZoneId defaultTimezone;
	
	private Quantity<Length> length; 
	
	public static NodeDTO fromModel(Node node) {
		NodeDTO dto = new NodeDTO();
		dto.setAbbr(node.getId().getAbbr().toString());
		dto.setNetId(node.getNets().stream().map(n->n.getId()).collect(Collectors.toSet()));
		dto.setName(node.getName());
		dto.setLength(node.getLength());
		dto.setDefaultTimezone(node.getDefaultTimezone());
		dto.setTracks(node.getTracks().stream().map(t->convertTrack(t))
				.collect(Collectors.toList()));
		return dto;
	}
	
	private static Pair<UUID, String> convertTrack(NodeTrack track){
		return Pair.of(track.getId().getId(), track.getTracknumber());
	}
}
