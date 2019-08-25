package com.paypal.mng.service.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TrackerList {
    @JsonProperty(value = "trackers")
    private List<Tracker> trackerList;

    public List<Tracker> getTrackerList() {
        return trackerList;
    }

    public void setTrackerList(List<Tracker> trackerList) {
        this.trackerList = trackerList;
    }

    @Override
    public String toString() {
        return "TrackerList{" +
            "trackerList=" + trackerList +
            '}';
    }
}
