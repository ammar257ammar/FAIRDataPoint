/**
 * The MIT License
 * Copyright © 2017 DTL
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package nl.dtls.fairdatapoint.service.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import lombok.extern.log4j.Log4j2;
import nl.dtls.fairdatapoint.database.mongo.repository.ResourceDefinitionRepository;
import nl.dtls.fairdatapoint.entity.resource.ResourceDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class OpenApiService {

    @Autowired
    private OpenAPI openAPI;

    @Autowired
    private OpenApiGenerator openApiGenerator;

    @Autowired
    private ResourceDefinitionRepository resourceDefinitionRepository;

    private Paths getGenericPaths() {
        return (Paths) openAPI.getExtensions().get("fdpGenericPaths");
    }

    private boolean isRelatedToResourceDefinition(PathItem pathItem, ResourceDefinition rd) {
        String rdUuid = (String) pathItem.getExtensions().getOrDefault("fdpResourceDefinition", "");
        return rdUuid.equals(rd.getUuid());
    }

    public void removeGenericPaths(ResourceDefinition rd) {
        Paths fdpGenericPaths = getGenericPaths();
        Set<String> toRemove = fdpGenericPaths
                .entrySet()
                .stream()
                .filter(item -> isRelatedToResourceDefinition(item.getValue(), rd))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
        log.info("Removing OpenAPI paths: {}", toRemove);
        openAPI.getPaths().keySet().removeAll(toRemove);
        fdpGenericPaths.keySet().removeAll(toRemove);
    }

    public void removeAllGenericPaths() {
        Paths fdpGenericPaths = getGenericPaths();
        log.info("Removing OpenAPI paths: {}", fdpGenericPaths.keySet());
        openAPI.getPaths().keySet().removeAll(fdpGenericPaths.keySet());
        fdpGenericPaths.clear();
        //openAPI.getPaths().keySet().removeAll(openAPI.getPaths().keySet().stream().filter(p -> p.contains("**")).collect(Collectors.toList()));
    }

    public void updateGenericPaths(ResourceDefinition rd) {
        Paths fdpGenericPaths = getGenericPaths();
        // Cleanup
        removeGenericPaths(rd);
        // Generate
        openApiGenerator.generatePathsForResourceDefinition(fdpGenericPaths, rd);
        // Apply
        log.info("Adding OpenAPI paths: {}", fdpGenericPaths.keySet());
        openAPI.getPaths().putAll(fdpGenericPaths);
    }

    public void updateAllGenericPaths() {
        Paths fdpGenericPaths = getGenericPaths();
        // Cleanup
        removeAllGenericPaths();
        // Re-generate from Resource Definitions
        List<ResourceDefinition> resourceDefinitions = resourceDefinitionRepository.findAll();
        resourceDefinitions.forEach(rd -> openApiGenerator.generatePathsForResourceDefinition(fdpGenericPaths, rd));
        // Apply
        log.info("Adding OpenAPI paths: {}", fdpGenericPaths.keySet());
        openAPI.getPaths().putAll(fdpGenericPaths);
    }

    @PostConstruct
    public void init() {
        log.info("Initializing OpenAPI with generic paths");
        updateAllGenericPaths();
    }
}
