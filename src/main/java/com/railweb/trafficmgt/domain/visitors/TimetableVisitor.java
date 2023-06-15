package com.railweb.trafficmgt.domain.visitors;

import com.railweb.shared.domain.util.Visitor;
import com.railweb.trafficmgt.domain.Timetable;

public interface TimetableVisitor extends Visitor {

	void visit(Timetable timetable);

}
