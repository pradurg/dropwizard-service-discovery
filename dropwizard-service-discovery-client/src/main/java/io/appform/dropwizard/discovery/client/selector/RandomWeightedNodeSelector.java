package io.appform.dropwizard.discovery.client.selector;

import com.flipkart.ranger.finder.RandomServiceNodeSelector;
import com.flipkart.ranger.model.ServiceNode;
import com.flipkart.ranger.model.ServiceNodeSelector;
import io.appform.dropwizard.discovery.common.ShardInfo;
import io.durg.tsaheylu.discovery.WeightedRandomFunction;

import java.util.List;
import java.util.stream.Collectors;

public class RandomWeightedNodeSelector extends RandomServiceNodeSelector<ShardInfo> implements ServiceNodeSelector<ShardInfo> {

    private WeightedRandomFunction weightedRandomFunction = new WeightedRandomFunction();

    @Override
    public ServiceNode<ShardInfo> select(List<ServiceNode<ShardInfo>> list) {
        int index = weightedRandomFunction.apply(list.stream()
                .map(ServiceNode::getHealthMetric)
                .collect(Collectors.toList()));

        if (index == -1) {
            return super.select(list);
        }

        return list.get(index);
    }
}
