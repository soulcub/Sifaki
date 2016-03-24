package com.sifaki.webparser.geo.vk;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

public class VkResult {

    @SerializedName("response")
    private List<Response> response = new ArrayList<>();
    @SerializedName("error")
    private Error error;

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VkResult that = (VkResult) o;

        return Objects.equal(this.response, that.response) &&
                Objects.equal(this.error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(response, error);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("response", response)
                .add("error", error)
                .toString();
    }

    public class Response {
        @SerializedName("cid")
        private int cid;
        @SerializedName("title")
        private String title;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Response that = (Response) o;

            return Objects.equal(this.cid, that.cid) &&
                    Objects.equal(this.title, that.title);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(cid, title);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("cid", cid)
                    .add("title", title)
                    .toString();
        }
    }

    public class Error {
        @SerializedName("error_code")
        private int errorCode;
        @SerializedName("error_msg")
        private String errorMsg;
        @SerializedName("request_params")
        private List<RequestParam> requestParams = new ArrayList<>();

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public List<RequestParam> getRequestParams() {
            return requestParams;
        }

        public void setRequestParams(List<RequestParam> requestParams) {
            this.requestParams = requestParams;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Error that = (Error) o;

            return Objects.equal(this.errorCode, that.errorCode) &&
                    Objects.equal(this.errorMsg, that.errorMsg) &&
                    Objects.equal(this.requestParams, that.requestParams);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(errorCode, errorMsg, requestParams);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("errorCode", errorCode)
                    .add("errorMsg", errorMsg)
                    .add("requestParams", requestParams)
                    .toString();
        }
    }

    public class RequestParam {

        @SerializedName("key")
        private String key;
        @SerializedName("value")
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            RequestParam that = (RequestParam) o;

            return Objects.equal(this.key, that.key) &&
                    Objects.equal(this.value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(key, value);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("key", key)
                    .add("value", value)
                    .toString();
        }
    }
}