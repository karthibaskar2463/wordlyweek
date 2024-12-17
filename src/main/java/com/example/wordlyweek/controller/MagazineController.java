/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.web.bind.annotation.*;
 * 
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here 
package com.example.wordlyweek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import com.example.wordlyweek.model.*;
import com.example.wordlyweek.service.*;

@RestController
public class MagazineController {
    @Autowired
    public MagazineJpaService magazineJpaService;

    @GetMapping("/magazines")
    public ArrayList<Magazine> getMagazines() {
        return magazineJpaService.getMagazines();
    }

    @GetMapping("/magazine/{magazineId}")
    public Magazine getMagazineById(@PathVariable("magazineId") int magazineId) {
        return magazineJpaService.getMagazineById(magazineId);
    }

    @PostMapping("/magazines")
    public Magazine addMagazine(@RequestBody Magazine magazine) {
        return magazineJpaService.addMagazine(magazine);
    }

    @PutMapping("/magazine/{magazineId}")
    public Magazine updateMagazine(@PathVariable("magazineId") int magazineId, @RequestBody Magazine magazine) {
        return magazineJpaService.updateMagazine(magazineId, magazine);
    }

    @DeleteMapping("/magazine/{magazineId}")
    public void deleteMagazine(@PathVariable("magazineId") int magazineId) {
        magazineJpaService.deleteMagazine(magazineId);
    }

    @GetMapping("/magazine/{magazineId}/writers")
    public List<Writer> getWritersByMagazineId(@PathVariable("magazineId") int magazineId) {
        return magazineJpaService.getWritersByMagazineId(magazineId);
    }

}
