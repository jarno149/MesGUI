/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dy.fi.maja.mesgui.models;

import java.util.List;

public class ChangedOrdersResult
    {
        private List<Order> modifiedObjects;
        private List<Order> createdObjects;
        private List<Order> removedObjects;

        public ChangedOrdersResult(List<Order> modifiedObjects, List<Order> createdObjects, List<Order> removedObjects)
        {
            this.modifiedObjects = modifiedObjects;
            this.createdObjects = createdObjects;
            this.removedObjects = removedObjects;
        }

    public ChangedOrdersResult()
    {
    }

    public void setModifiedObjects(List<Order> modifiedObjects)
    {
        this.modifiedObjects = modifiedObjects;
    }

    public void setCreatedObjects(List<Order> createdObjects)
    {
        this.createdObjects = createdObjects;
    }

    public void setRemovedObjects(List<Order> removedObjects)
    {
        this.removedObjects = removedObjects;
    }
        
        

        public List<Order> getModifiedObjects()
        {
            return modifiedObjects;
        }

        public List<Order> getCreatedObjects()
        {
            return createdObjects;
        }

        public List<Order> getRemovedObjects()
        {
            return removedObjects;
        }
        
        public int getResultsCount()
        {
            return this.modifiedObjects.size()
                    + this.createdObjects.size()
                    + this.removedObjects.size();
        }
    }
