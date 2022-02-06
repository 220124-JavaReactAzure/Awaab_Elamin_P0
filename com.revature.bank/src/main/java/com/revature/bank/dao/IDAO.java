package com.revature.bank.dao;

import com.revature.bank.util.List;

public interface IDAO<T> {
	T create(T obj);

	List<T> finAll();

	boolean update(T obj);

	boolean delete(String id);
}
