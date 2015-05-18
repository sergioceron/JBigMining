package core;

import java.util.ArrayList;

/**
 * Created by sergio on 15/05/15.
 */
public class Structure extends ArrayList<Structure.Item> {


    public Structure.Item add(Object descriptor, Dataset dataset) {
        Structure.Item item = new Structure.Item();
        item.setDescriptor(descriptor);
        item.setDataset(dataset);
        super.add(item);
        return item;
    }

    public void removeNull() {
        int i = 0;
        while (i < size()) {
            if (this.get(i).getDataset() == null)
                remove(i);
            else
                i++;
        }
    }

    public static class Item implements Cloneable {
        private Object descriptor;
        private Dataset dataset;

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Structure.Item si = new Structure.Item();
            si.setDescriptor(this.descriptor);
            si.setDataset(this.dataset.clone());
            return si;
        }

        public Object getDescriptor() {
            return descriptor;
        }

        public void setDescriptor(Object descriptor) {
            this.descriptor = descriptor;
        }

        public Dataset getDataset() {
            return dataset;
        }

        public void setDataset(Dataset dataset) {
            this.dataset = dataset;
        }
    }
}
