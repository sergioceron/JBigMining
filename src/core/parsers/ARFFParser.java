/*
 * Copyright (c) %today.year Sergio Ceron Figueroa
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of copyright holders nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * ''AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL COPYRIGHT HOLDERS OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package core.parsers;

import core.Dataset;
import core.FeatureDescription;
import core.MetaObject;
import core.PRObject;
import core.interfaces.IParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * -
 *
 * @author Sergio Ceron F.
 * @version rev: %I%
 * @date 28/05/13 02:30 PM
 */
public class ARFFParser implements IParser {
    private File file;
    private int classIndex = 0;

    public ARFFParser() {

    }

    public Dataset parse(String file) {
        this.file = new File(file);
        Dataset dataset;
        try {
            dataset = readHeaders();
            readData(dataset);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return dataset;
    }

    public void readData(Dataset dataSet) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        DataInputStream din = new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(din));

        boolean dataFlag = false;
        String line;
        while ((line = br.readLine()) != null) {

            if (line.toUpperCase().startsWith("@DATA")) {
                dataFlag = true;
                continue;
            }

            if (dataFlag && !line.trim().equals("%")) {
                PRObject instance = new PRObject(dataSet.getMetaObject());

                String values[] = line.split(",");
                for (int i = 0; i < values.length; i++) {
                    String rawValue = values[i].trim();
                    if (i == classIndex) {
                        instance.setClassLabel(rawValue);
                        continue;
                    }
                    FeatureDescription attribute = dataSet.getMetaObject().getFeatureDescriptions()[i];
                    if (rawValue.equals("?") || rawValue.isEmpty()) {
                        instance.getFeaturesValues()[i] = null;
                    } else if (attribute.getType() == FeatureDescription.Type.NUMERIC ||
                            attribute.getType() == FeatureDescription.Type.REAL) {
                        instance.getFeaturesValues()[i] = Double.valueOf(rawValue);
                    } else if (attribute.getType() == FeatureDescription.Type.INTEGER) {
                        instance.getFeaturesValues()[i] = Integer.valueOf(rawValue);
                    } else if (attribute.getType() == FeatureDescription.Type.BOOLEAN) {
                        if (rawValue.equals("1") || rawValue.toLowerCase().equals("true"))
                            instance.getFeaturesValues()[i] = true;
                        else
                            instance.getFeaturesValues()[i] = false;
                    } else {
                        attribute.putValue(rawValue);
                        instance.getFeaturesValues()[i] = Double.valueOf(rawValue);
                    }
                }

                dataSet.add(instance);

            }
        }

        br.close();
        din.close();
        fis.close();
    }

    public Dataset readHeaders() throws IOException {
        FileInputStream fis = new FileInputStream(file);
        DataInputStream din = new DataInputStream(fis);
        BufferedReader br = new BufferedReader(new InputStreamReader(din));

        String name = null;
        Dataset dataset;
        List<FeatureDescription> features = new ArrayList<FeatureDescription>();
        String line;
        int index = 0;
        while ((line = br.readLine()) != null) {
            if (line.toUpperCase().startsWith("@RELATION")) {
                name = readRelation(line);
            } else if (line.toUpperCase().startsWith("@ATTRIBUTE")) {
                FeatureDescription attribute = readAttribute(line);
                if (attribute.getName().equals("class"))
                    classIndex = index;
                else
                    features.add(attribute);
                index++;
            } else if (line.toUpperCase().startsWith("@DATA")) {
                break;
            }
        }

        MetaObject metaobject = new MetaObject(features.toArray(new FeatureDescription[features.size()]));
        dataset = new Dataset(name, metaobject);

        br.close();
        din.close();
        fis.close();

        return dataset;
    }

    private FeatureDescription readAttribute(String line) {
        line = line.replaceAll("\\t", " ");
        line = line.substring("@ATTRIBUTE".length()).trim();
        String name;
        if (line.startsWith("\"")) {
            int begin = line.indexOf('"');
            int last = line.lastIndexOf('"');
            name = line.substring(begin + 1, last);
        } else {
            name = line.split(" ")[0];
        }

        line = line.replaceFirst(name, "").trim();

        FeatureDescription attribute = new FeatureDescription(name.trim());
        if (line.startsWith("{")) {
            line = line.replaceAll("\"", "");
            String[] values = line.replaceAll("\\{", "").replaceAll("\\}", "").split(",");
            attribute.setType(FeatureDescription.Type.NOMINAL);
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].trim();
            }
            attribute.setValues(Arrays.asList(values));
            if (isBoolean(attribute.getValues()))
                attribute.setType(FeatureDescription.Type.BOOLEAN);
        } else {
            attribute.setType(FeatureDescription.Type.valueOf(line.toUpperCase()));
        }

        return attribute;
    }

    private boolean isBoolean(List<String> values) {
        for (String value : values) {
            if (!(value.equals("0") || value.equals("1")
                    || value.toLowerCase().equals("true")
                    || value.toLowerCase().equals("false"))) {
                return false;
            }
        }
        return true;
    }

    private String readRelation(String line) {
        line = line.replaceAll("\\t", " ");
        return line.substring(9).trim();
    }
}
