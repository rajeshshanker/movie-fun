package org.superbiz.moviefun.blobstore;

import java.io.InputStream;

public class Blob {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    private InputStream inputStream;
    private String contentType;

   public Blob(String name,InputStream inputStream,String contentType){
        this.name =name;
        this.inputStream = inputStream;
        this.contentType = contentType;

    }


}
