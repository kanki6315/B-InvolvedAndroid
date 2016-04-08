package edu.bucknell.binvolved.categories;

/**
 * Created by Alex on 4/3/2016.
 */
public class Category {
    public String name;
    public String region;
    public String resourceId;

    public Category()
    {
        // TODO Auto-generated constructor stub
    }

    public Category(String name, String region, String resourceFilePath)
    {
        //what data should go here?
        this.name = name;
        this.region= region;
        this.resourceId = resourceFilePath;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
