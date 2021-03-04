package io.appform.dropwizard.discovery.client.selector;

import com.flipkart.ranger.model.ServiceNode;
import io.appform.dropwizard.discovery.common.ShardInfo;
import io.durg.tsaheylu.discovery.WeightedRandomFunction;
import io.durg.tsaheylu.metered.ErrorRegistry;
import io.durg.tsaheylu.metered.ServiceEndpoint;
import io.durg.tsaheylu.metered.ServiceEndpointWithScore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TsaheyluHealthSelector extends RandomWeightedNodeSelector {
    private final ErrorRegistry errorRegistry;

    public TsaheyluHealthSelector(ErrorRegistry errorRegistry) {
        this.errorRegistry = errorRegistry;
    }

    @Override
    public ServiceNode<ShardInfo> select(List<ServiceNode<ShardInfo>> list) {
        if (Objects.isNull(errorRegistry)) {
            return super.select(list);
        }

        Map<ServiceEndpoint, ServiceNode<ShardInfo>> map =
                list.stream()
                        .collect(Collectors.toMap(
                                shardServiceNode -> new ServiceEndpoint(shardServiceNode.getPort(),
                                        shardServiceNode.getHost(),
                                        "http"),
                                shardInfoServiceNode -> shardInfoServiceNode)
                        );
        final List<ServiceEndpoint> endpoints = new ArrayList<>(map.keySet());
        List<ServiceEndpointWithScore> filteredEndpoints = errorRegistry.filter(endpoints);
        return super.select(filteredEndpoints.stream().map(e -> map.get(e.getServiceEndpoint())).collect(Collectors.toList()));
    }

}