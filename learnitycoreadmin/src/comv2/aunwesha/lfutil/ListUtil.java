package comv2.aunwesha.lfutil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ListUtil
{
    public static <T> Collection<Collection<T>> splitCollection(final Collection<T> listData, final int splitSize) {
        final List<Collection<T>> splitList = new ArrayList<Collection<T>>();
        if (GenericUtil.hasCollectionData(listData)) {
            if (listData.size() <= splitSize) {
                splitList.add(listData);
            }
            else {
                int _totalSize = listData.size();
                int _start = 0;
                int _end = 0;
                while (_totalSize > 0) {
                    if (_totalSize < splitSize) {
                        splitList.add(listData);
                        _totalSize = 0;
                    }
                    else {
                        _end = _start + splitSize;
                        _totalSize -= splitSize;
                        splitList.add(new ArrayList<T>(listData).subList(_start, _end));
                        _start = _end;
                    }
                }
            }
        }
        return splitList;
    }
    
    
    public static <T> List<T> makeList(final T... args) {
        final List<T> returnList = new ArrayList<T>();
        if (args != null) {
            for (final T data : args) {
                returnList.add(data);
            }
        }
        return returnList;
    }
    
    public static <O, S, T> List<S> map(final O object, final Method method, final Class<S> clazz, final List<T> listData, final List<?> params, final boolean firstParamListData) {
        final List<S> retDataList = new ArrayList<S>();
        for (final T t : listData) {
            final List<Object> paramList = new ArrayList<Object>();
            if (!GenericUtil.isListNullOrEmpty(params)) {
                paramList.addAll(params);
            }
            if (firstParamListData) {
                paramList.add(0, t);
            }
            else {
                paramList.add(t);
            }
            try {
                final S data = (S)method.invoke(object, paramList.toArray());
                retDataList.add(data);
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
            catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
        return retDataList;
    }
    
    public static <T, O> List<T> filter(final List<T> listData, final Method method, final O object) {
        final List<T> retDataList = new ArrayList<T>();
        for (final T t : listData) {
            try {
                final boolean result = (boolean)method.invoke(object, t);
                if (!result) {
                    continue;
                }
                retDataList.add(t);
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
            catch (InvocationTargetException e3) {
                e3.printStackTrace();
            }
        }
        return retDataList;
    }
    
    public static <T> boolean allInList(final List<T> listData, final T value) {
        boolean check = true;
        for (final T t : listData) {
            if (t != null && !t.equals(value)) {
                check = false;
                break;
            }
            if (t == null && value != null) {
                check = false;
                break;
            }
        }
        return check;
    }
    
    public static <T> boolean anyInList(final List<T> listData, final T value) {
        boolean check = false;
        for (final T t : listData) {
            if (t != null && t.equals(value)) {
                check = true;
                break;
            }
            if (t == null && value == null) {
                check = true;
                break;
            }
        }
        return check;
    }
}
