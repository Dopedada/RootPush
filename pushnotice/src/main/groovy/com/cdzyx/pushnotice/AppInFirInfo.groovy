package com.cdzyx.pushnotice

import com.google.gson.annotations.SerializedName;

class AppInFirInfo {
    private Cert cert

    Cert getCert() {
        return cert
    }

    static class Cert {
        private Upload binary
        private Upload icon

        Upload getBinary() {
            return binary
        }

        Upload getIcon() {
            return icon
        }

        static class Upload {
            @SerializedName("key")
            private String uploadKey
            @SerializedName("token")
            private String uploadToken
            @SerializedName("upload_url")
            private String uploadUrlAddress

            String getUploadKey() {
                return uploadKey
            }

            String getUploadToken() {
                return uploadToken
            }

            String getUploadUrlAddress() {
                return uploadUrlAddress
            }

            @Override
            public String toString() {
                return "Upload{" +
                        "uploadKey='" + uploadKey + '\'' +
                        ", uploadToken='" + uploadToken + '\'' +
                        ", uploadUrlAddress='" + uploadUrlAddress + '\'' +
                        '}';
            }
        }
    }
}
