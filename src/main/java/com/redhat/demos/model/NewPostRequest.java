package com.redhat.demos.model;

import java.util.List;

import lombok.Data;

@Data
public class NewPostRequest {
    private String postTitle;
    private List<String> tagNames;
}
