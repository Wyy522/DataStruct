package discipline;

import java.util.*;

public interface t {
    public static <T> Collection<T> synchronizedCollection (Collection<T> c)                                                {};
    public static <T> Set<T> synchronizedSet (Set<T> s)                                                             {};
    public static <T> SortedSet<T> synchronizedSortedset (SortedSet<T> s)                                   {};
    public static <T> List<T> synchronizedList (List<T> list)                                                       {  };
    public static <K,V> Map<K,V> synchronizedMap (Map<K,V> m)                                           {};
    public static <K,V> SortedMap<K,V> synchronizedSortedMap(SortedMap<K,V> m)                                          {};
}
