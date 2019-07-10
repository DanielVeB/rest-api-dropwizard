package com.comarch.danielkurosz.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


class SortingConverter {

    static HashMap<String, String> getSorts(String sortBy) throws IllegalArgumentException {
        HashMap<String, String> sorts = new HashMap<>();

        if (sortBy == null) return null;

        List<String> sortlist = new LinkedList<>(Arrays.asList(sortBy.split(",")));
        for (String sort : sortlist) {
            // s[0] - name of field , s[1]- asc or desc
            String[] s = sort.split(":");
            if (!s[1].equals("asc") && !s[1].equals("desc")) {
                throw new IllegalArgumentException(s[1]);
            }
            sorts.put(s[0], s[1]);
        }
        return sorts;
    }
}
