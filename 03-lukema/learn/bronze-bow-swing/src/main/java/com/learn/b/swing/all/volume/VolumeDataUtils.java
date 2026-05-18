package com.learn.b.swing.all.volume;


import java.util.ArrayList;
import java.util.List;

import com.learn.b.swing.all.raw.RawData;
import com.learn.b.swing.all.raw.RawDataUtils;


public class VolumeDataUtils {
    private static final List<VolumeData> DATA = new ArrayList<>();

    private static void init() {
        for (RawData rd : RawDataUtils.getData()) {
            VolumeData volumeData = new VolumeData();
            DATA.add(volumeData);

            volumeData.setVolume(rd.getVolume());
        }
    }

    public static List<VolumeData> getData() {
        synchronized (VolumeDataUtils.class) {
            if (DATA.size() == 0) {
                init();
            }
        }

        return DATA;
    }
}
