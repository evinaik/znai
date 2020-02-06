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

package com.twosigma.znai.groovy.extensions

import com.twosigma.znai.codesnippets.CodeSnippetsProps
import com.twosigma.znai.core.AuxiliaryFile
import com.twosigma.znai.core.ComponentsRegistry
import com.twosigma.znai.extensions.PluginParams
import com.twosigma.znai.extensions.PluginResult
import com.twosigma.znai.extensions.features.PluginFeatureList
import com.twosigma.znai.extensions.file.CodeReferencesFeature
import com.twosigma.znai.extensions.file.SnippetContentProvider
import com.twosigma.znai.extensions.file.SnippetHighlightFeature
import com.twosigma.znai.extensions.include.IncludePlugin
import com.twosigma.znai.groovy.parser.GroovyCode
import com.twosigma.znai.parser.ParserHandler
import com.twosigma.znai.parser.docelement.DocElementType

import java.nio.file.Path
import java.util.stream.Stream

class GroovyIncludePlugin implements IncludePlugin, SnippetContentProvider {
    private String path
    private Path fullPath
    private PluginFeatureList features

    private String content

    @Override
    String id() {
        return "groovy"
    }

    @Override
    IncludePlugin create() {
        return new GroovyIncludePlugin()
    }

    @Override
    PluginResult process(ComponentsRegistry componentsRegistry,
                         ParserHandler parserHandler,
                         Path markupPath,
                         PluginParams pluginParams) {
        features = new PluginFeatureList(
                new SnippetHighlightFeature(componentsRegistry, pluginParams, this),
                new CodeReferencesFeature(componentsRegistry, markupPath, pluginParams)
        )

        path = pluginParams.getFreeParam()
        fullPath = componentsRegistry.resourceResolver().fullPath(path)
        String fileContent = componentsRegistry.resourceResolver().textContent(fullPath)
        String entry = pluginParams.getOpts().get("entry")

        GroovyCode groovyCode = new GroovyCode(componentsRegistry, fullPath, fileContent)

        Boolean bodyOnly = pluginParams.getOpts().has("bodyOnly") ? pluginParams.getOpts().get("bodyOnly") : false

        content = extractContent(groovyCode, entry, bodyOnly)
        Map<String, Object> props = CodeSnippetsProps.create("groovy",
                content)
        props.putAll(pluginParams.getOpts().toMap())
        features.updateProps(props)

        return PluginResult.docElement(DocElementType.SNIPPET, props)
    }

    @Override
    Stream<AuxiliaryFile> auxiliaryFiles(ComponentsRegistry componentsRegistry) {
        return features.combineAuxiliaryFilesWith(Stream.of(AuxiliaryFile.builtTime(fullPath)))
    }

    private static String extractContent(GroovyCode groovyCode, String entry, Boolean bodyOnly) {
        if (entry == null) {
            return groovyCode.getFileContent()
        }

        return groovyCode.hasTypeDetails(entry) ?
                extractTypeContent(groovyCode, entry, bodyOnly):
                extractMethodContent(groovyCode, entry, bodyOnly)
    }

    private static String extractTypeContent(GroovyCode groovyCode, String entry, boolean bodyOnly) {
        def type = groovyCode.findType(entry)

        return bodyOnly ?
                type.bodyOnly :
                type.fullBody
    }

    private static String extractMethodContent(GroovyCode groovyCode, String entry, boolean bodyOnly) {
        def method = groovyCode.findMethod(entry)

        return bodyOnly ?
                method.bodyOnly :
                method.fullBody
    }

    @Override
    String snippetContent() {
        return content
    }

    @Override
    String snippetId() {
        return path
    }
}