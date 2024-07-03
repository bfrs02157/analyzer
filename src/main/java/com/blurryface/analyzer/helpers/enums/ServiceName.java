package com.blurryface.analyzer.helpers.enums;

public enum ServiceName {

    GLOBAL_AUTH("Global-Auth"),
    SHIPROCKET("Shiprocket"),
    PATRON("patron"),
    SHOPIFY("shopify"),
    SINFINI("Sinfini"),
    KALEYRA("Kaleyra"),
    NETCORE("netcore");

    final String name;

    ServiceName(String name) {
        this.name = name;
    }
}
