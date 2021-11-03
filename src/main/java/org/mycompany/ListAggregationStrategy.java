package org.mycompany;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AbstractListAggregationStrategy;
import org.springframework.stereotype.Component;

@Component
public class ListAggregationStrategy extends AbstractListAggregationStrategy<Object> {

	@Override
	public Object getValue(Exchange arg0) {
		// TODO Auto-generated method stub
		return arg0.getIn().getBody(Map.class);
	}

}
