package com.jakubfilipiak.restthymeleaf.commons.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Jakub Filipiak on 10.05.2019.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LocalFile {

    private String name;
    private String creationTime;
    private String lastModified;
    private Long size;
    private String downloadUri;
    private String deleteUri;
    private String fileType;
}
