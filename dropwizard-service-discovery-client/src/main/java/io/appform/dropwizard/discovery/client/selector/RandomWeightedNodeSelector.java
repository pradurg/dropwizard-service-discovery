package io.appform.dropwizard.discovery.client.selector;

import com.flipkart.ranger.model.ServiceNode;
import com.flipkart.ranger.model.ServiceNodeSelector;
import io.durg.tsaheylu.discovery.WeightedRandomFunction;
import io.durg.tsaheylu.model.NodeData;

import java.util.List;
import java.util.stream.Collectors;

public class RandomWeightedNodeSelector implements ServiceNodeSelector<NodeData> {

    private WeightedRandomFunction weightedRandomFunction = new WeightedRandomFunction();

    @Override
    public ServiceNode<NodeData> select(List<ServiceNode<NodeData>> list) {
        return list.get(weightedRandomFunction.apply(list.stream()
                .map(ServiceNode::getNodeData)
                .collect(Collectors.toList())));
    }
}
