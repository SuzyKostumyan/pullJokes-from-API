/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.cityjokes.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author suzy
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "joke")
public class Joke implements Serializable {

    @SerializedName("categories")
    @Expose
    private String[] categories;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("icon_url")
    @Expose
    private String iconUrl;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("value")
    @Expose
    private String value;

}
