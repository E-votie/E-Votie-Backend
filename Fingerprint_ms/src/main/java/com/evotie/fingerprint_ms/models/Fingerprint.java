package com.evotie.fingerprint_ms.models;

public class Fingerprint {
    private String application_id;
    private String templateData;

    public Fingerprint(String application_id, String templateData) {
        this.application_id = application_id;
        this.templateData = templateData;
    }

    public String getApplication_id() {
        return application_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    public String getTemplateData() {
        return templateData;
    }

    public void setTemplateData(String templateData) {
        this.templateData = templateData;
    }
}
