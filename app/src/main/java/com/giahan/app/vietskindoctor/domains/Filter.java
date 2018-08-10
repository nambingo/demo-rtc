package com.giahan.app.vietskindoctor.domains;

import java.util.List;

/**
 * Created by pham.duc.nam on 28/05/2018.
 */

public class Filter {
    private String label;
    private String key;
    private List<Option> options;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
