package com.saransh.proxy;

import com.saransh.model.client.ClientSideModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client-proxy" ,url = "${client.entity.get.baseurl}")
public interface ClientDefinitionProxy {
    @GetMapping("/{namespace}")
    public ClientSideModel getConfigByEntity(@PathVariable String namespace);
}
