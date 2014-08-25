package com.paralainer.shoppinglist.service;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by paralainer on 22.08.2014.
 * email: serg.talov@gmail.com
 */
public class MeasureService {
    private static Set<String> measures = new HashSet<String>();

    public static void regMeasure(String measure){
        if (StringUtils.isNotBlank(measure)) {
            measures.add(measure);
        }
    }

    public static Set<String> getMeasures(){
        return measures;
    }
}
