/*
 * You can use the following import statements
 *
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.http.HttpStatus;
 * import org.springframework.stereotype.Service;
 * import org.springframework.web.server.ResponseStatusException;
 * 
 * import java.util.*;
 *
 */

// Write your code here 
package com.example.wordlyweek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import com.example.wordlyweek.model.*;
import com.example.wordlyweek.repository.*;
import java.util.NoSuchElementException;

@Service
public class MagazineJpaService implements MagazineRepository {
    @Autowired
    private MagazineJpaRepository magazineJpaRepository;

    @Autowired
    private WriterJpaRepository writerJpaRepository;

    @Override
    public ArrayList<Magazine> getMagazines() {
        List<Magazine> magazineList = magazineJpaRepository.findAll();
        ArrayList<Magazine> magazines = new ArrayList<>(magazineList);
        return magazines;
    }

    @Override
    public Magazine getMagazineById(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            return magazine;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Magazine addMagazine(Magazine magazine) {
        List<Integer> writerIds = new ArrayList<>();

        for (Writer writer : magazine.getWriters()) {
            writerIds.add(writer.getWriterId());
        }

        List<Writer> writers = writerJpaRepository.findAllById(writerIds);
        magazine.setWriters(writers);

        for (Writer writer : writers) {
            writer.getMagazines().add(magazine);
        }

        writerJpaRepository.saveAll(writers);
        return magazineJpaRepository.save(magazine);
    }

    @Override
    public Magazine updateMagazine(int magazineId, Magazine magazine) {
        try {
            Magazine existingMagazine = magazineJpaRepository.findById(magazineId).get();

            if (magazine.getMagazineName() != null) {
                existingMagazine.setMagazineName(magazine.getMagazineName());
            }

            if (magazine.getPublicationDate() != null) {
                existingMagazine.setPublicationDate(magazine.getPublicationDate());
            }

            if (magazine.getWriters() != null) {
                List<Writer> writers = existingMagazine.getWriters();

                for (Writer writer : writers) {
                    writer.getMagazines().remove(existingMagazine);
                }
                writerJpaRepository.saveAll(writers);

                List<Integer> writerIds = new ArrayList<>();

                for (Writer writer : magazine.getWriters()) {
                    writerIds.add(writer.getWriterId());
                }

                List<Writer> newWriters = writerJpaRepository.findAllById(writerIds);

                for (Writer writer : newWriters) {
                    writer.getMagazines().add(existingMagazine);
                }
                writerJpaRepository.saveAll(newWriters);
                existingMagazine.setWriters(newWriters);
            }
            return magazineJpaRepository.save(existingMagazine);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteMagazine(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            List<Writer> writers = magazine.getWriters();

            for (Writer writer : writers) {
                writer.getMagazines().remove(magazine);
            }
            writerJpaRepository.saveAll(writers);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Writer> getWritersByMagazineId(int magazineId) {
        try {
            Magazine magazine = magazineJpaRepository.findById(magazineId).get();
            return magazine.getWriters();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}