package com.example.inventorylivedata.iu.base;

import java.util.List;

/**
 * Esta interfaz corresponde a todas las vistas que reciban del origen de datos
 * un listado de objeto génerico.
 * @param <T>
 */
public interface BaseListView <T> {
    void onSuccess(List<T> list);
}
