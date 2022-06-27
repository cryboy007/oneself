package com.znsd.oneself.util;

import com.google.common.collect.Sets;

import java.util.HashSet;

/**
 * @ClassName Demo
 * @Author tao.he
 * @Since 2022/6/8 14:01
 */
public class Demo {

    private static void listDiff() {
        HashSet<Integer> setA = Sets.newHashSet(1, 2, 3, 4, 5);
        HashSet<Integer> setB = Sets.newHashSet(4, 5, 6, 7, 8);

        Sets.SetView<Integer> union = Sets.union(setA, setB);
        System.out.println("union:" + union);

        Sets.SetView<Integer> difference = Sets.difference(setA, setB);
        System.out.println("difference:" + difference);

        Sets.SetView<Integer> difference2 = Sets.difference(union, setA);
        System.out.println("difference2:" + difference2);

        Sets.SetView<Integer> intersection = Sets.intersection(setA, setB);
        System.out.println("intersection:" + intersection);
    }
}
