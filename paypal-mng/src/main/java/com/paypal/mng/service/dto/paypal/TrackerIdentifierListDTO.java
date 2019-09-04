package com.paypal.mng.service.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TrackerIdentifierListDTO {
    @JsonProperty(value = "tracker_identifiers")
    private List<Tracker> trackerList;

    @JsonProperty("errors")
    private List<PayPalError> errors;

    public List<Tracker> getTrackerList() {
        return trackerList;
    }

    public void setTrackerList(List<Tracker> trackerList) {
        this.trackerList = trackerList;
    }

    public List<PayPalError> getErrors() {
        return errors;
    }

    public void setErrors(List<PayPalError> errors) {
        this.errors = errors;
    }

    public static class PayPalError {
        private String name;

        private String message;

        @JsonProperty("details")
        private List<ErrorDetail> details;

        public PayPalError() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<ErrorDetail> getDetails() {
            return details;
        }

        public void setDetails(List<ErrorDetail> details) {
            this.details = details;
        }

        @Override
        public String toString() {
            return "PayPalError{" +
                "name='" + name + '\'' +
                ", message='" + message + '\'' +
                ", details=" + details +
                '}';
        }
    }

    public static class ErrorDetail {
        private String field;

        private String value;

        private String location;

        private String issue;

        private String description;

        public ErrorDetail() {
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getIssue() {
            return issue;
        }

        public void setIssue(String issue) {
            this.issue = issue;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "ErrorDetail{" +
                "field='" + field + '\'' +
                ", value='" + value + '\'' +
                ", location='" + location + '\'' +
                ", issue='" + issue + '\'' +
                ", description='" + description + '\'' +
                '}';
        }
    }

    @Override
    public String toString() {
        return "TrackerIdentifierListDTO{" +
            "trackerList=" + trackerList +
            ", errors=" + errors +
            '}';
    }
}
