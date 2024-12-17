/*
 * You can use the following import statements
 *
 * import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 * 
 * import javax.persistence.*;
 * import java.util.List;
 * 
 */

// Write your code here 
package com.example.wordlyweek.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "writer")
public class Writer {
    @Id
    @Column(name = "writerid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int writerId;

    @Column(name = "writername")
    private String writerName;

    @Column(name = "bio")
    private String bio;

    @ManyToMany
    @JoinTable(name = "writer_magazine", joinColumns = @JoinColumn(name = "writerid"), inverseJoinColumns = @JoinColumn(name = "magazineid"))
    @JsonIgnoreProperties("writers")
    private List<Magazine> magazines = new ArrayList<>();

    public Writer() {
    }

    public Writer(int writerId, String writerName, String bio, List<Magazine> magazines) {
        this.writerId = writerId;
        this.writerName = writerName;
        this.bio = bio;
        this.magazines = magazines;
    }

    public void setWriterId(int writerId) {
        this.writerId = writerId;
    }

    public int getWriterId() {
        return writerId;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void setMagazines(List<Magazine> magazines) {
        this.magazines = magazines;
    }

    public List<Magazine> getMagazines() {
        return magazines;
    }

}