package com.comarch.danielkurosz.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


class SortingConverter {

    static HashMap<String, String> getSorts(String sortList) throws IllegalArgumentException {
        HashMap<String, String> sorts = new HashMap<>();

        // must to add ',' at the end of sortList to good working regex
        sortList+=",";
        if(!sortList.matches("(((firstName)|(lastName)|(email)):((asc)|(desc)),)*")){
            throw new IllegalArgumentException();
        }

        List<String> sortlist = new LinkedList<>(Arrays.asList(sortList.split(",")));
        System.out.println(sortlist.size());
        for (String sort : sortlist) {
            // s[0] - name of field , s[1]- asc or desc
            String[] s = sort.split(":");
            sorts.put(s[0], s[1]);
        }
        return sorts;
    }
}
