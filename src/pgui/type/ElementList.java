package pgui.type;

import java.util.Arrays;

/**
 * A datatype used for organising multiple elements together
 */
public class ElementList {
    // Two parallel arrays of equal length
    Element[] E = new Element[0];
    String[] ids = new String[0];

    public ElementList() {
    }

    /**
     * Returns the Element with the given ID
     * @param ID Element ID
     * @return The corresponding Element with that ID
     */
    public Element get(String ID){
        for (int i = 0; i < ids.length; i++) {
            if (ids[i].equals(ID)) {
                return E[i];
            }
        }
        return null;
    }

    /**
     * Returns the ID of the given Element
     * @param x Element
     * @return Element's ID
     */
    public String ID(Element x){
        for (int i = 0; i < E.length; i++) {
            if (E[i].equals(x)) {
                return ids[i];
            }
        }
        return null;
    }

    /**
     * Adds an element to the end of ElementList
     * @param x The Element to be added
     * @param ID The ID of the Element to be added
     */
    public void append(Element x, String ID){
        x.setID(ID);

        E = Arrays.copyOf(E, E.length + 1);
        E[E.length - 1] = x;

        ids = Arrays.copyOf(ids, ids.length + 1);
        ids[ids.length - 1] = ID;
    }

    /**
     * Adds an element to the end of ElementList.
     * Generates ID in the form: ClassName#xxx, where xxx is the index represented as 3 digits
     * @param x Element to be added
     */
    public void append(Element x){
        // Determine element type
        String elementType = x.getClass().getSimpleName();

        int num = numberOf(x.getClass());
        // Formats string with 3 numbers
        String ID = abbr(elementType) + "#" + String.format("%03d", num);
        append(x, ID);
    }

    /**
     * Converts a class name into a 3 character long abbreviation
     * @param className
     * @return
     */
    String abbr(String className){
        switch (className.toString()){
            case ("Button"):            return "BTN";
            case ("RadioButton"):       return "RAD";
            case ("RadioButtonGroup"):  return "RBG";
            case ("ScrollWindow"):      return "SCW";
            case ("Slider"):            return "SLD";
            case ("Switch"):            return "SWT";
            case ("Text"):              return "TXT";
            case ("Window"):            return "WIN";
            default:                    return  null;
        }
    }

    // Determine number of previous elements
    int numberOf(Class c){
        int count = 0;
        for (Element e : E){
            if (e.getClass().equals(c)){
                count++;
            }
        }
        return count;
    }

    /**
     * Index of specified element
     * @return The index of the specified element
     */
    public int index(Element x){
        for (int i = 0; i < E.length; i++){
            if (E[i].equals(x)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Length of list
     * @return Length of list
     */
    public int length(){
        return E.length;
    }

    /**
     * Returns an array of elements
     * @return Element[]
     */
    public Element[] elements(){
        return E;
    }

    /**
     * Returns a String array of element IDs
     * @return String[] IDs
     */
    public String[] ids(){
        return ids;
    }

    @Override
    public String toString(){
        String out = "[ ";
        for (String i : ids){
            out += i + " ";
        }
        return out + "]";
    }
}
