package org.superbiz.moviefun;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superbiz.moviefun.albums.Album;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CsvUtils {

    private final static Logger logger = LoggerFactory.getLogger(CsvUtils.class);

    public static <T> List<T> readFromCsv(ObjectReader objectReader, InputStream inputStream) throws IOException {
        List<T> results = new ArrayList<>();

        MappingIterator<T> iterator = objectReader.readValues(inputStream);

        while (iterator.hasNext()) {
            results.add(iterator.nextValue());
        }
        results.forEach(album -> logger.info("Items in CSV  :" +album.toString()));
        return results;
    }
}
