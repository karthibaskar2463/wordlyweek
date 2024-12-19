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
public class WriterJpaService implements WriterRepository {
    @Autowired
    private WriterJpaRepository writerJpaRepository;

    @Autowired
    private MagazineJpaRepository magazineJpaRepository;

    @Override
    public ArrayList<Writer> getWriters() {
        List<Writer> writerList = writerJpaRepository.findAll();
        ArrayList<Writer> writers = new ArrayList<>(writerList);
        return writers;
    }

    @Override
    public Writer getWriterById(int writerId) {
        try {
            Writer writer = writerJpaRepository.findById(writerId).get();
            return writer;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Writer addWriter(Writer writer) {
        List<Integer> magazineIds = new ArrayList<>();

        for (Magazine magazine : writer.getMagazines()) {
            magazineIds.add(magazine.getMagazineId());
        }

        List<Magazine> magazines = magazineJpaRepository.findAllById(magazineIds);

        if (magazineIds.size() != magazines.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        writer.setMagazines(magazines);

        return writerJpaRepository.save(writer);
    }

    @Override
    public Writer updateWriter(int writerId, Writer writer) {
        try {
            Writer existingWriter = writerJpaRepository.findById(writerId).get();

            if (writer.getWriterName() != null) {
                existingWriter.setWriterName(writer.getWriterName());
            }

            if (writer.getBio() != null) {
                existingWriter.setBio(writer.getBio());
            }

            if (writer.getMagazines() != null) {
                List<Integer> magazineIds = new ArrayList<>();

                for (Magazine magazine : writer.getMagazines()) {
                    magazineIds.add(magazine.getMagazineId());
                }

                List<Magazine> magazines = magazineJpaRepository.findAllById(magazineIds);

                if (magazineIds.size() != magazines.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

                }
                existingWriter.setMagazines(magazines);
            }
            return writerJpaRepository.save(existingWriter);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteWriter(int writerId) {
        try {

            writerJpaRepository.deleteById(writerId);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Magazine> getMagazinesByWriterId(int writerId) {
        try {
            Writer writer = writerJpaRepository.findById(writerId).get();
            List<Magazine> magazines = writer.getMagazines();
            return magazines;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}