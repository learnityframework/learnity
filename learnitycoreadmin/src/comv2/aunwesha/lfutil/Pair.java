package comv2.aunwesha.lfutil;

import java.io.Serializable;

public class Pair<T1, T2> implements Serializable
{
    private static final long serialVersionUID = 4271023139171355529L;
    T1 first;
    T2 second;
    
    public Pair() {
        this.first = null;
        this.second = null;
    }
    
    public Pair(final T1 first, final T2 second) {
        this.first = first;
        this.second = second;
    }
    
    public T1 getFirst() {
        return this.first;
    }
    
    public void setFirst(final T1 first) {
        this.first = first;
    }
    
    public T2 getSecond() {
        return this.second;
    }
    
    public void setSecond(final T2 second) {
        this.second = second;
    }
}
