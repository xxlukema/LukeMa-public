package com.learn.jsf.bean;


import java.io.Serializable;
import java.util.StringTokenizer;


public class LabelValue
    implements Serializable, Comparable<Object> {
    private static final long serialVersionUID = 1L;

    private static final String DELIM = ":";

    private String label;
    private int value = 0;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + value;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        LabelValue other = (LabelValue) obj;
        if (label == null) {
            if (other.label != null)
                return false;
        }
        else if (!label.equals(other.label))
            return false;
        if (value != other.value)
            return false;
        return true;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }

        if (o instanceof LabelValue) {
            if (this.label == null) {
                return -1;
            }
            int ret = this.label.compareTo(((LabelValue) o).label);
            if (ret != 0) {
                return ret;
            }
            else {
                return this.value - ((LabelValue) o).value;
            }
        }

        return 1;
    }

    @Override
    public String toString() {
        return label + DELIM + value;
    }

    public static LabelValue toMe(String str) {
        if (str != null) {
            StringTokenizer st = new StringTokenizer(str, DELIM);
            if (st.hasMoreTokens()) {
                String lbl = st.nextToken();
                if (st.hasMoreTokens()) {
                    String v = st.nextToken();

                    try {
                        LabelValue labelValue = new LabelValue();
                        labelValue.setLabel(lbl);
                        labelValue.setValue(Integer.valueOf(v));
                        return labelValue;
                    }
                    catch (Throwable t) {
                    }
                }
            }
        }

        return null;
    }

    public static LabelValue getUnknown() {
        LabelValue labelValue = new LabelValue();
        labelValue.setLabel("Unknown");
        return labelValue;
    }
}
