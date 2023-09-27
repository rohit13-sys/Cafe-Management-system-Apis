package com.CMS.com.CMS.service;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public class GenericService<T> implements IGenericService<T> {

    // The DAO class will also need to be generic,
    // so that it can use the right class types
    private final CrudRepository<T, String> dao;

    public GenericService(CrudRepository<T, String> repository) {
        this.dao = repository;
    }

    @Override
    public List<T> findAll() {
        return (List<T>) dao.findAll();
    }

    @Override
    public Optional<T> findById(String id) {
        return dao.findById(id);
    }

    @Override
    public T save(T entity) {
        return dao.save(entity);
    }

    @Override
    public void delete(T entity) {
        dao.delete(entity);
    }

    @Override
    public void deleteById(String id) {
        dao.findById(id);
        dao.deleteById(id);
    }

    @Override
    public long count() {
        return 0;
    }

//	@Override
//	public List<T> saveAll(List<T> entities) {
//		// TODO Auto-generated method stub
//		List<T> savedRecords;
// 		try {
// 			savedRecords = (List<T>) dao.saveAll(entities);
//		} catch (DataIntegrityViolationException e) {
//             throw TransactionService.getExceptionToThrowForConstraintViolation(e);
//		} catch (Exception e) {
//			throw new GenericException();
//		}
//		return savedRecords;
//
//	}
	
	@Override
	public void deleteAll(List<T> entities) {
		// TODO Auto-generated method stub
		dao.deleteAll(entities);
	}
}