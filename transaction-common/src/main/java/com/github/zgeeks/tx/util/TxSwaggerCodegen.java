package com.github.zgeeks.tx.util;

import io.swagger.codegen.languages.JavaResteasyServerCodegen;

public class TxSwaggerCodegen extends JavaResteasyServerCodegen {

    public TxSwaggerCodegen() {
        apiTemplateFiles.remove("apiServiceImpl.mustache");
        apiTemplateFiles.remove("apiServiceFactory.mustache");
        apiTemplateFiles.remove("apiService.mustache");
    }
}
