package com.github.fcopardo.apf.core;

/**
 * Created by FcoPardo on 3/5/17.
 */

public abstract class AbstractModel<T> implements modelOperations<T>{

    /**
     * Generic abstract method for accessing to ID field. The subclasses must implement
     * it as an accessor to the ID field.
     *
     * @return
     */

    public abstract T getId();
    public abstract void setId(T id);
    
    protected final Class<T> idClass;


    /**
     * Class members
     * StorageStrategy determines the entity's storage strategy.
     * encryptionActive determines if the entity's fields are going to be encrypted.
     * pathToFiles stores the relative path to any files the entity stores in the local file system.
     */

    protected int StorageStrategy;
    protected String pathToFiles;

    public AbstractModel(Class<T> idClass) {
        this.idClass = idClass;
    }

    public Class<T> getIdClass() {
        return idClass;
    }

    /**
     * Determines the storage strategy of the instance.
     *
     * @param UpdateStrategy 1 for Local or Memory storage, 2 for Database storage, 3 for WebService storage,
     *                       4 for Local and Database storage, 5 for Local and WebService storage, 6 for Database and WebService storage
     *                       and 7 for all of them.
     */
    public void setStorageStrategy(@Constants.StorageStrategy int UpdateStrategy) {
        this.StorageStrategy = UpdateStrategy;
    }

    public int getStorageStrategy() {
        return StorageStrategy;
    }

    public String getPathToFiles() {
        return pathToFiles;
    }

    public void setPathToFiles(String PathToFiles) {
        pathToFiles = PathToFiles;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if (!getId().equals((this.getClass().cast(o)).getId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}

