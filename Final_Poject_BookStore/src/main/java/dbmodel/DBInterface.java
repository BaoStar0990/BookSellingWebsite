package dbmodel;

import java.util.List;

public interface DBInterface<T> {
//    public boolean insert(T t);
//    public boolean update(T t);
//    public boolean delete(T t);
    public List<T> selectAll();
    public T selectByID(Object id);
}
