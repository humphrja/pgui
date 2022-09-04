package pgui.type;

import java.util.Arrays;

/**
 * A datatype used for organising multiple elements together
 */
public class ElementList {
    // Two parallel arrays of equal length
    Element[] E = new Element[0];
    String[] ids = new String[0];

    ElementList() {
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
     * Appends an element to the ElementList
     * @param x The Element to be added
     * @param ID The ID of the Element to be added
     */
    public void append(Element x, String ID){
        E = Arrays.copyOf(E, E.length + 1);
        E[E.length - 1] = x;

        ids = Arrays.copyOf(ids, ids.length + 1);
        ids[ids.length - 1] = ID;
    }

    /**
     *
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
}
