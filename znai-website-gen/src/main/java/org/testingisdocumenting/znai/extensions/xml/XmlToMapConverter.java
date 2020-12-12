/*
 * Copyright 2020 znai maintainers
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

package org.testingisdocumenting.znai.extensions.xml;

import com.sun.jmx.remote.internal.ArrayQueue;
import org.testingisdocumenting.znai.utils.XmlUtils;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

public class XmlToMapConverter extends DefaultHandler {
    private final List<Map<String, Object>> result;
    private final Deque<List<Map<String, Object>>> currentStack;
    private final Set<String> missingPaths;
    private final Deque<String> currentPathParts;
    private StringBuilder accumulatedText;

    public static XmlToMapResult convertAndValidatePaths(String xmlContent, Collection<String> paths) {
        XmlToMapConverter converter = new XmlToMapConverter(paths);
        return new XmlToMapResult(converter.convertXml(xmlContent), converter.missingPaths);
    }

    private XmlToMapConverter(Collection<String> paths) {
        this.missingPaths = new HashSet<>(paths);

        currentPathParts = new ArrayDeque<>();
        currentStack = new ArrayDeque<>();
        result = new ArrayList<>();

        currentStack.add(result);
        accumulatedText = new StringBuilder();
    }

    private Map<String, ?> convertXml(String xmlContent) {
        XmlUtils.parseXml(xmlContent, this);
        return result.get(0);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        Map<String, Object> asMap = new LinkedHashMap<>();

        currentPathParts.add(qName);
        String renderedPath = String.join(".", currentPathParts);

        System.out.println(renderedPath);
        List<Map<String, Object>> children = new ArrayList<>();
        asMap.put("tagName", qName);
        asMap.put("attributes", parseAttributes(attributes));
        asMap.put("children", children);

        currentStack.peekLast().add(asMap);
        currentStack.add(children);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        accumulatedText.append(new String(ch, start, length));
    }

    private Map<String, Object> createTextNode(String text) {
        Map<String, Object> textNode = new LinkedHashMap<>();
        textNode.put("tagName", "");
        textNode.put("text", text);

        return textNode;
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (! accumulatedText.toString().trim().isEmpty()) {
            currentStack.peekLast().add(createTextNode(accumulatedText.toString().trim()));
        }

        currentPathParts.removeLast();
        List<Map<String, Object>> top = currentStack.removeLast();
        System.out.println(top);
        accumulatedText = new StringBuilder();
    }

    private List<Map<String, Object>> parseAttributes(Attributes attributes) {
        List<Map<String, Object>> result = new ArrayList<>();

        int len = attributes.getLength();
        for (int i = 0; i < len; i++) {
            String name = attributes.getLocalName(i);
            String value = attributes.getValue(i);

            Map<String, Object> attr = new LinkedHashMap<>();
            attr.put("name", name);
            attr.put("value", "\"" + value + "\"");

            result.add(attr);
        }

        return result;
    }
}
