package dao;

public interface IDAO<T> {
	  public void create(T obj);
	  public T readOneByName(String nom);
	}
