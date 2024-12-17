/*
 * You can use the following import statements
 *
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 */

// Write your code here 
package com.example.wordlyweek.repository;

import java.util.ArrayList;
import java.util.List;
import com.example.wordlyweek.model.Magazine;
import com.example.wordlyweek.model.Writer;

public interface MagazineRepository {
    ArrayList<Magazine> getMagazines();

    Magazine getMagazineById(int MagazineId);

    Magazine addMagazine(Magazine magazine);

    Magazine updateMagazine(int magazineId, Magazine magazine);

    void deleteMagazine(int magazineId);

    List<Writer> getWritersByMagazineId(int magazineId);
}