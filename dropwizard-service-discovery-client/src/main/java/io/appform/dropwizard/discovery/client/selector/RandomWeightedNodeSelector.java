package io.appform.dropwizard.discovery.client.selector;

import com.flipkart.ranger.finder.RandomServiceNodeSelector;
import com.flipkart.ranger.model.ServiceNode;
import io.durg.tsaheylu.discovery.WeightedRandomFunction;
import io.durg.tsaheylu.model.NodeData;

import java.util.List;
import java.util.stream.Collectors;

public class RandomWeightedNodeSelector extends RandomServiceNodeSelector<NodeData> {

    private WeightedRandomFunction weightedRandomFunction = new WeightedRandomFunction();

    @Override
    public ServiceNode<NodeData> select(List<ServiceNode<NodeData>> list) {
        int index = weightedRandomFunction.apply(list.stream()
                .map(ServiceNode::getNodeData)
                .collect(Collectors.toList()));

        if(index == -1) {
            return super.select(list);
        }

        return list.get(index);
    }
}
