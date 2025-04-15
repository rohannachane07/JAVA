package model;
abstract public class Vehicle
{
    protected String number;  //Use String for vehicle numbers, as they often include alphabets (e.g., MH12AB1234).
    protected String type;    //We use protected instead of private bcoz we want to allow subclasses to access these fields directly.
    protected long entryTime;  //But we still maintain encapsulation because:We don’t expose the fields directly to UI or service classes.We provide public getters/setters to control how external code can access/modify the data.

    public Vehicle(String n)
    {
        number=n;
        this.entryTime=System.currentTimeMillis();
    }

    public String getNumber()
    {
        return number;
    }

    public String getType()
    {
        return type;
    }

    public long getEntryTime()
    {
        return entryTime;
    }
    public abstract double calculateCharge();
}
