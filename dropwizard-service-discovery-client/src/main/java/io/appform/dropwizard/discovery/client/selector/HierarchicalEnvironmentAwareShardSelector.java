package io.appform.dropwizard.discovery.client.selector;

import com.flipkart.ranger.finder.sharded.MapBasedServiceRegistry;
import com.flipkart.ranger.model.ServiceNode;
import com.flipkart.ranger.model.ShardSelector;
import com.google.common.collect.ListMultimap;
import io.appform.dropwizard.discovery.client.Constants;
import io.durg.tsaheylu.model.NodeData;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class HierarchicalEnvironmentAwareShardSelector implements ShardSelector<NodeData, MapBasedServiceRegistry<NodeData>> {

    @Override
    public List<ServiceNode<NodeData>> nodes(final NodeData criteria,
                                              final MapBasedServiceRegistry<NodeData> serviceRegistry) {
        val serviceNodes = serviceRegistry.nodes();
        val serviceName = serviceRegistry.getService().getServiceName();
        val environment = criteria.getEnvironment();

        if (Objects.equals(environment, Constants.ALL_ENV)) {
            return allNodes(serviceNodes);
        }
        for (NodeData shardInfo : criteria) {
            val currentEnvNodes = serviceNodes.get(shardInfo);
            if (!currentEnvNodes.isEmpty()) {
                log.debug("Effective environment for discovery of {} is {}", serviceName, environment);
                return currentEnvNodes;
            }
        }
        return Collections.emptyList();
    }

    private List<ServiceNode<NodeData>> allNodes(ListMultimap<NodeData, ServiceNode<NodeData>> serviceNodes) {
        return serviceNodes.asMap()
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
