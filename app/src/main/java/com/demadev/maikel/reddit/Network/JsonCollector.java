package com.demadev.maikel.reddit.Network;

import org.json.JSONException;

/**
 * Created by Maikel on 26/12/2014.
 */
public interface JsonCollector {
    public String retrieveProperJson(String rawJson)throws JSONException;
}
