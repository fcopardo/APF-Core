package com.github.fcopardo.apf.core;

import android.content.Context;

/**
 * Created by FcoPardo on 3/7/17.
 */

public class daoFactory<O> {

    private Class<O> schemaClass;

    private SpecificMemoryStorage daoStorage = new SpecificMemoryStorage();

    private String databaseName = "";

    public daoFactory(Class<O> schemaClass) {
        this.schemaClass = schemaClass;
    }

    public daoFactory(Class<O> schemaClass, Context context) {
        this.schemaClass = schemaClass;
    }

    public void setDatabaseName(String s){
        if( !(s==null || "".trim().equalsIgnoreCase(s)) ){
            if( !(databaseName==null || "".trim().equalsIgnoreCase(databaseName)) ){
                databaseName = s;
            }
        }
    }

    private String getDatabaseName(){
        if( !(databaseName==null || "".trim().equalsIgnoreCase(databaseName)) ){
            return null;
        }
        return databaseName;
    }

    /**
     * Allows to get singleton instances of AdvancedDao. Creates an specific AdvancedDao
     * for each BaseModel class in the given schema.
     *
     * @param entityClass a valid BaseModel subclass.
     * @param <T>         Generic class operator. Represents the BaseModel subclass.
     * @param <C>         Generic class operator. Represents the BaseModel subclass ID field's class.
     * @return an AdvancedDao parametrized
     * as AdvancedDao<T extends BaseModel, BaseModel.getIdClass, O extends OrmLiteSqliteOpenHelper>
     */
    public <T extends AbstractModel<C>, C> daoOperations<T, C, O> getSingleDaoInstance(Class<T> entityClass) {

        Class<C> idClass = null;
        try {

            idClass = entityClass.newInstance().getIdClass();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        String key = entityClass.getSimpleName() + "-" + schemaClass.getSimpleName();

        if (daoStorage.find(key)) {
            return (AdvancedDao<T, C, O>) daoStorage.getEntity(key, AdvancedDao.class);
        } else {
            AdvancedDao<T, C, O> dao = new AdvancedDao<>(entityClass, idClass, schemaClass);
            if(getDatabaseName()!=null){
                dao.setDatabaseName(databaseName);
            }
            daoStorage.create(key, dao);
            return dao;
        }
    }

    /**
     * Return an instance of AdvancedDao so one of this methods can be called. If the entity's class is not supported,
     * throws a GrizzlyModelException.
     *
     * @param entity  the entity to provide the DAO.
     * @param context the application context.
     * @param <T>     a subclass of BaseModel.
     * @return an AdvancedDao instance.
     * @throws com.grizzly.apf.Exceptions.GrizzlyModelException
     */
    public <T extends BaseModel<C>, C> AdvancedDao<T, C, O> getProperDao(T entity, Context context) throws GrizzlyModelException {

        try {
            AdvancedDao dao = this.getSingleDaoInstance(entity.getClass());
            dao.setSource(entity);
            dao.setContext(context);
            return dao;
        } catch (Exception e) {
            throw new GrizzlyModelException();
        }
    }

    public <T extends BaseModel<C>, C> AdvancedDao<T, C, O> getProperDao(Class<T> entityClass, Context context) throws GrizzlyModelException {

        try {
            AdvancedDao<T, C, O> dao = this.getSingleDaoInstance(entityClass);
            dao.setContext(context);
            return dao;
        } catch (Exception e) {
            throw new GrizzlyModelException();
        }
    }

    /**
     * Performs a search and returns a T entity matching the desired results. If there isn't any results, then throws a
     * GrizzlyNotFoundException. If the T class provided is not supported, throws a GrizzlyModelException.
     *
     * @param entity  the entity with the ID to be searched.
     * @param context application context.
     * @param <T>     A subclass of BaseModel.
     * @return a T object with the search result.
     * @throws com.grizzly.apf.Exceptions.GrizzlyModelException
     * @throws com.grizzly.apf.Exceptions.GrizzlyNotFoundException
     */
    public <T extends BaseModel<C>, C> T getProperDaoResponse(T entity, Context context) throws GrizzlyModelException, GrizzlyNotFoundException {

        try {
            AdvancedDao<T, C, O> dao = this.getProperDao(entity, context);
            if (dao.find()) {
                return dao.getSource();
            } else {
                throw new GrizzlyNotFoundException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new GrizzlyModelException();
        }
    }
}
