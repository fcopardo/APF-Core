package com.github.fcopardo.apf.core;

/**
 * Created by FcoPardo on 3/5/17.
 */

public interface modelOperations<T> {

    /**
     * Generic abstract method for accessing to ID field. The subclasses must implement
     * it as an accessor to the ID field.
     *
     * @return
     */

    public abstract T getId();
    public abstract void setId(T id);

    public Class<T> getIdClass();

    /**
     * Determines the storage strategy of the instance.
     *
     * @param UpdateStrategy 1 for Local or Memory storage, 2 for Database storage, 3 for WebService storage,
     *                       4 for Local and Database storage, 5 for Local and WebService storage, 6 for Database and WebService storage
     *                       and 7 for all of them.
     */
    public void setStorageStrategy(@Constants.StorageStrategy int UpdateStrategy);

    public int getStorageStrategy();

    public String getPathToFiles();

    public void setPathToFiles(String PathToFiles);

    @Override
    public boolean equals(Object o);

    @Override
    public int hashCode();
}
