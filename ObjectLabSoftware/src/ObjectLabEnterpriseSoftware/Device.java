package ObjectLabEnterpriseSoftware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* This class is used to describe a device that has the following properties:
    device name, accepted file extensions, trackable fields (see requirment documents regarding
        art department hardware device).
*/
public class Device 
{
    /* We only want to store either the type Double or String */
    public static final int TYPE_UNKOWN = -1;
    public static final int TYPE_DOUBLE = 0;
    public static final int TYPE_STRING = 1;
    
    private String deviceName;
    private ArrayList<String> fileExtensions;
    private ArrayList<String> fieldNames;
    private HashMap<String, Object> fields;
    
    public Device()
    {
        deviceName = null;
        fileExtensions = null;
        fieldNames = null;
        fields = null;
    }
    
    public Device(String name, ArrayList<String> ext)
    {
        deviceName = name;
        fileExtensions = ext;
        fieldNames = new ArrayList<>();
        fields = new HashMap<>();
    }
    
    public void setDeviceName(String n)
    {
        deviceName = n;
    }
    
    public void setFileTypes(ArrayList<String> ext)
    {
        fileExtensions = ext;
    }
    
    public void setFieldNames(ArrayList<String> fn)
    {
        fieldNames = fn;
    }
    
    public void setFieldNames(String fn[])
    {
        if(fieldNames != null)
            fieldNames.clear();
        fieldNames.addAll(Arrays.asList(fn));
    }
    
    public void setDeviceData(String nameOfFields[], HashMap<String, Object> dataTable)
    {
        setFieldNames(nameOfFields);
        fields = dataTable;
    }
    
    public String getDeviceName()
    {
        return deviceName;
    }
    
    public ArrayList<String> getFileExtensions()
    {
        return fileExtensions;
    }
    
    public ArrayList<String> getFieldNames()
    {
        return fieldNames;
    }
    
    public HashMap<String, Object> getTrackableFields()
    {
        return fields;
    }
    
    public void addFileExtension(String ext)
    {
        fileExtensions.add(ext);
    }
    
    /* For this method if you are simply trying to add in a type for the field just pass in any
        value for that data type. 
       For example, if you are adding a new device and the field that needs to be tracked is "volume"
        just pass in "new Double()" for the second parameter.
    */
    public boolean addField(String key, Object value)
    {
        /* Validate our params passed in return false if key exists or value is not one of the defined types */
        if(fields != null)
        {
            if(fields.containsKey(key))
                return false;
            
            if(!(value instanceof Double || value instanceof Integer || value instanceof String))
                return false;
        }
        
        fieldNames.add(key);
        fields.put(key, value);
        return true;
    }
    
    public int getFieldType(String key)
    {
        Object value = fields.get(key);
        
        if(value instanceof Double || value instanceof Integer)
            return TYPE_DOUBLE;
        else if(value instanceof String)
            return TYPE_STRING;
        else
            return TYPE_UNKOWN;
    }
    
    public Object getFieldData(String key)
    {
        return fields.get(key);
    }
    
    public Object rmKey(String key)
    {
        fieldNames.remove(key);
        return fields.remove(key);
    }
}
