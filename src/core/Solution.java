package core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sergio on 15/05/15.
 */
public class Solution implements Cloneable, Comparable<Solution> {
    private int age;
    private boolean[] booleanArray;
    private Dataset objects;
    public double optimizationValue;

    public Solution(int length) {
        this.booleanArray = new boolean[length];
        this.age = 0;
    }

    public Solution(int length, Set<Integer> features) {
        this.booleanArray = new boolean[length];
        this.age = 0;
        for (int i = 0; i < length; i++) {
            if (features.contains(i)) {
                booleanArray[i] = true;
            } else {
                booleanArray[i] = false;
            }
        }
    }

    public Set<Integer> featureSet() {
        Set<Integer> features;
        features = new HashSet<Integer>();
        int i;
        for (i = 0; i < booleanArray.length; i++) {
            if (booleanArray[i]) {
                features.add(i);
            }
        }
        return features;
    }

    public boolean isComplete() {
        boolean flag;
        flag = true;

        int i;
        for (i = 0; i < booleanArray.length; i++)
            if (!booleanArray[i]) {
                flag = false;
            }
        return flag;
    }

    public boolean isEqualBooleanArray(Solution sol) throws Exception {
        if (booleanArray.length != sol.booleanArray.length)
            throw new Exception("Unable to compare arrays of different length.");

        boolean equal = true;
        for (int i = 0; i < sol.booleanArray.length; i++)
            if (booleanArray[i] != sol.booleanArray[i])
                equal = false;
        return equal;
    }

    public boolean isEqualToAnyBooleanArray(List<Solution> possible) throws Exception {
        boolean equal = false;
        for (int i = 0; i < possible.size(); i++)
            if (possible.get(i).isEqualBooleanArray(this))
                equal = true;
        return equal;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Solution theClone = new Solution(this.booleanArray.length);
        theClone.setObjects(this.objects.clone());
        theClone.setOptimizationValue(this.optimizationValue);
        theClone.setAge(this.age);
        for (int i = 0; i < booleanArray.length; i++)
            theClone.booleanArray[i] = booleanArray[i];
        return theClone;
    }

    @Override
    public int compareTo(Solution o) {
        if (o.getOptimizationValue() > this.optimizationValue) {
            return 1;
        } else {
            if (o.getOptimizationValue() == this.optimizationValue) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean[] getBooleanArray() {
        return booleanArray;
    }

    public void setBooleanArray(boolean[] booleanArray) {
        this.booleanArray = booleanArray;
    }

    public Dataset getObjects() {
        return objects;
    }

    public void setObjects(Dataset objects) {
        this.objects = objects;
    }

    public double getOptimizationValue() {
        return optimizationValue;
    }

    public void setOptimizationValue(double optimizationValue) {
        this.optimizationValue = optimizationValue;
    }
}
