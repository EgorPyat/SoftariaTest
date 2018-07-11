package ru.nsu.pyataev.softariatest.mymaputils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapUtils {
    public static <U,H> MapsKeysDifference keysDiff(Map<U,H> left, Map<U,H> right){
        Set<U> keysInLeft = new HashSet<U>(left.keySet());
        Set<U> keysInRight = new HashSet<U>(right.keySet());

        keysInLeft.removeAll(keysInRight);
        keysInRight.removeAll(left.keySet());

        Set<U> both = new HashSet<U>(left.keySet());
        both.retainAll(right.keySet());

        for(Iterator<U> iter = both.iterator(); iter.hasNext();){
            U key = iter.next();
            if(left.get(key).equals(right.get(key))){
                iter.remove();
            }
        }

        return new MapsKeysDifference<U>(keysInLeft, keysInRight, both);
    }

    public static class MapsKeysDifference<U>{
        private Set<U> entriesInLeft;
        private Set<U> entriesInRight;
        private Set<U> enTriesInBothDiffValues;

        MapsKeysDifference(Set<U> left, Set<U> right, Set<U> both){
            entriesInLeft = left;
            entriesInRight = right;
            enTriesInBothDiffValues = both;
        }

        public Set<U> getEntriesInRight() {
            return entriesInRight;
        }

        public Set<U> getEntriesInLeft() {
            return entriesInLeft;
        }

        public Set<U> getEntriesInBothDiffValues() {
            return enTriesInBothDiffValues;
        }
    }
}
