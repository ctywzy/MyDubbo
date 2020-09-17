package rpc.common.util;

import domain.entry.ServiceEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class  CollectionUtil {

    /**
     * 判断集合是否不为空
     * @param collection
     * @return
     */
    public static Boolean isNotEmpty(Collection<?> collection){
        return collection != null && collection.size() != 0;
    }

    /**
     * 判断集合是否为空
     * @param collection
     * @return
     */
    public static Boolean isEmpty(Collection<?> collection){
        return collection == null || collection.size() == 0;
    }

    /**
     * 将集合转换为list对象
     * @param collection
     * @param <T>
     * @return
     */
    public static <T> List<T> newArrayList(Collection<T> collection) {
        if(isEmpty(collection)){
            return new ArrayList();
        }

        List<T> list = new ArrayList(collection.size());

        list.addAll(collection);
        return list;
    }

}
