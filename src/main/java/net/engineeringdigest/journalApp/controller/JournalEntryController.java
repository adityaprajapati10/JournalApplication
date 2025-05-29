package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.JournalApplication;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JournalEntry> all  = journalEntryService.getAll();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try {
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
         journalEntryService.deleteById(myId);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry oldJournalEntry = journalEntryService.findById(myId).orElse(null);

        if(oldJournalEntry != null){
            oldJournalEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldJournalEntry.getTitle());
            oldJournalEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldJournalEntry.getContent());
            return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
        }

        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }

}
