package com.example.emojiproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.Map;


@Document(indexName = "emojis")
public class Emoji {

    @JsonProperty
    private String emoji;
    @JsonProperty
    private String description;
    @JsonProperty
    private String category;
    @JsonProperty
    private List<String> aliases;
    @JsonProperty
    private List<String> tags;
    @JsonProperty
    private String  unicode_version;
    @JsonProperty
    private String ios_version;
    @JsonProperty
    private boolean skin_tones;

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getUnicode_version() {
        return unicode_version;
    }

    public void setUnicode_version(String unicode_version) {
        this.unicode_version = unicode_version;
    }

    public String getIos_version() {
        return ios_version;
    }

    public void setIos_version(String ios_version) {
        this.ios_version = ios_version;
    }

    public boolean isSkin_tones() {
        return skin_tones;
    }

    public void setSkin_tones(boolean skin_tones) {
        this.skin_tones = skin_tones;
    }
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static Map<String, Object> getAsMap(final Emoji account) {
        return OBJECT_MAPPER.convertValue(account, new TypeReference<Map<String, Object>>() {
        });
    }
    //    private String base64Image;
}
