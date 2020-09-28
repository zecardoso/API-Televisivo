package com.apitelevisivo.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UploadFileResponse implements Serializable {

	private static final long serialVersionUID = 4359322923556537957L;
    
    public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}