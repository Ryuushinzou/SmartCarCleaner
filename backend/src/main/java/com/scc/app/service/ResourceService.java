package com.scc.app.service;

import com.scc.app.model.Resource;
import com.scc.app.model.User;
import com.scc.app.mysql.repository.ResourceRepository;
import com.scc.app.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    private ConcurrentMap<Long, Resource> idToResource = new ConcurrentHashMap<>();

    @Scheduled(fixedDelay = 10_000)
    private void syncWithDb() {

        ConcurrentMap<Long, Resource> idToResourceTemporary = new ConcurrentHashMap<>();

        resourceRepository.findAll().forEach(resource -> idToResourceTemporary.put(resource.getId(), resource.clone()));

        idToResource = idToResourceTemporary;
    }

    public Resource saveResource(Resource resource) {

        return resourceRepository.save(resource);
    }

    public Resource getResourceById(Long id) {
        return idToResource.get(id);
    }

    public Collection<Resource> getAllResources() {
        return idToResource.values();
    }


    public Resource update(Resource resourceUpdated) {

        Resource resource = idToResource.get(resourceUpdated.getId());
        if (resource != null) {

            if (resourceUpdated.getName() != null) {
                resource.setName(resourceUpdated.getName());
            }
            if (resourceUpdated.getDescription() != null) {
                resource.setDescription(resourceUpdated.getDescription());
            }
            if (resourceUpdated.getPricePerUnit() != null) {
                resource.setPricePerUnit(resourceUpdated.getPricePerUnit());
            }
        }
        return null;
    }
}
