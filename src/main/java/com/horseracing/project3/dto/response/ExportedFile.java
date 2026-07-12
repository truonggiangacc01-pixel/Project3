package com.horseracing.project3.dto.response;

public record ExportedFile(
        String fileName,
        String contentType,
        byte[] content
) {
}
