package com.blurryface.analyzer.core.entity.manager;

import com.blurryface.analyzer.core.entity.model.Services;
import com.blurryface.analyzer.core.entity.repo.ServicesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicesManager {

    @Autowired
    private ServicesRepo servicesRepo;

    public List<Services> getDetailsByServiceTagIn(List<String> serviceTags) {
        return servicesRepo.findByServiceTagIn(serviceTags);
    }

    public Services getDetailsByServiceTag(String serviceTag) {
        return servicesRepo.findByServiceTag(serviceTag).orElse(null);
    }

    public Services saveServices(Services services) {
        return servicesRepo.save(services);
    }

    public List<Services> saveAllServices(List<Services> services) {
        return servicesRepo.saveAll(services);
    }
}
