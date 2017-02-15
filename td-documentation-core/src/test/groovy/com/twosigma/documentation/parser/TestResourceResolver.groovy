package com.twosigma.documentation.parser

import com.twosigma.documentation.extensions.include.IncludeResourcesResolver
import com.twosigma.utils.ResourceUtils

import java.nio.file.Path
import java.nio.file.Paths

/**
 * @author mykola
 */
class TestResourceResolver implements IncludeResourcesResolver {
    @Override
    String textContent(String path) {
        return ResourceUtils.textContent(path)
    }

    @Override
    Path fullPath(String path) {
        return Paths.get(path).toAbsolutePath()
    }
}