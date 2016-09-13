/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.model.common.rest;

import java.io.Serializable;

import org.openo.baseservice.remoteservice.exception.ExceptionArgs;

/**
 * Response package of REST request, provide exception, data body analysis.<br/>
 * 
 * @param <T>Generic paradigm
 * @author
 * @version SDNO 0.5 Aug 4, 2016
 */
public class RestRsp<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected int result;

    protected String message;

    protected T data;

    protected ExceptionArgs exceptionArg;

    /**
     * Constructor<br/>
     * 
     * @since SDNO 0.5
     */
    public RestRsp() {
        super();
    }

    /**
     * Constructor<br/>
     * 
     * @param result Result to set
     * @since SDNO 0.5
     */
    public RestRsp(int result) {
        super();
        this.result = result;
    }

    /**
     * Constructor<br/>
     * 
     * @param result Result to set
     * @param message Message to set
     * @since SDNO 0.5
     */
    public RestRsp(int result, String message) {
        super();
        this.result = result;
        this.message = message;
    }

    /**
     * Constructor<br/>
     * 
     * @param result Result to set
     * @param message Message to set
     * @param data Data to set
     * @since SDNO 0.5
     */
    public RestRsp(int result, String message, T data) {
        super();
        this.result = result;
        this.message = message;
        this.data = data;
    }

    /**
     * Constructor<br/>
     * 
     * @param result Result to set
     * @param data Data to set
     * @since SDNO 0.5
     */
    public RestRsp(int result, T data) {
        super();
        this.result = result;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public ExceptionArgs getExceptionArg() {
        return exceptionArg;
    }

    public String getMessage() {
        return message;
    }

    public int getResult() {
        return result;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setExceptionArg(ExceptionArgs exceptionArg) {
        this.exceptionArg = exceptionArg;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
