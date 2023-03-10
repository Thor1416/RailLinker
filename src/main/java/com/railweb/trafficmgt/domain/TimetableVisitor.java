package com.railweb.trafficmgt.domain;

import com.railweb.shared.domain.util.Visitor;
import com.railweb.trafficmgt.domain.network.Line;
import com.railweb.trafficmgt.domain.network.LineTrack;
import com.railweb.trafficmgt.domain.network.Network;
import com.railweb.trafficmgt.domain.network.Node;
import com.railweb.trafficmgt.domain.network.NodeTrack;
import com.railweb.trafficmgt.domain.train.Train;
import com.railweb.trafficmgt.domain.train.TrainRoute;
import com.railweb.trafficmgt.domain.train.TrainType;
import com.railweb.trafficmgt.domain.train.TrainsCycle;

public interface TimetableVisitor extends Visitor {

	public void visit(Timetable timetable);
	public void visit(Network net);
	public void visit(Train train);
	public void visit(Line line);
	public void visit(NodeTrack nodeTrack);
	public void visit(LineTrack lineTrack);
	public void visit(TrainType trainType);
	public void visit(TrainRoute trainRoute);
	public void visit(Node node);
	public void visit(TrainsCycle trainsCycle);
	
}
