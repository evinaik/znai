/*
 * Copyright 2019 TWO SIGMA OPEN SOURCE, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.twosigma.znai.diagrams;

import com.twosigma.znai.diagrams.graphviz.Graphviz;
import com.twosigma.znai.diagrams.graphviz.GraphvizDiagram;
import com.twosigma.znai.diagrams.graphviz.gen.DiagramNode;
import com.twosigma.znai.diagrams.graphviz.gen.GraphvizFromJsonGen;
import com.twosigma.znai.diagrams.graphviz.gen.GraphvizGenConfig;
import com.twosigma.znai.diagrams.graphviz.gen.GraphvizGenResult;
import com.twosigma.znai.core.AuxiliaryFile;
import com.twosigma.znai.core.ComponentsRegistry;
import com.twosigma.znai.core.ResourcesResolver;
import com.twosigma.znai.extensions.PluginParams;
import com.twosigma.znai.extensions.PluginResult;
import com.twosigma.znai.extensions.include.IncludePlugin;
import com.twosigma.znai.parser.ParserHandler;
import com.twosigma.znai.structure.DocStructure;
import com.twosigma.znai.structure.DocUrl;
import com.twosigma.znai.utils.JsonUtils;
import com.twosigma.znai.diagrams.graphviz.GraphvizEngine;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class FlowChartIncludePlugin implements IncludePlugin {
    private static final AtomicInteger diagramCount = new AtomicInteger();

    private Path filePath;
    private List<Path> nodeLibPath;
    private DocStructure docStructure;
    private Path markupPath;

    @Override
    public String id() {
        return "flow-chart";
    }

    @Override
    public IncludePlugin create() {
        return new FlowChartIncludePlugin();
    }

    @Override
    public PluginResult process(ComponentsRegistry componentsRegistry,
                                ParserHandler parserHandler,
                                Path markupPath,
                                PluginParams pluginParams) {
        this.markupPath = markupPath;
        filePath = componentsRegistry.resourceResolver().fullPath(pluginParams.getFreeParam());
        docStructure = componentsRegistry.docStructure();

        DiagramsGlobalAssetsRegistration.register(componentsRegistry.globalAssetsRegistry());

        String graphJson = componentsRegistry.resourceResolver().textContent(filePath);

        GraphvizGenConfig genConfig = new GraphvizGenConfig();
        genConfig.setVertical(pluginParams.getOpts().get("vertical", false));

        Map<String, ?> graph = JsonUtils.deserializeAsMap(graphJson);
        GraphvizGenResult genResult = new GraphvizFromJsonGen(graph,
                loadNodeLibraries(componentsRegistry.resourceResolver(), pluginParams),
                genConfig).generate();

        GraphvizDiagram diagram = Graphviz.graphvizEngine.diagramFromGv(
                pluginParams.getOpts().get("layout", GraphvizEngine.DOT_LAYOUT),
                "dag" + diagramCount.incrementAndGet(),
                genResult.getGraphViz());

        Map<String, Object> props = new LinkedHashMap<>();
        props.put("diagram", diagram.toMap());
        props.put("idsToHighlight", pluginParams.getOpts().getList("highlight"));
        props.put("wide", pluginParams.getOpts().get("wide", false));

        Map<String, String> urls = extractUrls(genResult.getUsedNodes());
        validateUrls(markupPath, urls);

        props.put("urls", convertUrls(urls));

        return PluginResult.docElement("GraphVizDiagram", props);
    }

    private Map<String, String> extractUrls(Collection<DiagramNode> nodes) {
        return nodes.stream()
                .filter(DiagramNode::hasUrl)
                .collect(Collectors.toMap(DiagramNode::getId, DiagramNode::getUrl));
    }

    private void validateUrls(Path markupPath, Map<String, String> urls) {
        urls.values().forEach(url -> docStructure.validateUrl(markupPath, "inside :include-flow-chart:", new DocUrl(url)));
    }

    private Map<String, String> convertUrls(Map<String, String> urls) {
        return urls.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, this::buildUrl));
    }

    private List<List<?>> loadNodeLibraries(ResourcesResolver resourcesResolver,
                                            PluginParams pluginParams) {
        nodeLibPath = pluginParams.getOpts().getList("nodeLibPath").stream()
                .map(filePath -> resourcesResolver.fullPath(filePath.toString()))
                .collect(Collectors.toList());

        return nodeLibPath.stream()
                .map(resourcesResolver::textContent)
                .map(JsonUtils::deserializeAsList)
                .collect(toList());

    }

    private String buildUrl(Map.Entry<String, String> entry) {
        return docStructure.createUrl(markupPath, new DocUrl(entry.getValue()));
    }

    @Override
    public Stream<AuxiliaryFile> auxiliaryFiles(ComponentsRegistry componentsRegistry) {
        return Stream.concat(
                Stream.of(AuxiliaryFile.builtTime(filePath)),
                nodeLibPath.stream().map(AuxiliaryFile::builtTime));
    }
}