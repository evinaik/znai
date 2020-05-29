/*
 * Copyright 2020 znai maintainers
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

package org.testingisdocumenting.znai.enterprise.storage;

import org.testingisdocumenting.znai.console.ConsoleOutputs;
import org.testingisdocumenting.znai.console.ansi.Color;
import org.testingisdocumenting.znai.enterprise.landing.LandingDocEntriesProvider;
import org.testingisdocumenting.znai.enterprise.landing.LandingDocEntry;
import org.testingisdocumenting.znai.server.docpreparation.DocumentationPreparationProgress;
import org.testingisdocumenting.znai.structure.DocMeta;
import org.testingisdocumenting.znai.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testingisdocumenting.znai.fs.FsUtils.*;

public class FileBasedDocumentationStorage implements DocumentationStorage, LandingDocEntriesProvider {
    public static final String META_FILE_NAME = "meta.json";
    private final Path storageRoot;
    private final Path docsRoot;
    private final Map<String, DocMeta> docMetaByDocId;

    public FileBasedDocumentationStorage(Path storageRoot, Path docsRootPath) {
        this.storageRoot = storageRoot;
        this.docsRoot = docsRootPath;
        this.docMetaByDocId = enumerateDocMetas();
    }

    @Override
    public boolean contains(String docId) {
        Path src = storageRoot.resolve(docId).resolve("");
        return Files.exists(src);
    }

    @Override
    synchronized public void store(String docId, String version, Path generatedDocumentation) {
        Path dest = storageRoot.resolve(docId).resolve(version);
        deleteDirectory(dest);
        copyDirectory(generatedDocumentation, dest);

        DocumentationFileBasedTimestamp.store(dest);

        docMetaByDocId.put(docId, new DocMeta(FileUtils.fileTextContent(generatedDocumentation.resolve(META_FILE_NAME))));

        ConsoleOutputs.out("stored ", Color.WHITE, docId, Color.BLUE, " as ", Color.PURPLE, dest);
    }

    @Override
    synchronized public void prepare(String docId, String version,
                                     DocumentationPreparationProgress progress) {
        progress.reportProgress("Checking documentation", Collections.emptyMap(), 10);
        Path src = storageRoot.resolve(docId).resolve(version);

        Path tempDestRoot = creteTempDir();
        Path tempDest = tempDestRoot.resolve(docId).resolve(version);
        Path finalDest = docsRoot.resolve(docId).resolve(version);

        deleteDirectory(tempDest);
        copyDirectory(src, tempDest);
        progress.reportProgress("Moved documentation to a temporary location", Collections.emptyMap(), 40);

        progress.reportProgress("Moving documentation to a permanent location", Collections.emptyMap(), 80);
        deleteDirectory(finalDest);

        moveDirectory(tempDest, finalDest);
        deleteDirectory(tempDestRoot);

        progress.reportProgress("Moved documentation to a permanent location", Collections.emptyMap(), 100);
    }

    private Path creteTempDir() {
        try {
            return Files.createTempDirectory(docsRoot, "temp");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long lastUpdateTime(String docId, String version) {
        return DocumentationFileBasedTimestamp.read(storageRoot.resolve(docId).resolve(version));
    }

    @Override
    public Stream<LandingDocEntry> provide() {
        return docMetaByDocId.entrySet().stream()
                .map(entry -> new LandingDocEntry(
                        entry.getKey(),
                        entry.getValue().getTitle(),
                        "",
                        entry.getValue().getCategory(),
                        entry.getValue().getDescription()));
    }

    synchronized private Map<String, DocMeta> enumerateDocMetas() {
        if (storageRoot == null || !Files.exists(storageRoot)) {
            return new HashMap<>();
        }

        try {
            return Files.list(storageRoot)
                    .filter(file -> Files.isDirectory(file))
                    .filter(file -> Files.exists(file.resolve("meta.json")))
                    .map(file -> file.resolve("meta.json"))
                    .collect(Collectors.toMap(
                            fileMeta -> fileMeta.getParent().getFileName().toString(),
                            fileMeta -> new DocMeta(FileUtils.fileTextContent(fileMeta))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
