package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.SortListException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class SortingConverter {

    public HashMap<String, String> getSorts(String sortList) throws AppException {
        HashMap<String, String> sorts = new HashMap<>();
        if (sortList == null) return null;
        // had to add ',' at the end of sortList to good working regex
        sortList += ",";
        if (!sortList.matches("(((firstName)|(lastName)|(email)):((asc)|(desc)),)*")) {
            throw new SortListException();
        }

        List<String> sortlist = new LinkedList<>(Arrays.asList(sortList.split(",")));
        for (String sort : sortlist) {
            // s[0] - name of field , s[1]- asc or desc
            String[] s = sort.split(":");
            sorts.put(s[0], s[1]);
        }
        return sorts;
    }
}
