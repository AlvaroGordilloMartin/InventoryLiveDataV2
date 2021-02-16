package com.example.inventorylivedata.data.repository;

import com.example.inventorylivedata.data.InventoryDatabase;
import com.example.inventorylivedata.data.dao.DependencyDao;
import com.example.inventorylivedata.data.model.Dependency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DependencyRepository {
    private List<Dependency> list;
    private static DependencyRepository repository;
    private DependencyDao dependencyDao;

    static {
        repository = new DependencyRepository();
    }

    public DependencyRepository() {
        this.list = new ArrayList<>();
        dependencyDao = InventoryDatabase.getDatabase().dependencyDao();
    }



    public static DependencyRepository getInstance() {
        return repository;
    }

    public static boolean Exists(Dependency dependency){
        return dependency.toString().isEmpty();
    }

    public List<Dependency> get(){
        try {
            list = InventoryDatabase.databaseWriteExecutor.submit(()->dependencyDao.get()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    public void add(Dependency dependency) {
        InventoryDatabase.databaseWriteExecutor.submit(() -> dependencyDao.insert(dependency));
    }

    public void Edit(Dependency dependency) {
       list.get(list.lastIndexOf(dependency)).Edit(dependency);
       InventoryDatabase.databaseWriteExecutor.submit(() -> dependencyDao.update(dependency));
    }

    public void delete(Dependency deleted) {
        InventoryDatabase.databaseWriteExecutor.submit(() -> dependencyDao.delete(deleted));
    }
}
