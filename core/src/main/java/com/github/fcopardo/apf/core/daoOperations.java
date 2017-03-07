package com.github.fcopardo.apf.core;

import android.content.Context;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by FcoPardo on 3/5/17.
 */

public interface daoOperations<T extends AbstractModel<C>, C, O> {
    /**
     * entityClass: a reference to the entity's class.
     * idClass: a reference to the id's class.
     * source: a instance of an entity, embedded into the DAO.
     * id: the id or primary key element of the "source"
     * dao: base generic dao to access the ormLite API.
     * context: an android application context.
     * helper: a class in charge of providing the database connection and schema.
     */

    public void setDatabaseName(String s);

    public String getDatabaseName();

    public void setContext(Context c);

    public Context getContext();

    /**
     * Allows to get the helper used in this DAO.
     *
     * @return a database helper based in the helperClass (O) argument
     */
    public O getHelper();

    public void setHelper(O helper);

    /**
     * Adds the error to the ErrorHandler so it can be reported later.
     *
     * @param t the error message.
     */
    public void handleError(String t);

    /**
     * Returns the entity affected by the CRUD operations. If neither an operation has been done nor an entity has been set, it will return a new instance.
     * @return
     */
    public T getSource();

    public void setSource(T source);

    public String getRouteToId();

    public void setRouteToId(String routeToId);

    /**
     * Returns a boolean.
     * Creates an instance of T in the database, using source as the object instance.
     *
     * @return true if the instance is created successfully.
     */
    public boolean create();

    /**
     * Returns a boolean.
     * Creates an instance of T in the database, using entity as the object instance.
     *
     * @param entity an entity object to be instantiated.
     * @return true if the instance is created successfully.
     */
    public boolean create(T entity);

    /**
     * Returns a boolean.
     * Looks for an object instance into the database.
     *
     * @param id the ID of the object to be searched.
     * @return true if the instance is found.
     */
    public boolean find(C id);

    /**
     * Returns a boolean.
     * Looks for an object instance into the database, using the embedded object.
     *
     * @return true if the instance is found.
     */
    public boolean find();

    /**
     * Returns a boolean.
     * Updates an object instance into the database, referenced by the embedded object's key.
     *
     * @return true if the instance is updated.
     */
    public boolean update(T entity);

    /**
     * Returns a boolean.
     * Updates an object instance into the database, referenced by entity's key.
     *
     * @param entity the instance to be updated.
     * @return true if the instance is updated.
     */
    public boolean update();

    /**
     * Returns a boolean.
     * Search an object instance in the database and then deletes it.
     *
     * @param a the ID of the object to be deleted.
     * @return true if the instance is successfully deleted.
     */
    public boolean findAndDelete(C a);

    /**
     * Creates or updates an object, depending if it's in the database or not.
     * @param a The entity to be persisted
     * @return true if the entity was persisted, false if either create or update were impossible.
     */
    public boolean persist(T a);

    /**
     * Returns a boolean.
     * Deletes an object instance from the database.
     *
     * @param entity the object to be deleted.
     * @return true if the instance is successfully deleted.
     */
    public boolean delete(T entity);

    /**
     * If possible, creates instances of every object in the list into the database. If any object exists, then updates it instead.
     *
     * @param list the objects to be instantiated.
     */
    public void importList(List<T> list);

    /**
     * Returns all the objects from the database, either in FIFO or LIFO order.
     *
     * @param invertOrder * @param invertOrder indicates if the list must be LIFO or FIFO ordered. True for LIFO, false for FIFO (default order).
     * @return a list with the table contents in the desired order.
     */
    public List<T> getAll(boolean invertOrder);

    /**
     * Returns all the objects from the database which complains with column=value, either in FIFO or LIFO order.
     *
     * @param column      the column that will be used to filter the group.
     * @param value       the value that the column must have.
     * @param invertOrder indicates if the list must be LIFO or FIFO ordered. True for LIFO, false for FIFO (default order).
     * @return A list with all the complaining instances.
     */
    public List<T> getGroup(String column, Object value, boolean invertOrder);

    /**
     * Returns all the objects from the database matching all the values into @map, either in FIFO or LIFO order.
     *
     * @param map         a Map containing a String for identifying the column to be used, and the value to be searched.
     * @param invertOrder indicates if the list must be LIFO or FIFO ordered. True for LIFO, false for FIFO (default order).
     * @return a list with all the complaining instances.
     */
    public List<T> getGroup(Map map, boolean invertOrder);

    /**
     * Returns all the objects from the database that matches the condition "column between lowValue and topValue".
     * Optionally, it allows to search by primary key, and several more fields using a map.
     *
     * @param column   column to be evaluated
     * @param lowValue lowest value
     * @param topValue highest value
     * @param withID   true to use the ID of the source object embedded in the DAO. False to not use it.
     * @param map      Column, Value. Allows to create more search parameters.
     */
    public List<T> getBetween(String column, Object lowValue, Object topValue,
                              boolean withID, Map<String, Object> map);

    /**
     * Returns all the objects from the database that matches the condition "column between lowValue and topValue".
     * Optionally, it allows to search by primary key.
     *
     * @param column   column to be evaluated
     * @param lowValue lowest value
     * @param topValue highest value
     * @param withID   true to use the ID of the source object embedded in the DAO. False to not use it.
     */
    public List<T> getBetween(String column, Object lowValue, Object topValue,
                              boolean withID);

    /**
     * Creates a LinkedHashMap of type <C, T> from a list of type <T>. Allows to search through the received entities
     * using the ID field without iterating. Supports regular iteration too. Useful for collections that will be used
     * in any selector-like control.
     *
     * @param list a List<T> where T is the entity type.
     * @return a LinkedHashMap<Entity.IdField, Entity>
     */
    public Map<C, T> getDataAsMap(List<T> list);

    /**
     * Returns all the objects from the database matching all the conditions in the map, either in FIFO or LIFO order.
     * Optionally, it can use the embedded object's primary key to search as well.
     *
     * @param withID  true to use the ID of embedded entity (source)
     * @param map     the query's parameters. "String" is an identifier for the whole argument. It should
     *                be table field+number and it allows to modify the query a lot faster than a list,
     *                while Object[] contains the method to be executed, the value to be searched and finally
     *                the table field to be evaluated. The map must be an instance or subclass of LinkedHashMap if you
     *                want to use the OR clause.
     * @param reverse true for LIFO, false for FIFO.
     * @return a list with the complaining elements.
     */
    public List<T> getListByQuery(boolean withID, boolean reverse, Map<String, Object[]> map);

    /*
    Performs a IN search
     */
    public List<T> getListWhereIn(boolean withID, boolean reverse, String column, List<Object> values);

    /**
     * Deletes all the T objects from the database.
     *
     */
    public void deleteAll();

    /**
     * Deletes all the objects matching the ids from the collection argument.
     * @param ids the ids of the objects to be deleted.
     */
    public void deleteByIds(Collection<C> ids);

    /**
     * Deletes all the objects into the collection from the database.
     * @param instances a collection of T instances.
     */
    public void deleteCollection(List<T> instances);


}
