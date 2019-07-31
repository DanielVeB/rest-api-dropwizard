package com.comarch.danielkurosz.clients;

public class Tag {

    private String tag_id;
    private String tag_value;

    public Tag() {
    }

    public Tag(String tag_id, String tag_value) {
        this.tag_id = tag_id;
        this.tag_value = tag_value;
    }

    public String getTagId() {
        return tag_id;
    }

    public void setTagId(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTagValue() {
        return tag_value;
    }

    public void setTagValue(String tag_value) {
        this.tag_value = tag_value;
    }
}
