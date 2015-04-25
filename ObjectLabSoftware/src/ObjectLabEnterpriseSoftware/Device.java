package ObjectLabEnterpriseSoftware;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/* This class (model) is used to describe a device that has the following properties:
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

    private static final int INITIAL_TABLE_SIZE = 40;
    private final HashMap<String, Object> fields;

    public Device()
    {
        deviceName = null;
        fileExtensions = new ArrayList<>();
        fieldNames = new ArrayList<>();
        fields = new HashMap<>(INITIAL_TABLE_SIZE);
    }

    public Device(String name, ArrayList<String> ext)
    {
        deviceName = name;
        fileExtensions = ext;
        fieldNames = new ArrayList<>();
        fields = new HashMap<>(INITIAL_TABLE_SIZE);
    }

    public void setDeviceName(String n)
    {
        deviceName = n;
    }

    public void setFileTypes(ArrayList<String> ext)
    {
        if (fileExtensions != null)
        {
            fileExtensions.clear();
        }
        fileExtensions = ext;
    }

    public void setFieldNames(ArrayList<String> fn)
    {
        if (fieldNames != null)
        {
            fieldNames.clear();
        }
        fieldNames = fn;
    }

    public void setFieldNames(String fn[])
    {
        if (fieldNames != null)
        {
            fieldNames.clear();
        }
        fieldNames.addAll(Arrays.asList(fn));
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

    public int getFieldType(String key)
    {
        Object value = fields.get(key);

        if (value instanceof Double || value instanceof Integer)
        {
            return TYPE_DOUBLE;
        } else if (value instanceof String)
        {
            return TYPE_STRING;
        } else
        {
            return TYPE_UNKOWN;
        }
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
        /* Validate our params passed in return false if value is not one of the defined types */
        if (fields == null || key == null || value == null || !(value instanceof Double || value instanceof Integer || value instanceof String))
            return false;

        /* If the key does not exist then add the key into the list of field names for this Device */
        if (!fields.containsKey(key))
            fieldNames.add(key);
        fields.put(key, value);

        return true;
    }
    
    public Object rmField(String key)
    {
        fieldNames.remove(key);
        return fields.remove(key);
    }

}
