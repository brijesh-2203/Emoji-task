package com.example.emojiproject.controller;

import com.example.emojiproject.model.Emoji;
import com.example.emojiproject.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EmojiController {

    @Autowired
    private QueryService service;


    @GetMapping("/{indexName}/{search}")
    public List<String> getEmoji(@PathVariable String indexName,@PathVariable String search)
    {
        List<Emoji> emojis = service.getemo(search,indexName);
        List<String> emoji = new ArrayList<>();

        if(emojis.isEmpty())
        {
            emoji.add("Emoji not found!!");
        }
        else {
            for (Emoji emoticon : emojis) {
                emoji.add(emoticon.getEmoji());
            }
        }
        return emoji;
    }

    @GetMapping("/{indexName}")
    public List<String> getEmojis(@PathVariable String indexName)
    {
        List<Emoji> emojis = service.getemojis(indexName);
        List<String> emoji = new ArrayList<>();

        for (Emoji emoticon : emojis) {
            emoji.add(emoticon.getEmoji());
        }
        return emoji;
    }

    @PostMapping("/add/{indexName}")
    public String addEmojis(@PathVariable String indexName) throws IOException {

       if(service.getindices(indexName))
       {
           return "Index Name already exist";
       }
        service.writeAccounts(indexName);
        return "added";
    }
    @DeleteMapping("/delete/{indexName}")
    public String deleteIndex(@PathVariable String indexName) throws IOException {
        service.deleteIndex(indexName);
        return "Deleted Successfully";
    }
}
